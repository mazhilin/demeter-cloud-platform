package com.demeter.cloud.framework.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>封装Dcloud项目CheckEmptyUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 23:19
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class CheckEmptyUtil {
    /**
     * 判断一个参数是否为空。判断的时候根据不同的类型，会有不同的判断标准。
     *
     * @param object 待校验参数
     * @return 如果object为null，不进行类型判断，直接返回为true. 如果object不为null，则针对不同的class，分别进行判断。 String:
     *     trim后length为０的时候返回true. List:　返回list.isEmpty(). Map: 返回map.isEmpty(). Array:
     *     如果length为０，返回true. Set:　返回set.isEmpty(). CharSequence: length为0或者toString后trim为空，则返回true.
     * @author Marklin
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            String str = (String) object;
            if (str.trim().length() == 0) {
                return true;
            }
        }

        if (object instanceof CharSequence) {
            CharSequence cs = (CharSequence) object;
            if (cs.length() == 0) {
                return true;
            }

            if (cs.toString().trim().length() == 0) {
                return true;
            }
        }

        if (object instanceof List<?>) {
            List<?> list = (List<?>) object;
            return list.isEmpty();
        }

        if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            if (array.length == 0) {
                return true;
            }
        }

        if (object instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) object;
            return map.isEmpty();
        }

        if (object instanceof Set<?>) {
            Set<?> set = (Set<?>) object;
            return set.isEmpty();
        }

        return false;
    }

    /**
     * 判断对象是否非空，其实现为isEmpty取反
     *
     * @param object 待校验参数
     * @return
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 如果传入的所有参数中任意一个为空，则返回true.
     *
     * @param objects 待校验参数
     * @return
     */
    public static boolean isOrEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }

        for (Object object : objects) {
            if (isEmpty(object)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param objects 对象数组
     * @return boolean
     * @apiNote 判断多个
     * @author yepk
     * @date 2019/5/7 9:49
     */
    public static boolean isOrNotEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        for (Object object : objects) {
            if (isNotEmpty(object)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAndEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }
        for (Object object : objects) {
            if (isNotEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 只要其中一个对象是empty，返回true，否则返回false
     *
     * @param o
     * @return
     */
    public static final boolean isAnyEmpty(Object... o) {
        for (int i = 0; i < o.length; i++) {
            Object obj = o[i];
            if (CheckEmptyUtil.isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAndNotEmpty(Object... objects) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        for (Object object : objects) {
            if (isEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 强化trim()
     *
     * @param val
     * @return
     */
    public static final String trim(String val) {
        if (isEmpty(val)) {
            return "";
        } else {
            return val.trim();
        }
    }

    /**
     * 将字符串的首字母变大写
     *
     * @param s
     * @return
     */
    public static String upperCaseFirstChar(String s) {
        if (CheckEmptyUtil.isEmpty(s)) {
            return "";
        }
        return s.substring(0, 1).toString().toUpperCase() + s.substring(1).toString();
    }

    /**
     * 将非法且不可见的XML字符转为空格（空格的的ascii码是32）
     *
     * @param bts byte[]
     * @return 转换后的 byte[]
     */
    public static final byte[] convertInvalidInvisibleToSpace(byte[] bytes) {
        if (isEmpty(bytes)) {
            return new byte[0];
        }
        byte[] tmp = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b >= 0 && b <= 8 || b >= 14 && b <= 31 || b == 11 || b == 12 || b == 127) {
                tmp[i] = 32;
            } else {
                tmp[i] = bytes[i];
            }
        }
        return tmp;
    }

    /** 将非法且不可见的XML字符转为空格 */
    public static final String convertInvalidInvisibleToSpace(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return new String(convertInvalidInvisibleToSpace(str.getBytes()));
    }
}
