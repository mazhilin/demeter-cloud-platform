package com.demeter.cloud.core.util;


import com.demeter.cloud.model.exception.BusinessException;
import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * <p>封装Dcloud项目IOUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-09 22:29
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class IOUtil extends IOUtils {
    /**
     * 统一close所有可关闭的对象
     *
     * @param close
     * @throws BusinessException
     */
    public static final void close(Closeable... close) throws BusinessException {
        if (CheckEmptyUtil.isNotEmpty(close)) {
            for (Closeable cl : close) {
                try {
                    if (CheckEmptyUtil.isNotEmpty(cl)) {
                        cl.close();
                    }
                } catch (IOException e) {
                    throw new BusinessException("Closeable object close fail.", e);
                }
            }
        }
    }

    /**
     * 统一flush所有可关闭的对象
     *
     * @param flush
     * @throws BusinessException
     */
    public static final void flush(Flushable... flush) {
        if (CheckEmptyUtil.isNotEmpty(flush)) {
            for (Flushable fl : flush) {
                try {
                    if (CheckEmptyUtil.isNotEmpty(fl)) {
                        fl.flush();
                    }
                } catch (IOException e) {
                    new BusinessException("Flush object close fail.", e);
                }
            }
        }
    }
}
