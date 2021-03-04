package com.demeter.cloud.core.storage;

import com.demeter.cloud.core.util.CharUtil;
import com.demeter.cloud.core.util.IpAddressUtil;
import com.demeter.cloud.model.entity.StorageFile;
import com.demeter.cloud.model.service.StorageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * <p>封装Dcloud项目StorageServerService类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 01:08
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class StorageServerService {
    private String active;
    private Storage storage;
    @Autowired
    private StorageFileService storageService;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * 存储一个文件对象
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param fileName      文件索引名
     */
    public String store(
            InputStream inputStream, long contentLength, String contentType, String fileName, HttpServletRequest request) {
        String key = generateKey(fileName);
        storage.store(inputStream, contentLength, contentType, key);

        String url = generateUrl(key);
        StorageFile storageFile = new StorageFile();
        storageFile.setStorageMode(active);
        storageFile.setName(fileName);
        storageFile.setSize((int) contentLength);
        storageFile.setContentType(contentType);
        storageFile.setKey(key);
        storageFile.setUrl(url);
        storageFile.setIpAddress(IpAddressUtil.getIpAddress(request));
        storageService.add(storageFile);

        return url;
    }

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        Storage storageInfo = null;

        do {
            key = CharUtil.getRandomString(20) + suffix;
            storageInfo = (Storage) storageService.queryFileByKey(key);
        } while (storageInfo != null);

        return key;
    }

    public Stream<Path> loadAll() {
        return storage.loadAll();
    }

    public Path load(String keyName) {
        return storage.load(keyName);
    }

    public Resource loadAsResource(String keyName) {
        return storage.loadAsResource(keyName);
    }

    public void delete(String keyName) {
        storage.delete(keyName);
    }

    private String generateUrl(String keyName) {
        return storage.generateUrl(keyName);
    }
}
