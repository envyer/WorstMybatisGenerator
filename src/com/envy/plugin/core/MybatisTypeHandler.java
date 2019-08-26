package com.envy.plugin.core;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class MybatisTypeHandler {
    public static String getType(String jdbcType){
        jdbcType = jdbcType.toUpperCase();
        int i = jdbcType.indexOf(" ");
        if(i > -1){
            jdbcType = jdbcType.substring(0, i);
        }
        try {
            MybatisType mybatisType = MybatisType.valueOf(jdbcType);
            return mybatisType.getJdbcType();
        } catch (Exception e) {
            return jdbcType;
        }
    }
}
