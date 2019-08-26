package com.envy.plugin.core;

import com.envy.plugin.core.utils.CharEncoding;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.util.List;
import java.util.Locale;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public abstract class AbstractGenerator implements IGenerator {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
    private EntryParser entryParse = new EntryParser();

    static {
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding(CharEncoding.UTF_8);
        cfg.setEncoding(Locale.CHINA, CharEncoding.UTF_8);
        cfg.setDateFormat("yyyy-MM-dd HH:mm:ss");
        cfg.unsetCacheStorage();
    }

    /**
     * 单个table的所有模板生成方法
     * 可以继承重写
     */
    @Override
    public void generate(Table table) throws Exception {
        List<Entry> entries = entryParse.initEntries(table);
        generateTemps(entries);
    }

    /**
     * 遍历所有的entries
     */
    private void generateTemps(List<Entry> entries) throws Exception {
        int size = entries.size();
        for (int i = 0; i < size; i++) {
            generateTemp(entries.get(i));
        }
    }

    /**
     * 单个模板生成方法
     */
    private void generateTemp(Entry entry) throws Exception {
        if (entry.isDefaultTemp()) {
            cfg.setClassForTemplateLoading(getClass(), "temps");
        } else {
            cfg.setDirectoryForTemplateLoading(entry.getTempFile());
        }
        Template template = cfg.getTemplate(entry.getTempName());
        create(template, entry);
    }

    abstract void create(Template template, Entry entry) throws Exception;
}
