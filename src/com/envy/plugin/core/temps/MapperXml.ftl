<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${MapperPackage}.${Mapper}" >
    <resultMap id="BaseResult" type="${DO}" >
    <#list columns as column>
        <result column="${column.columnName}" property="${column.name}"/>
    </#list>
    </resultMap>

    <sql id="TableName">
        ${table}
    </sql>

    <sql id="BaseColumn" >
    <#list columns as column>
        <#if column_index gt 0>
        `${column.columnName}`<#if column_has_next>,</#if>
        </#if>
    </#list>
    </sql>

    <insert id="insert" parameterType="${DO}" keyProperty="${pk.columnName}" useGeneratedKeys="true">
        INSERT INTO
          <include refid="TableName" /> (<include refid="BaseColumn" />)
        VALUES(
        <#list columns as column>
          <#if column_index gt 0>
          <#if column.name == "createTime" || column.name == "updateTime">
          NOW()<#if column_has_next>,</#if>
          <#else>
          ${r"#{"}${column.name}${r"}"}<#if column_has_next>,</#if>
          </#if>
          </#if>
        </#list>
        )
    </insert>

    <select id="getById" resultMap="BaseResult">
        SELECT
          ${pk.name}, <include refid="BaseColumn" />
        FROM
          <include refid="TableName"/>
        WHERE
          id = ${r"#{id}"}
    </select>

    <delete id="deleteById">
        DELETE
        FROM
          <include refid="TableName"/>
        WHERE
          id = ${r"#{id}"}
    </delete>
</mapper>