package com.envy.plugin;

/**
 * @author hzqianyizai on 2019/5/31.
 */
public class PersistentInput {
    private static final PersistentInput INSTANT = new PersistentInput();

    private PersistentInput() {
    }

    public static void persist(String jdbcUrl, String jdbcUserName, String jdbcUserPassword) {
        INSTANT.setJdbcUrl(jdbcUrl);
        INSTANT.setJdbcUserName(jdbcUserName);
        INSTANT.setJdbcUserPassword(jdbcUserPassword);
        INSTANT.setChange(true);
    }

    public static PersistentInput get() {
        return INSTANT;
    }

    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcUserPassword;
    private boolean change = false;

    public String getJdbcUrl() {
        return jdbcUrl;
    }
    private void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
    public String getJdbcUserName() {
        return jdbcUserName;
    }
    private void setJdbcUserName(String jdbcUserName) {
        this.jdbcUserName = jdbcUserName;
    }
    public String getJdbcUserPassword() {
        return jdbcUserPassword;
    }
    private void setJdbcUserPassword(String jdbcUserPassword) {
        this.jdbcUserPassword = jdbcUserPassword;
    }
    public boolean isChange() {
        return change;
    }
    public void setChange(boolean change) {
        this.change = change;
    }
}
