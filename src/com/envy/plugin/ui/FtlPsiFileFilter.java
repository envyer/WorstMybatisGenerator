package com.envy.plugin.ui;

import com.envy.plugin.core.utils.StringUtils;
import com.intellij.ide.util.TreeFileChooser;
import com.intellij.psi.PsiFile;

/**
 * @author hzqianyizai on 2019/6/12.
 */
public class FtlPsiFileFilter implements TreeFileChooser.PsiFileFilter {
    public static final FtlPsiFileFilter INSTANCE = new FtlPsiFileFilter();

    @Override
    public boolean accept(PsiFile psiFile) {
        return StringUtils.equals(psiFile.getVirtualFile().getExtension(), "ftl");
    }
}
