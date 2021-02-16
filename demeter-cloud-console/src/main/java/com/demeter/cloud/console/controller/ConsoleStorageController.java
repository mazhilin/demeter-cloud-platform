package com.demeter.cloud.console.controller;

import com.demeter.cloud.core.storage.StorageServerService;
import com.demeter.cloud.model.persistence.controller.BaseController;
import com.demeter.cloud.model.service.StorageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>封装Qicloud项目ConsoleStorageController类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:12
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
@RestController
@RequestMapping(value = "/console/storage/")
@Validated
public class ConsoleStorageController extends BaseController {
    @Autowired
    private StorageServerService storageService;
    @Autowired
    private StorageFileService fileService;


}
