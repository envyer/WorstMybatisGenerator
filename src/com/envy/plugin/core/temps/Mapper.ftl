<#if MapperPackage??>
package ${MapperPackage};
</#if>

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ${DOPackage}.${DO};

@Mapper
public interface ${Mapper}{
    <#assign dO = "${DO?uncap_first}" />
    void insert(${DO} ${dO});
    ${DO} getById(@Param("id") ${pk.javaType} id);
    int deleteById(@Param("id") ${pk.javaType} id);
}