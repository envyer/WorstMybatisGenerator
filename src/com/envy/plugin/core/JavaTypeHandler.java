package com.envy.plugin.core;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class JavaTypeHandler {
    public static JavaType getType(String jdbcType) {
        return JavaType.valueOf(jdbcType.toUpperCase());
    }
}
