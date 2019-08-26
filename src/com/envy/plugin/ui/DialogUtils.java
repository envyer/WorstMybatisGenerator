package com.envy.plugin.ui;

import com.intellij.openapi.ui.Messages;

/**
 * @author hzqianyizai on 2019/5/31.
 */
public class DialogUtils {
    public static void succ(String message) {
        Messages.showInfoMessage(message, "Info");
    }
    public static void error(String message) {
        Messages.showErrorDialog(message, "Error");
    }
}
