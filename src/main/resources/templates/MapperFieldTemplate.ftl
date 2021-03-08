<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${namespace}">

    <!-- 此文件用于专门填写自定义sql字段，在impl目录下的同名mapper文件中使用。如需修改，请注意保持数据的一致-->

    <!--BaseResultMap-->
    <resultMap id="BaseResultMap" type="${poType}">
        <#list fieldColumnList as fieldColumn>
        <#if fieldColumn.fieldName == "id">
        <id column="id" property="id" jdbcType="INTEGER"></id>
        <#else>
        <result column="${fieldColumn.columnName}" property="${fieldColumn.fieldName}" jdbcType="${fieldColumn.jdbcType}"></result>
        </#if>
        </#list>
    </resultMap>

    <!--查询条件-->
    <sql id="condition">
        <where>
            <if test="ids != null">
                <if test="ids.size() > 0">
                    id in
                    <foreach collection="ids" item="item" open="(" separator="," close=")">
                        <#noparse>#{item}</#noparse>
                    </foreach>
                </if>
                <if test="ids.size() == 0">
                    1 = 0
                </if>
            </if>
            <if test="id != null">
                and id = <#noparse>#{id}</#noparse>
            </if>
        </where>
    </sql>
    <!-- 排序规则-->
    <sql id="order_by">

    </sql>

    <!--  管理员权限能够查询的字段-->
    <sql id = "admin_field">
        <trim suffixOverrides=",">
            <#list  fieldColumnList as fieldColumn>
            ${fieldColumn.columnName},
            </#list>
        </trim>
    </sql>

    <!--  老师权限能够查询的字段-->
    <sql id="teacher_field">
        <trim suffixOverrides=",">
            <#list  fieldColumnList as fieldColumn>
            ${fieldColumn.columnName},
            </#list>
        </trim>
    </sql>

    <!--  学生权限能够查询的字段-->
    <sql id="student_field">
        <trim suffixOverrides=",">
            <#list  fieldColumnList as fieldColumn>
            ${fieldColumn.columnName},
            </#list>
        </trim>
    </sql>

    <!--  游客能够查询的字段-->
    <sql id="not_login_field">
        <trim suffixOverrides=",">
            <#list  fieldColumnList as fieldColumn>
            ${fieldColumn.columnName},
            </#list>
        </trim>
    </sql>

    <!--  自定义字段 -->
    <sql id="custom_field">
        <trim suffixOverrides=",">
            <#list  fieldColumnList as fieldColumn>
            ${fieldColumn.columnName},
            </#list>
        </trim>
    </sql>
</mapper>