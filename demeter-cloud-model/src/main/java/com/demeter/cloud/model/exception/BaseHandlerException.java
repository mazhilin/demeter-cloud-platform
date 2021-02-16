package com.demeter.cloud.model.exception;

/**
 * <p>封装Qicloud项目BaseHandlerException类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 21:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public abstract class BaseHandlerException extends RuntimeException {
    private static final long serialVersionUID = -4552090901512143756L;

    public BaseHandlerException() {
        super();
    }

    public BaseHandlerException(final String message) {
        super(message);
    }

    public BaseHandlerException(Exception e) {
        super(e);
    }

    public BaseHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseHandlerException(Throwable cause) {
        super(cause);
    }
}
