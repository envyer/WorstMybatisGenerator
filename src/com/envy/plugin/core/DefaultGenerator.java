package com.envy.plugin.core;

import com.envy.plugin.core.utils.TemplateUtil;
import freemarker.template.Template;

public class DefaultGenerator extends AbstractGenerator{
	@Override
	void create(Template template, Entry entry) throws Exception {
		TemplateUtil.create(template, entry);
	}
}
