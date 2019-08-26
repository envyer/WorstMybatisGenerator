package com.envy.plugin.core;

import com.envy.plugin.core.utils.StringUtils;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public enum JavaType {
    STRING("String"),
    CHAR("String"),
    VARCHAR("String"),
    LONGVARCHAR("String"),
    TEXT("String"),
    MEDIUMTEXT("String"),
    LONGTEXT("String"),
    NUMERIC("BigDecimal"),
    DECIMAL("BigDecimal"),
    BIT("boolean"),
    TINYINT("int", "Integer"),
    SMALLINT("Short", "Integer"),
    INTEGER("int", "Integer"),
    BIGINT("long", "Long"),
    NUMBER("long", "Long"),
    FLOAT("Double"),
    DOUBLE("Double"),
    BINARY("byte[]"),
    VARBINARY("byte[]"),
    LONGVARBINARY("byte[]"),
    BLOB("byte[]"),
    CLOB("String"),
    DATE("Date"),
    DATETIME("Date"),
    TIME("Date"),
    TIMESTAMP("Date"),
    INT("int", "Integer");

    private String javaType;

    private String javaPackageType;

    private JavaType(String javaType) {
        this.javaType = javaType;
    }

    private JavaType(String javaType, String javaPackageType) {
        this.javaType = javaType;
        this.javaPackageType = javaPackageType;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getJavaPackageType() {
        return javaPackageType;
    }

    public boolean isWrapClass() {
        return StringUtils.isNotEmpty(javaPackageType);
    }
}
