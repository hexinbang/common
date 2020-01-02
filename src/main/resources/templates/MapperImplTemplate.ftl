<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${namespace}">
    <!--
    此文件用于实现mapper接口，如果Sql语句中有字段过长并且多次重复使用，为了代码的可读性以及可维护性，
    请尽量将重复多次出现的sql字段填写在sql_field文件夹下的同名文件中。
    -->

    <!-- 分页查找-->
    <select id="queryPage" resultMap="BaseResultMap" parameterType="${queryType}">
        select
        <if test="roleId == 1">
            <include refid="admin_field"></include>
        </if>
        <if test="roleId == 2">
            <include refid="teacher_field"></include>
        </if>
        <if test="roleId == 3">
            <include refid="student_field"></include>
        </if>
        <if test="roleId == 4">
            <include refid="not_login_field"></include>
        </if>
        from ${tableName}
        <include refid="condition"></include>
        <include refid="order_by"></include>
        <#noparse>limit #{start},#{rows}</#noparse>
    </select>

    <!--批量查找  不分页-->
    <select id="queryList" resultMap="BaseResultMap" parameterType="${queryType}">
        select
        <if test="roleId == 1">
            <include refid="admin_field"></include>
        </if>
        <if test="roleId == 2">
            <include refid="teacher_field"></include>
        </if>
        <if test="roleId == 3">
            <include refid="student_field"></include>
        </if>
        <if test="roleId == 4">
            <include refid="not_login_field"></include>
        </if>
        from ${tableName}
        <include refid="condition"></include>
        <include refid="order_by"></include>
    </select>

    <select id="queryById"  resultMap="BaseResultMap" parameterType="${queryType}">
        select
        <if test="roleId == 1">
            <include refid="admin_field"></include>
        </if>
        <if test="roleId == 2">
            <include refid="teacher_field"></include>
        </if>
        <if test="roleId == 3">
            <include refid="student_field"></include>
        </if>
        <if test="roleId == 4">
            <include refid="not_login_field"></include>
        </if>
        from ${tableName}
        where id = <#noparse>#{id}</#noparse>
    </select>

    <select id="queryByIds" resultMap="BaseResultMap" parameterType="${queryType}">
        select
        <if test="roleId == 1">
            <include refid="admin_field"></include>
        </if>
        <if test="roleId == 2">
            <include refid="teacher_field"></include>
        </if>
        <if test="roleId == 3">
            <include refid="student_field"></include>
        </if>
        <if test="roleId == 4">
            <include refid="not_login_field"></include>
        </if>
        from ${tableName}
        <where>
            id in
            <foreach collection="ids" item="item" open="(" close=")" separator=",">
                <#noparse>#{item}</#noparse>
            </foreach>
        </where>
    </select>

    <insert id="save" parameterType="${poType}">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
            <if test="${fieldColumn.fieldName} != null">
                ${fieldColumn.columnName},
            </if>
            </#if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
            <if test="${fieldColumn.fieldName} != null">
                 <#noparse>#{</#noparse>${fieldColumn.fieldName},jdbcType=${fieldColumn.jdbcType}},
            </if>
            </#if>
            </#list>
        </trim>
    </insert>

    <insert id="saveAndReturnId" parameterType="${poType}" useGeneratedKeys="true" keyProperty="id">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
             <if test="${fieldColumn.fieldName} != null">
                 ${fieldColumn.columnName},
             </if>
            </#if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
            <if test="${fieldColumn.fieldName} != null">
                <#noparse>#{</#noparse>${fieldColumn.fieldName},jdbcType=${fieldColumn.jdbcType}},
            </if>
            </#if>
            </#list>
        </trim>
    </insert>

    <insert id="batchSave" parameterType="list">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
            ${fieldColumn.columnName},
            </#if>
            </#list>
        </trim>
        <trim prefix="values">
            <foreach collection="list" item="item" separator=",">
                <trim prefix="("  suffix=")" suffixOverrides=",">
                    <#list fieldColumnList as fieldColumn>
                    <#if fieldColumn.fieldName != "id">
                    <#noparse>#{item.</#noparse>${fieldColumn.fieldName},jdbcType=${fieldColumn.jdbcType}},
                    </#if>
                    </#list>
                </trim>
            </foreach>
        </trim>
    </insert>

    <delete id="deleteById" parameterType="integer">
        delete from ${tableName} where id = <#noparse>#{id}</#noparse>
    </delete>

    <delete id="deleteByIds" parameterType="list">
        delete from ${tableName}
        <where>
            <if test="list.size > 0">
                id in
                <foreach collection="list" open="(" separator="," close=")" item="item">
                    <#noparse>#{item}</#noparse>
                </foreach>
            </if>
            <if test="list.size == 0">
                1 = 0
            </if>
        </where>
    </delete>

    <update id="update" parameterType="${poType}" >
        update ${tableName}
        <set>
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
            <if test="${fieldColumn.fieldName} != null">
                 ${fieldColumn.columnName} = <#noparse>#{</#noparse>${fieldColumn.fieldName},jdbcType=${fieldColumn.jdbcType}},
            </if>
            </#if>
            </#list>
        </set>
        where id = <#noparse>#{id}</#noparse>
    </update>
    
    <update id="batchUpdate" parameterType="map">
        update ${tableName}
        <set>
            <#list fieldColumnList as fieldColumn>
            <#if fieldColumn.fieldName != "id">
            <if test="data.${fieldColumn.fieldName} != null">
                ${fieldColumn.columnName} = <#noparse>#{</#noparse>data.${fieldColumn.fieldName},jdbcType=${fieldColumn.jdbcType}},
            </if>
            </#if>
            </#list>
        </set>
        <where>
            <if test="ids.size() > 0">
                id in
                <foreach collection="ids" open="(" separator="," close=")" item="item">
                    <#noparse>#{item}</#noparse>
                </foreach>
            </if>
            <if test="ids.size() == 0">
                1 = 0
            </if>
        </where>
    </update>

    <select id="count" parameterType="${queryType}" resultType="integer">
        select count(*) from ${tableName}
        <include refid="condition"></include>
    </select>

</mapper>