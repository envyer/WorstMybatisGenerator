package com.envy.plugin.core.config;

/**
 * @author hzqianyizai on 2019/6/3.
 */
public class DataSourceConfig {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcUserPassword;

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUserName() {
        return jdbcUserName;
    }

    public void setJdbcUserName(String jdbcUserName) {
        this.jdbcUserName = jdbcUserName;
    }

    public String getJdbcUserPassword() {
        return jdbcUserPassword;
    }

    public void setJdbcUserPassword(String jdbcUserPassword) {
        this.jdbcUserPassword = jdbcUserPassword;
    }
}
