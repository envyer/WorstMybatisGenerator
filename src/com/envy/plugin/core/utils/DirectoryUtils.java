package com.envy.plugin.core.utils;

import com.envy.plugin.core.exception.DirectoryException;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public abstract class DirectoryUtils {
    private static final String CLASSPATH_URL_PREFIX = "classpath:";

    public static String getPathToUse(String path){
        if(StringUtils.isEmpty(path)){
            throw new DirectoryException("path is null...");
        }
        String pathToUse = path;
        if(pathToUse.startsWith(CLASSPATH_URL_PREFIX)) {
            pathToUse = pathToUse.substring(CLASSPATH_URL_PREFIX.length());
        }
        pathToUse = StringUtils.cleanPath(pathToUse);
        if (pathToUse.startsWith(StringUtils.FOLDER_SEPARATOR)) {
            pathToUse = pathToUse.substring(1);
        }
        return pathToUse;
    }
}
