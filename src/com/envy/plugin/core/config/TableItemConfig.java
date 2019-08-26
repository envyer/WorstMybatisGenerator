package com.envy.plugin.core.config;

/**
 * @author hzqianyizai on 2019/6/3.
 */
public class TableItemConfig {
    private boolean defaultTemp;
    private String tempPath;
    private String bind;
    private boolean injectColumns;
    private String directoryPath;

    public boolean isDefaultTemp() {
        return defaultTemp;
    }

    public void setDefaultTemp(boolean defaultTemp) {
        this.defaultTemp = defaultTemp;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public boolean isInjectColumns() {
        return injectColumns;
    }

    public void setInjectColumns(boolean injectColumns) {
        this.injectColumns = injectColumns;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }
}
