package com.demeter.cloud.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>封装Dcloud项目ObjectUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-04 11:23
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public final class ObjectUtil {
    /**
     * 辅助方法 数组元素 + 前缀 + 分隔符 然后拼接起来 如 参数分别是 [AA,BB,CC], TDD, -- 返回结果是
     * TDDAA--TDDBB--TDDCC
     *
     * @param array
     * @param prefix
     * @return
     */
    public static <T> String join(List<T> array, String prefix, String joinStr) {
        if (array == null || prefix == null || joinStr == null) {
            return "";
        }
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            if (i == array.size() - 1) {
                buff.append(prefix + array.get(i));
            } else {
                buff.append(prefix + array.get(i) + joinStr);
            }
        }
        return buff.toString();
    }

    public static <T> String joinStr(List<T> array, String joinStr) {
        if (array == null || joinStr == null) {
            return "";
        }
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            if (i == array.size() - 1) {
                buff.append("'" + array.get(i) + "'");
            } else {
                buff.append("'" + array.get(i) + "'" + joinStr);
            }
        }
        return buff.toString();
    }

    /**
     * 辅助方法 数组元素 + 前缀 然后返回 如 参数分别是 [AA,BB,CC], TDD 返回结果是 [TDDAA,TDDBB,TDDCC]
     *
     * @param array
     * @param prefix
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> joinPrefix(List<T> array, String prefix) {
        if (array == null || prefix == null) {
            return new ArrayList<>();
        }
        List<T> newArray = new ArrayList<T>(array.size());
        for (T t : array) {
            newArray.add((T) (prefix + t));
        }
        return newArray;
    }
}
