package com.envy.plugin;

/**
 * @author hzqianyizai on 2019/6/3.
 */
public class Assert {
    public static void isTrue(boolean f, String message) {
        if (!f) {
            throw new AssertException(message);
        }
    }

    public static class AssertException extends RuntimeException {
        public AssertException(String message) {
            super(message);
        }
    }
}
