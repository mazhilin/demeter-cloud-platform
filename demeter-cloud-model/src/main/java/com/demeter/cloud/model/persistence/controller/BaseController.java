package com.demeter.cloud.model.persistence.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>封装Dcloud项目BaseController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 23:00
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public abstract class BaseController implements Controller{
    protected  final Logger logger = LoggerFactory.getLogger(this.getClass());
}
