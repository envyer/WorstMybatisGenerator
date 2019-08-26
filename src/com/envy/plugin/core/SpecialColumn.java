package com.envy.plugin.core;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public enum SpecialColumn {
    // 后续添加注意，请在之前的基础上前移一位1
    // 01
    Date(1, "hasDate"),
    // 10
    BigDecimal(2, "hasDecimal");

    private int sign;
    private String key;

    SpecialColumn(int sign, String key){
        this.sign = sign;
        this.key = key;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isSpecial(int sign){
        return (this.sign & sign) != this.sign;
    }
}
