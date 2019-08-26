<#if DOPackage??>
package ${DOPackage};
</#if>

<#if hasDate??>
import java.util.Date;
</#if>
<#if hasDecimal??>
import java.math.BigDecimal;
</#if>
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ${DO}{
    <#list columns as column>
    /**
     * ${column.comment}
     */
    private ${column.javaType} ${column.name};

    </#list>
}
