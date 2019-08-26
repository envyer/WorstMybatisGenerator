package com.envy.plugin.core;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class SpecialColumnHandler {
    public static SpecialColumn resolve(String jdbcType){
        try {
            return SpecialColumn.valueOf(jdbcType);
        } catch (Exception e) {
            // do nothing
        }
        return null;
    }

    /**
     * 根据传入的jdbcType以及sign返回，true表示命中第一次，第二次命中为false
     */
    public static SpecialColumn resolve(String jdbcType, int sign){
        try {
            SpecialColumn column = SpecialColumn.valueOf(jdbcType);
            return column.isSpecial(sign) ? column : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
