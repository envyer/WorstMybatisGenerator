package com.envy.plugin.ui;

import com.envy.plugin.core.utils.StringUtils;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author hzqianyizai on 2019/6/14.
 */
public class PsiPathFilter implements PathFilter {
    private static final String _SRC = "src";
    private static final String _MAIN = "main";
    private static final String _RESOURCES = "resources";
    private static final String _TEST = "test";
    private MybatisGeneratorItem item;

    public PsiPathFilter(MybatisGeneratorItem item) {
        this.item = item;
    }

    @Override
    public boolean accept(VirtualFile virtualFile) {
        String path = virtualFile.getPath();

        // project path
        if (StringUtils.equals(path, item.getProjectPath())) {
            return true;
        }

        String s = path.substring(path.lastIndexOf(StringUtils.FOLDER_SEPARATOR) + 1);
        // maven path
        String mavenModule = item.getMavenModule();
        if (StringUtils.equals(s, mavenModule)) {
            return true;
        }

        if (!path.contains(mavenModule)) {
            return false;
        }

        if (item.isResource()) {
            // /src, /src/main, /src/main/resources is correct
            return (StringUtils.equals(s, _SRC) || StringUtils.equals(s, _MAIN) || path.contains(_RESOURCES));
        } else if (item.isTest()) {
            // /src, /src/test is correct
            return (StringUtils.equals(s, _SRC) || path.contains(_TEST));
        }

        return true;
    }

    @Override
    public boolean choose(VirtualFile vFile) {
        String path = vFile.getPath();
        if (item.isResource()) {
            return path.contains(_RESOURCES);
        }

        if (item.isTest()) {
            return path.contains(_TEST);
        }

        return true;
    }
}
