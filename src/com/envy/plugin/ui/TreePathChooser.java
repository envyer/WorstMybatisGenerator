package com.envy.plugin.ui;

import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author hzqianyizai on 2019/6/14.
 */
public interface TreePathChooser {
    VirtualFile getSelectedFile();
    void showDialog();
}
