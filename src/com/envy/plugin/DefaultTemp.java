package com.envy.plugin;

/**
 * @author hzqianyizai on 2019/5/31.
 */
public class DefaultTemp {
    private String path;
    private String bind;
    private String identify;
    private String extension;
    private boolean injectColumns;
    private boolean resource;
    private boolean test;

    DefaultTemp(String path, String bind, String identify, boolean injectColumns, boolean resource, boolean test, String extension) {
        this.path = path;
        this.bind = bind;
        this.identify = identify;
        this.extension = extension;
        this.injectColumns = injectColumns;
        this.resource = resource;
        this.test = test;
    }

    public String getPath() {
        return path;
    }
    public String getBind() {
        return bind;
    }
    public String getIdentify() {
        return identify;
    }
    public String getExtension() {
        return extension;
    }
    public boolean isInjectColumns() {
        return injectColumns;
    }
    public boolean isResource() {
        return resource;
    }
    public boolean isTest() {
        return test;
    }
}
