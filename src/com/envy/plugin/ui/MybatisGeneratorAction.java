package com.envy.plugin.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;

/**
 * @author hzqianyizai on 2019/5/9.
 */
public class MybatisGeneratorAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(LangDataKeys.PROJECT);
        MybatisGeneratorDialog dialog = new MybatisGeneratorDialog(project);
        dialog.pack();

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
