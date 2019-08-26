package com.envy.plugin.core.utils;

import com.envy.plugin.core.Entry;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;

import java.io.Writer;

public class TemplateUtil {
	public static void create(Template template, Entry entry) throws Exception {
		Writer writer = null;
		try {
			writer = FileUtils.writer(entry.getFilePath());
			template.process(entry.getDependEntries(), writer);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
}
