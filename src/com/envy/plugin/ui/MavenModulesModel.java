package com.envy.plugin.ui;

import com.intellij.openapi.module.Module;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author hzqianyizai on 2019/6/13.
 */
public class MavenModulesModel {
    public static DefaultComboBoxModel<String> create(Module[] modules) {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement("");
        Arrays.stream(modules).forEach(module -> comboBoxModel.addElement(module.getName()));
        return comboBoxModel;
    }
}
