package com.envy.plugin;

import java.util.*;

/**
 * @author hzqianyizai on 2019/5/31.
 */
public class DefaultTempConfiguration {
    private static final Map<String, DefaultTemp> MAP = new LinkedHashMap<>();

    static {
        List<DefaultTemp> list = new ArrayList<>();

        list.add(new DefaultTemp("DO.ftl", "DO", "DO", true, false, false, "java"));
        list.add(new DefaultTemp("Mapper.ftl", "Mapper", "Mapper", false, false, false, "java"));
        list.add(new DefaultTemp("MapperXml.ftl", "MapperXml", "Mapper", true, true, false, "xml"));

        list.forEach(i -> MAP.put(i.getBind(), i));
    }

    public static Set<String> listDefaultTempBind() {
        return MAP.keySet();
    }

    public static DefaultTemp getTemp(String bind) {
        return MAP.get(bind);
    }
}
