package com.envy.plugin.ui;

import com.envy.plugin.Assert;
import com.envy.plugin.DefaultTempConfiguration;
import com.envy.plugin.PersistentInput;
import com.envy.plugin.core.config.DataSourceConfig;
import com.envy.plugin.core.config.TableConfig;
import com.envy.plugin.core.config.TableItemConfig;
import com.envy.plugin.core.factory.GenerateFactory;
import com.envy.plugin.core.utils.StringUtils;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBUI;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MybatisGeneratorDialog extends JDialog {
    private Project project;
    private Module[] modules;

    private JPanel contentPane;
    private JTextField jdbcDriverInput;
    private JTextField jdbcUserNameInput;
    private JPasswordField jdbcUserPasswordInput;
    private JTextField jdbcUrlInput;
    private JTextField tableNameInput;
    private JButton addButton;
    private JButton generatorButton;
    private JComboBox<String> mavenModuleInput;
    private JBScrollPane scrollPanel;
    private JPanel itemPanels;

    MybatisGeneratorDialog(Project project) {
        setupUI();
        setContentPane(contentPane);
        setModal(true);

        this.project = project;
        this.modules = ModuleManager.getInstance(project).getModules();

        customSetupUI();
        addDefaultItems();
        restore();

        addGeneratorButtonAction();
        addAddButtonAction();
    }

    public Project getProject() {
        return project;
    }

    public Module[] getModules() {
        return modules;
    }

    public JComboBox<String> getMavenModuleInput() {
        return mavenModuleInput;
    }

    private void customSetupUI() {
        itemPanels = new JPanel();
        itemPanels.setLayout(new VerticalFlowLayout());
        scrollPanel.setViewportView(itemPanels);
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(20);
        scrollPanel.setPreferredSize(new Dimension(300, 615));

        mavenModuleInput.setModel(MavenModulesModel.create(modules));

        jdbcUrlInput.setFocusable(true);
        jdbcUserNameInput.setFocusable(true);
        jdbcUserPasswordInput.setFocusable(true);
        tableNameInput.setFocusable(true);
        mavenModuleInput.setFocusable(true);
    }

    private void addDefaultItems() {
        DefaultTempConfiguration.listDefaultTempBind().forEach(this::appendItemPanel);
        scrollPanelRepaint();
    }

    private void restore() {
        PersistentInput input = PersistentInput.get();
        if (input.isChange()) {
            jdbcUrlInput.setText(input.getJdbcUrl());
            jdbcUserNameInput.setText(input.getJdbcUserName());
            jdbcUserPasswordInput.setText(input.getJdbcUserPassword());
        }
    }

    private void store() {
        PersistentInput.persist(jdbcUrlInput.getText(), jdbcUserNameInput.getText(), String.valueOf(jdbcUserPasswordInput.getPassword()));
    }

    private void addGeneratorButtonAction() {
        generatorButton.addActionListener(listener -> {
            try {
                DataSourceConfig dataSourceConfig = verify();
                TableConfig tableConfig = verifyItems();
                GenerateFactory.create(dataSourceConfig, tableConfig);
                DialogUtils.succ("generate success");
                dispose();
                VirtualFileManager.getInstance().syncRefresh();
            } catch (Exception e) {
                e.printStackTrace();
                DialogUtils.error((e instanceof Assert.AssertException ? e.getMessage() : ExceptionUtils.getStackTrace(e)));
            } finally {
                store();
            }
        });
    }

    private void addAddButtonAction() {
        addButton.addActionListener(listener -> {
            appendItemPanel(null);
            scrollPanelRepaint();
        });
    }

    private void appendItemPanel(String bind) {
        MybatisGeneratorItem itemPanel = new MybatisGeneratorItem(this, panel -> {
            itemPanels.remove(panel);
            scrollPanelRepaint();
        });
        itemPanel.selectTemp(bind);
        itemPanels.add(itemPanel);
    }

    private void scrollPanelRepaint() {
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    private DataSourceConfig verify() {
        String jdbcUrl = jdbcUrlInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(jdbcUrl), "jdbc url can not be null");

        String jdbcDriver = jdbcDriverInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(jdbcDriver), "jdbc driver can not be null");

        String jdbcUserName = jdbcUserNameInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(jdbcUserName), "jdbc user name can not be null");

        String jdbcUserPassword = String.valueOf(jdbcUserPasswordInput.getPassword());
        Assert.isTrue(StringUtils.isNotBlank(jdbcUserPassword), "jdbc user password can not be null");

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setJdbcUrl(jdbcUrl);
        dataSourceConfig.setJdbcDriver(jdbcDriver);
        dataSourceConfig.setJdbcUserName(jdbcUserName);
        dataSourceConfig.setJdbcUserPassword(jdbcUserPassword);
        return dataSourceConfig;
    }

    private TableConfig verifyItems() {
        String tableName = tableNameInput.getText();
        Assert.isTrue(StringUtils.isNotBlank(tableName), "table name can not be null");

        String mavenModule = (String) mavenModuleInput.getSelectedItem();
        Assert.isTrue(StringUtils.isNotBlank(mavenModule), "maven module can not be null");

        Assert.isTrue(itemPanels.getComponentCount() > 0, "at least one item");

        TableConfig tableConfig = new TableConfig();
        tableConfig.setTableName(tableName);

        String domainName = StringUtils.camel(tableName);
        java.util.List<TableItemConfig> items = new ArrayList<>(itemPanels.getComponentCount());
        for (Component component : itemPanels.getComponents()) {
            if (component instanceof MybatisGeneratorItem) {
                items.add(((MybatisGeneratorItem) component).verify(domainName));
            }
        }

        tableConfig.setItems(items);
        return tableConfig;
    }

    private void setupUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(1, 1, JBUI.insets(10), -1, -1));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayoutManager(7, 4, JBUI.emptyInsets(), -1, -1));
        contentPane.add(panel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        JLabel jdbcUrlLabel = new JLabel();
        jdbcUrlLabel.setText("Jdbc Url *");
        panel.add(jdbcUrlLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        jdbcUrlInput = new JTextField();
        jdbcUrlInput.setText("jdbc:mysql://ip:port/database?useUnicode=true&characterEncoding=UTF-8");
        panel.add(jdbcUrlInput, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        JLabel jdbcDriverLabel = new JLabel();
        jdbcDriverLabel.setText("Jdbc Driver *");
        panel.add(jdbcDriverLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        jdbcDriverInput = new JTextField();
        jdbcDriverInput.setText("com.mysql.jdbc.Driver");
        panel.add(jdbcDriverInput, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        JLabel jdbcUserNameLabel = new JLabel();
        jdbcUserNameLabel.setText("Jdbc User Name *");
        panel.add(jdbcUserNameLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        jdbcUserNameInput = new JTextField();
        panel.add(jdbcUserNameInput, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 25), null, 0, false));

        JLabel jdbcUserPasswordLabel = new JLabel();
        jdbcUserPasswordLabel.setText("Jdbc User Password *");
        panel.add(jdbcUserPasswordLabel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jdbcUserPasswordInput = new JPasswordField();
        panel.add(jdbcUserPasswordInput, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        JLabel tableNameLabel = new JLabel();
        tableNameLabel.setText("Table Name *");
        panel.add(tableNameLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        tableNameInput = new JTextField();
        panel.add(tableNameInput, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 25), null, 0, false));

        JLabel mavenModuleLabel = new JLabel();
        mavenModuleLabel.setText("Maven Module *");
        panel.add(mavenModuleLabel, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 17), null, 0, false));
        mavenModuleInput = new ComboBox<>();
        panel.add(mavenModuleInput, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        scrollPanel = new JBScrollPane();
        panel.add(scrollPanel, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        addButton = new JButton();
        addButton.setEnabled(true);
        addButton.setText("add");
        panel.add(addButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        generatorButton = new JButton();
        generatorButton.setEnabled(true);
        generatorButton.setText("generate");
        panel.add(generatorButton, new GridConstraints(6, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
