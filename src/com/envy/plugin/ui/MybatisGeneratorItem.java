package com.envy.plugin.ui;

import com.envy.plugin.Assert;
import com.envy.plugin.DefaultTemp;
import com.envy.plugin.DefaultTempConfiguration;
import com.envy.plugin.core.config.TableItemConfig;
import com.envy.plugin.core.utils.StringUtils;
import com.intellij.ide.util.TreeFileChooser;
import com.intellij.ide.util.TreeFileChooserFactory;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.ui.JBColor;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.function.Consumer;

/**
 * @author hzqianyizai on 2019/5/31.
 */
public class MybatisGeneratorItem extends JPanel {
    private String projectPath;
    private String mavenModule;
    private boolean resource;
    private boolean test;

    private JTextField bindInput;
    private String identify;
    private JTextField extensionInput;
    private ComboBox<String> overrideMavenModuleInput;
    private ComboBox<String> tempInput;
    private JCheckBox injectColumnsInput;
    private JCheckBox isResourceInput;
    private JCheckBox isTestInput;
    private JTextField directoryInput;

    MybatisGeneratorItem(MybatisGeneratorDialog dialog, Consumer<JPanel> deleteConsumer) {
        setLayout(new GridLayoutManager(9, 3, JBUI.insets(5), -1, -1));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(JBColor.GRAY), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font(getFont().getName(), getFont().getStyle(), getFont().getSize()), JBColor.GRAY));

        JLabel tempLabel = new JLabel();
        tempLabel.setText("Temp *");
        add(tempLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tempInput = new ComboBox<>();
        tempInput.setEditable(true);
        DefaultComboBoxModel<String> comboBoxModel = addComboBoxModel();
        tempInput.setModel(comboBoxModel);
        tempInput.addItemListener(this::tempInputItemChange);
        add(tempInput, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        CustomJButton tempChooseButton = new CustomJButton("Select");
        tempChooseButton.addActionListener(e -> showChildDialog(dialog, d -> {
            TreeFileChooserFactory factory = TreeFileChooserFactory.getInstance(d.getProject());
            TreeFileChooser fileChooser = factory.createFileChooser("Temp Select", null, null, FtlPsiFileFilter.INSTANCE);
            fileChooser.showDialog();
            PsiFile selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                tempInput.setSelectedItem(selectedFile.getVirtualFile().getPath());
            }
        }));
        add(tempChooseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(100, 100), 0, false));

        JLabel overrideMavenModuleLabel = new JLabel();
        overrideMavenModuleLabel.setText("Maven Module");
        add(overrideMavenModuleLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        overrideMavenModuleInput = new ComboBox<>();
        overrideMavenModuleInput.addItemListener(this::moduleInputItemChange);
        add(overrideMavenModuleInput, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel bindLabel = new JLabel();
        bindLabel.setText("Bind *");
        add(bindLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bindInput = new JTextField();
        add(bindInput, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel extensionLabel = new JLabel();
        extensionLabel.setText("Extension *");
        add(extensionLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extensionInput = new JTextField();
        add(extensionInput, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel injectColumnsLabel = new JLabel();
        injectColumnsLabel.setText("Inject Columns");
        add(injectColumnsLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        injectColumnsInput = new JCheckBox();
        injectColumnsInput.setText("");
        add(injectColumnsInput, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel isResourceLabel = new JLabel();
        isResourceLabel.setText("Is Resource");
        add(isResourceLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isResourceInput = new JCheckBox();
        isResourceInput.setText("");
        isResourceInput.addChangeListener(e -> resetDirectory());
        JLabel isResourceDescription = new JLabel();
        isResourceDescription.setForeground(JBColor.GRAY);
        isResourceDescription.setText("file will generate under resources directory");
        add(isResourceInput, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        add(isResourceDescription, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel isTestLabel = new JLabel();
        isTestLabel.setText("Is Test");
        add(isTestLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        isTestInput = new JCheckBox();
        isTestInput.setText("");
        isTestInput.addChangeListener(e -> resetDirectory());
        add(isTestInput, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLabel isTestDescription = new JLabel();
        isTestDescription.setForeground(JBColor.GRAY);
        isTestDescription.setText("file will generate under test directory");
        add(isTestDescription, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        JLabel directoryLabel = new JLabel();
        directoryLabel.setText("Directory *");
        add(directoryLabel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        directoryInput = new JTextField();
        directoryInput.setText("");
        directoryInput.setEditable(false);
        add(directoryInput, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, directoryInput.getPreferredSize(), null, 0, false));

        CustomJButton directoryChooseButton = new CustomJButton("Select");
        directoryChooseButton.addActionListener(e -> {
            try {
                verifyModule(dialog);

                showChildDialog(dialog, d -> {
                    TreePathChooserDialog pathChooser = new TreePathChooserDialog(d.getProject(), "Directory Select", new PsiPathFilter(this));
                    pathChooser.showDialog();
                    VirtualFile selectedFile = pathChooser.getSelectedFile();
                    if (selectedFile != null) {
                        directoryInput.setText(selectedFile.getPath());
                    }
                });
            } catch (Assert.AssertException assertException) {
                DialogUtils.error(assertException.getMessage());
            }
        });
        add(directoryChooseButton, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(100, 100), 0, false));

        overrideMavenModuleInput.setModel(MavenModulesModel.create(dialog.getModules()));

        CustomJButton deleteButton = new CustomJButton("Delete");
        deleteButton.addActionListener(listener -> deleteConsumer.accept(this));
        add(deleteButton, new GridConstraints(8, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        setFocusable();
    }

    private void showChildDialog(MybatisGeneratorDialog dialog, Consumer<MybatisGeneratorDialog> consumer) {
        dialog.setVisible(false);
        consumer.accept(dialog);
        dialog.setVisible(true);
    }

    private void verifyModule(MybatisGeneratorDialog dialog) {
        String mavenModule = (String) overrideMavenModuleInput.getSelectedItem();
        if (StringUtils.isBlank(mavenModule)) {
            mavenModule = (String) dialog.getMavenModuleInput().getSelectedItem();
        }
        Assert.isTrue(StringUtils.isNotBlank(mavenModule), "choose maven module first");

        this.projectPath = dialog.getProject().getBasePath();
        this.mavenModule = mavenModule;
        this.resource = isResourceInput.isSelected();
        this.test = isTestInput.isSelected();
        Assert.isTrue(!(this.resource && this.test), "only choose one, either resource or test");
    }

    private void setFocusable() {
        overrideMavenModuleInput.setFocusable(true);
        bindInput.setFocusable(true);
        extensionInput.setFocusable(true);
    }

    private DefaultComboBoxModel<String> addComboBoxModel() {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement("");
        DefaultTempConfiguration.listDefaultTempBind().forEach(comboBoxModel::addElement);
        return comboBoxModel;
    }

    TableItemConfig verify(String domainName) {
        String temp = (String) tempInput.getSelectedItem();
        Assert.isTrue(StringUtils.isNotBlank(temp), "temp can not be null");

        String bind = bindInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(bind), "bind can not be null");

        String extension = extensionInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(extension), "extension can not be null");

        String directory = directoryInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(directory), "directory can not be null");

        TableItemConfig item = new TableItemConfig();
        DefaultTemp defaultTemp = DefaultTempConfiguration.getTemp(temp);
        if (defaultTemp != null) {
            item.setDefaultTemp(true);
            item.setTempPath(defaultTemp.getPath());
        } else {
            item.setTempPath(temp);
        }
        item.setBind(bind);
        item.setInjectColumns(injectColumnsInput.isSelected());
        item.setDirectoryPath(getDirectoryPath(directory, domainName, (identify == null ? bind : identify), extension));
        return item;
    }

    private void tempInputItemChange(ItemEvent itemEvent) {
        if (itemEvent.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        String value = (String) itemEvent.getItem();
        DefaultTemp temp;
        if (StringUtils.isBlank(value) || (temp = DefaultTempConfiguration.getTemp(value)) == null) {
            bindInput.setEnabled(true);
            identify = null;
            extensionInput.setEnabled(true);
            injectColumnsInput.setEnabled(true);
            isResourceInput.setEnabled(true);
            isTestInput.setEnabled(true);
            return;
        }

        bindInput.setEnabled(false);
        bindInput.setText(temp.getBind());
        identify = temp.getIdentify();

        extensionInput.setEnabled(false);
        extensionInput.setText(temp.getExtension());

        injectColumnsInput.setEnabled(false);
        injectColumnsInput.setSelected(temp.isInjectColumns());

        isResourceInput.setEnabled(false);
        isResourceInput.setSelected(temp.isResource());

        isTestInput.setEnabled(false);
        isTestInput.setSelected(temp.isTest());
    }

    private void moduleInputItemChange(ItemEvent itemEvent) {
        resetDirectory();
    }

    private void resetDirectory() {
        directoryInput.setText("");
    }

    private String getDirectoryPath(String directory, String domainName, String identify, String extension) {
        return directory + StringUtils.FOLDER_SEPARATOR + domainName + identify + StringUtils.CURRENT_PATH + extension;
    }

    public void selectTemp(String bind) {
        tempInput.setSelectedItem(bind);
    }

    public String getProjectPath() {
        return projectPath;
    }
    public String getMavenModule() {
        return mavenModule;
    }
    public boolean isResource() {
        return resource;
    }
    public boolean isTest() {
        return test;
    }
}
