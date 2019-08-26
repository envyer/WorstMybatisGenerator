package com.envy.plugin.core;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public enum MybatisType {
    INT("INTEGER"),
    DATETIME("TIMESTAMP");

    private String jdbcType;

    private MybatisType(String jdbcType) {
        this.setJdbcType(jdbcType);
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
