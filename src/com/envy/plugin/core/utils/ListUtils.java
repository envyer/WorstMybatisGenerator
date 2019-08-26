package com.envy.plugin.core.utils;

import java.util.List;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public abstract class ListUtils {
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
