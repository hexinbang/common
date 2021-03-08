package com.hxb.common.generator;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hexinbang@foxmail.com
 * @date 2019/10/10 17:12
 */
@Component
public class MapperGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String namespacePrefix = "org.hxb.common.dao";

    private String templateDir = "templates";

    private String outputDir = "mappers";

    /**
     * 使用Freemarker模板生成mapper文件
     * @param entityClazz
     * @param queryClazz
     */
    public void run(Class entityClazz,Class queryClazz){
        System.out.println("**********开始构造"+entityClazz.getSimpleName()+"的Mapper文件***********");
        this.initDefaultConfig();
        Configuration configuration=new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("UTF-8");
        BufferedWriter bufferedWriter=null;
        try {
            configuration.setClassForTemplateLoading(this.getClass(),"/templates");
            Map<String,Object>dataMap=getDataMap(entityClazz,queryClazz);
            String mapperFileName=entityClazz.getSimpleName()+"Mapper.xml";

            Template implTemplate=configuration.getTemplate("MapperImplTemplate.ftl");
            File mapperFile1=new File(outputDir+"sql_impl/"+mapperFileName);
            if (!mapperFile1.getParentFile().exists()){
                mapperFile1.getParentFile().mkdirs();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile1)));
            System.out.println("开始构造Mapper_impl文件，文件输出目录："+outputDir+"sql_impl...");
            implTemplate.process(dataMap,bufferedWriter);
            System.out.println("Mapper_impl文件构造完成！");
            Template fieldTemplate=configuration.getTemplate("MapperFieldTemplate.ftl");
            File mapperFile2=new File(outputDir+"sql_field/"+mapperFileName);
            if(!mapperFile2.getParentFile().exists()){
                mapperFile2.getParentFile().mkdirs();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile2)));
            System.out.println("开始生成Mapper_field文件，文件输出目录："+outputDir+"sql_field...");
            fieldTemplate.process(dataMap,bufferedWriter);
            System.out.println("Mapper_field文件构造完成！");
            System.out.println("***********"+entityClazz.getSimpleName()+"的Mapper文件构造结束**********");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedWriter!=null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量生成mapper文件
     * map中key为 entityClass，value为queryClass
     * @param var1
     */
    public void batchRun(@NonNull Map<Class,Class> var1){
        if (var1.size() > 0){
            for (Map.Entry entry:var1.entrySet()){
                Class var2 = (Class) entry.getKey();
                Class var3 = (Class) entry.getValue();
                this.run(var2,var3);
            }
        }
    }


    /**
     * 设置属性
     * 如果需要改变相应的文件路径。否则使用默认的参数
     * @param namespacePrefix
     * @param templateDir
     * @param outputDir
     */
    public void setConfig(String namespacePrefix,String templateDir,String outputDir){
        if(namespacePrefix != null && !namespacePrefix.isEmpty()){
            this.namespacePrefix = namespacePrefix;
        }
        if(templateDir != null && !templateDir.isEmpty()){
            this.templateDir = templateDir;
        }
        if(outputDir != null && !outputDir.isEmpty()){
            this.outputDir = outputDir;
        }
    }

    private void initDefaultConfig(){
        if(this.namespacePrefix == null){
            this.namespacePrefix = "org.hxb.common.dao";
        }
        if(this.templateDir == null){
            this.templateDir = "templates";
        }
        if(this.outputDir == null){
            this.outputDir = "mappers";
        }
    }

    private Map<String,Object> getDataMap(@NonNull Class entityClazz,@NonNull Class queryClazz){
        System.out.println("正在获取Class:"+entityClazz.getSimpleName()+"的数据集合...");
        StringBuilder sb=new StringBuilder();
        sb.append(namespacePrefix).append(".").append(entityClazz.getSimpleName()).append("Mapper");
        String namespace=sb.toString();
        String tableName=getTableName(entityClazz);
        String poType=entityClazz.getTypeName();
        String queryType=queryClazz.getTypeName();
        List<FieldWithColumn>fieldWithColumns=getFieldWithColumn(entityClazz);
        System.out.println("数据集合获取完成！");
        Map<String,Object> result=new HashMap<>();
        result.put("namespace",namespace);
        result.put("poType",poType);
        result.put("queryType",queryType);
        result.put("tableName",tableName);
        result.put("fieldColumnList",fieldWithColumns);
        return result;
    }

    @SneakyThrows
    private List<FieldWithColumn> getFieldWithColumn(@NonNull Class clazz){
        String tableName=this.getTableName(clazz);
        String sql="select * from "+tableName+" limit 1";
        SqlRowSet sqlRowSet=jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData rowSetMetaData=sqlRowSet.getMetaData();

        Map<String,String>fieldMap=this.getFieldNames(clazz);
        List<FieldWithColumn>result=new ArrayList<>();

        int length=rowSetMetaData.getColumnCount();
        for(int i=1;i<=length;i++){
            String columnName=rowSetMetaData.getColumnName(i);
            String columnType=JDBCType.valueOf(rowSetMetaData.getColumnType(i)).getName();
            //将数据库字段名下划线去掉，并全部转换为小写。跟entity的属性比较
            String key=columnName.replaceAll("_","").toLowerCase();
            String fieldName=fieldMap.get(key);
            if(fieldName==null){
                throw new Exception("找不到与表字段'"+columnName+"'相匹配的属性");
            }else{
                FieldWithColumn fieldWithColumn=new FieldWithColumn(fieldName,columnName,columnType);
                result.add(fieldWithColumn);
            }
        }
        return result;
    }

    private String getTableName(@NonNull Class clazz){
        TableName annotation= (TableName) clazz.getAnnotation(TableName.class);
        String tableName=annotation.value();
        if(annotation == null || tableName.equals("")){
            return null;
        }
        return tableName;
    }

    private Map<String, String> getFieldNames(@NonNull Class clazz){
        Field[]fields=clazz.getDeclaredFields();
        Map<String,String> result=new HashMap<>();
        for(Field field:fields){
            field.setAccessible(true);
            String fieldName=field.getName();
            //将字段转换为小写作为map的key，方便后续跟mysql表字段名作比较
            String key=fieldName.toLowerCase();
            result.put(key,fieldName);
        }
        return result;
    }

}
