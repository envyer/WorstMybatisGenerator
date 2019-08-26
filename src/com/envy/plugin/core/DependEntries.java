package com.envy.plugin.core;

import java.util.HashMap;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class DependEntries extends HashMap<String, Object> {

    public DependEntries(int initialCapacity) {
        super(initialCapacity);
    }

    public void addEnty(com.envy.plugin.core.Entry enty) {
        putAll(enty);
    }
}
