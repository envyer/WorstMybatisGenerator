package com.envy.plugin.core.utils;

import java.io.*;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public abstract class FileUtils {
    public static File getFile(String path){
        return new File(DirectoryUtils.getPathToUse(path));
    }

    public static Writer writer(String path) throws IOException {
        File file = create(path);
        return new OutputStreamWriter(new FileOutputStream(file), CharEncoding.UTF_8);
    }

    private static File create(String path) throws IOException {
        File file = new File(path);
        if(file.exists()){
            throw new IOException("文件: " + path + " 已经存在");
        }

        File parentFile = file.getParentFile();
        if(!parentFile.exists() && !parentFile.mkdir()){
            throw new IOException("目录: " + path + "创建失败");
        }

        if (!file.createNewFile()) {
            throw new IOException("文件:" + path + " 创建失败或者打开失败.");
        }

        return file;
    }
}
