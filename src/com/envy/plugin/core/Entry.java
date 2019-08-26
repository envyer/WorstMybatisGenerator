package com.envy.plugin.core;

import com.envy.plugin.core.utils.FileUtils;
import com.envy.plugin.core.utils.StringUtils;

import java.io.File;
import java.util.HashMap;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class Entry extends HashMap<String, Object> {
    private boolean defaultTemp;
    private String tempPath;
    private String tempName;
    private String className;
    private String packagePath;
    private String filePath;
    private DependEntries dependEntries;

    public Entry(String tempPath, boolean defaultTemp, Table table) {
        // 按照这个策略产生map
        super((table.getSize() + 1) * 3);
        this.dependEntries = table.getDependEnties();
        this.defaultTemp = defaultTemp;
        if (defaultTemp || !tempPath.contains(StringUtils.FOLDER_SEPARATOR)) {
            this.tempPath = StringUtils.FOLDER_SEPARATOR;
            this.tempName = tempPath;
        } else {
            int index = tempPath.lastIndexOf(StringUtils.FOLDER_SEPARATOR);
            this.tempPath = tempPath.substring(0, index);
            this.tempName = tempPath.substring(index + 1);
        }
    }

    public boolean isDefaultTemp() {
        return defaultTemp;
    }
    
    public File getTempFile() {
        return FileUtils.getFile(tempPath);
    }
    
    public String getTempName() {
        return tempName;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFilePath() {
        return filePath;
    }

    public DependEntries getDependEntries() {
        return dependEntries;
    }

    public void setJavaFile(String filePath) {
        String path = filePath.substring(filePath.indexOf(StringUtils.FOLDER_SEPARATOR + "java" + StringUtils.FOLDER_SEPARATOR) + 6);
        int index = path.lastIndexOf(StringUtils.FOLDER_SEPARATOR);
        if (index != -1) {
            setPackage(path.substring(0, index));
        }
        setClassName(path.substring(index + 1));
    }

    private void setPackage(String packagePath) {
        packagePath = packagePath.replace(StringUtils.FOLDER_SEPARATOR, StringUtils.CURRENT_PATH);
        this.packagePath = packagePath;
    }

    private void setClassName(String fileName) {
        this.className = fileName.substring(0, fileName.indexOf(StringUtils.CURRENT_PATH));
    }

    public void setBinding(String binding) {
        put(binding, this.className);
        put(binding + ConfigConstants.PACKAGE, this.packagePath);
    }
}
