package com.envy.plugin.ui;

import com.envy.plugin.ui.PathFilter;
import com.envy.plugin.ui.TreePathChooser;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.projectView.BaseProjectTreeBuilder;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.impl.AbstractProjectTreeStructure;
import com.intellij.ide.projectView.impl.ProjectAbstractTreeStructureBase;
import com.intellij.ide.projectView.impl.ProjectTreeBuilder;
import com.intellij.ide.util.treeView.AlphaComparator;
import com.intellij.ide.util.treeView.NodeRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.TabbedPaneWrapper;
import com.intellij.ui.TreeSpeedSearch;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public final class TreePathChooserDialog extends DialogWrapper implements TreePathChooser {
    private Tree myTree;
    private VirtualFile mySelectedFile = null;
    private final Project myProject;
    private BaseProjectTreeBuilder myBuilder;
    @Nullable private final PathFilter myFilter;

    public TreePathChooserDialog(@NotNull Project project,
                                 String title,
                                 @Nullable PathFilter filter) {
        super(project, true);
        myFilter = filter;
        setTitle(title);
        myProject = project;
        init();

        SwingUtilities.invokeLater(this::handleSelectionChanged);
    }

    @Override
    protected JComponent createCenterPanel() {
        final DefaultTreeModel model = new DefaultTreeModel(new DefaultMutableTreeNode());
        myTree = new Tree(model);

        final ProjectAbstractTreeStructureBase treeStructure = new AbstractProjectTreeStructure(myProject) {
            @Override
            public boolean isFlattenPackages() {
                return false;
            }

            @Override
            public boolean isShowMembers() {
                return false;
            }

            @Override
            public boolean isHideEmptyMiddlePackages() {
                return true;
            }

            @NotNull
            @Override
            public Object[] getChildElements(@NotNull final Object element) {
                return filterFiles(super.getChildElements(element));
            }

            @Override
            public boolean isAbbreviatePackageNames() {
                return false;
            }

            @Override
            public boolean isShowLibraryContents() {
                return false;
            }

            @Override
            public boolean isShowModules() {
                return false;
            }

            @Override
            public List<TreeStructureProvider> getProviders() {
                return super.getProviders();
            }
        };
        myBuilder = new ProjectTreeBuilder(myProject, myTree, model, AlphaComparator.INSTANCE, treeStructure);

        myTree.setRootVisible(false);
        myTree.expandRow(0);
        myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        myTree.setCellRenderer(new NodeRenderer());

        final JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myTree);
        scrollPane.setPreferredSize(JBUI.size(500, 300));

        myTree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    doOKAction();
                }
            }
        });

        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(MouseEvent e) {
                final TreePath path = myTree.getPathForLocation(e.getX(), e.getY());
                if (path != null && myTree.isPathSelected(path)) {
                    return true;
                }
                return false;
            }
        }.installOn(myTree);

        myTree.addTreeSelectionListener(e -> handleSelectionChanged());

        new TreeSpeedSearch(myTree);

        TabbedPaneWrapper myTabbedPane = new TabbedPaneWrapper(getDisposable());
        myTabbedPane.addTab(IdeBundle.message("tab.chooser.project"), scrollPane);
        myTabbedPane.addChangeListener(e -> handleSelectionChanged());

        return myTabbedPane.getComponent();
    }

    private void handleSelectionChanged(){
        VirtualFile virtualFile = calcSelectedVirtualFile();
        if (virtualFile != null && myFilter != null) {
            setOKActionEnabled(myFilter.choose(virtualFile));
        }
    }

    @Override
    public VirtualFile getSelectedFile() {
        return mySelectedFile;
    }

    @Override
    protected void doOKAction() {
        mySelectedFile = calcSelectedVirtualFile();
        if (mySelectedFile == null) return;
        super.doOKAction();
    }

    @Override
    public void doCancelAction() {
        mySelectedFile = null;
        super.doCancelAction();
    }

    @Override
    public void showDialog() {
        show();
    }

    private VirtualFile calcSelectedVirtualFile() {
        final TreePath path = myTree.getSelectionPath();
        if (path == null) return null;
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
        final Object userObject = node.getUserObject();
        if (!(userObject instanceof ProjectViewNode)) return null;
        ProjectViewNode pvNode = (ProjectViewNode) userObject;
        return pvNode.getVirtualFile();
    }


    @Override
    public void dispose() {
        if (myBuilder != null) {
            Disposer.dispose(myBuilder);
            myBuilder = null;
        }
        super.dispose();
    }

    @Override
    protected String getDimensionServiceKey() {
        return "#com.intellij.ide.util.TreePathChooserDialog";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return myTree;
    }

    private Object[] filterFiles(final Object[] list) {
        Condition<VirtualFile> condition = node -> !(myFilter != null && !myFilter.accept(node));
        final List<Object> result = new ArrayList<>(list.length);
        for (Object o : list) {
            if (!(o instanceof ProjectViewNode)) {
                continue;
            }

            final ProjectViewNode projectViewNode = (ProjectViewNode) o;
            VirtualFile virtualFile = projectViewNode.getVirtualFile();
            if (virtualFile == null
                    || !virtualFile.isDirectory()
                    || !condition.value(virtualFile)) {
                continue;
            }

            result.add(o);
        }
        return ArrayUtil.toObjectArray(result);
    }
}
