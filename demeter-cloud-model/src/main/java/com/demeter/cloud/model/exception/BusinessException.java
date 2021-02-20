package com.demeter.cloud.model.exception;

/**
 * <p>封装Dcloud项目BusinessException类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 21:33
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class BusinessException extends BaseHandlerException{

    private static final long serialVersionUID = 7670720348542447804L;

    public BusinessException(Exception e) {
        super(e);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }
}
