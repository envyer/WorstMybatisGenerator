package com.envy.plugin.core.exception;

/**
 * @author hzqianyizai on 2017/5/20.
 */
public class DirectoryException extends RuntimeException{
    public DirectoryException() {
        super();
    }

    public DirectoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryException(String message) {
        super(message);
    }

    public DirectoryException(Throwable cause) {
        super(cause);
    }
}
