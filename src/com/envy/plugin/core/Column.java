package com.envy.plugin.core;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class Column {
    private String columnName;
    private String name;
    private String jdbcType;
    private String javaType;
    private boolean wrapClass;
    private String defaultValue;
    private String comment;
    private String setMethod;
    private String getMethod;
    private boolean nullable;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean isWrapClass() {
        return wrapClass;
    }

    public void setWrapClass(boolean wrapClass) {
        this.wrapClass = wrapClass;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(String setMethod) {
        this.setMethod = "set" + setMethod;
    }

    public String getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
