package com.demeter.cloud.core.util;

import com.demeter.cloud.core.constant.Constants;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>封装Qicloud项目GenerateCodeUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-07 22:51
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class GenerateCodeUtil {
    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)
     */
    private static final char[] hexChars = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};

    /**
     * (不能与自定义进制有重复)
     */
    private static final char noneHex = 'o';

    /**
     * 进制长度
     */
    private static final Integer hexLength = hexChars.length;

    /**
     * 序列最小长度
     */
    private static final Integer minLength = 6;
    private static int sequence = 0;

    /**
     * @param length    字符长度
     * @param isNumeric
     * @return
     * @description:生成随机字符串
     */
    public static String randomString(int length, boolean isNumeric) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (isNumeric) {
            base = "0123456789";
        }
        Random random = new Random();
        StringBuffer buffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            buffer.append(base.charAt(random.nextInt(base.length())));
        }
        return buffer.toString();
    }

    /**
     * 不重复的参数进行拼装，返回查询条件字符串
     *
     * @param parameters 参数map
     * @param sort       是否按照字典排序
     * @return
     */
    public static String generateQueryString(Map<String, Object> parameters, boolean sort) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (!"".equals(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue());
            }
        }
        String[] arrayToSort = list.toArray(new String[list.size()]);
        if (sort) {
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(arrayToSort[i]);
            if (i < (list.size() - 1)) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * 根据参数获得相关签名
     *
     * @param buffer  加密参数，ASCII 码从小到大排序（字典序）
     * @param encrypt 加密方式 SHA1 MD5
     * @return
     */
    public static String signature(String buffer, String encrypt, boolean toUpperCase) {
        String sign = "";
        if ("MD5".equals(encrypt)) {
            // MD5加密
            sign = Hashing.md5().hashString(buffer, Charsets.UTF_8).toString();
        } else if ("SHA1".equals(encrypt)) {
            // SHA1加密
            sign = Hashing.sha1().hashString(buffer, Charsets.UTF_8).toString();
        }
        if (toUpperCase) {
            sign = sign.toUpperCase();
        }
        return sign;
    }

    /**
     * 根据参数获得相关签名
     *
     * @param params  加密参数，ASCII 码从小到大排序（字典序）
     * @param encrypt 加密方式 SHA1 MD5
     * @return
     */
    public static String signature(Map params, String encrypt, boolean toUpperCase) {
        String sign = "";
        // 拼接字符串，按照字典排序
        String buffer = generateQueryString(params, true);
        if ("MD5".equals(encrypt)) {
            // MD5加密
            sign = Hashing.md5().hashString(buffer, Charsets.UTF_8).toString();
        } else if ("SHA1".equals(encrypt)) {
            // SHA1加密
            sign = Hashing.sha1().hashString(buffer, Charsets.UTF_8).toString();
        }
        if (toUpperCase) {
            sign = sign.toUpperCase();
        }
        return sign;
    }

    /**
     * 根据ID生成六位随机码
     *
     * @param id ID
     * @return 随机码
     */
    public static String parseShareCode(long id) {

        return parseShareCodeCore(id, minLength);
    }

    /**
     * 根据ID生成指定位数随机码
     *
     * @param id         ID
     * @param codeLength 指定长度
     * @return 随机码
     */
    public static String parseShareCodeCore(long id, int codeLength) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / hexLength) > 0) {
            int ind = (int) (id % hexLength);
            buf[--charPos] = hexChars[ind];
            id /= hexLength;
        }
        buf[--charPos] = hexChars[(int) (id % hexLength)];
        String result = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (result.length() < codeLength) {
            StringBuilder sb = new StringBuilder();
            sb.append(noneHex);
            Random rnd = new Random();
            for (int i = 1; i < codeLength - result.length(); i++) {
                sb.append(hexChars[rnd.nextInt(hexLength)]);
            }
            result += sb.toString();
        }
        return result;
    }

    /**
     * 依据code生成Id
     *
     * @param code
     * @return
     */
    public static long parseId(String code) {
        char chs[] = code.toCharArray();
        long result = 0L;
        for (int i = 0; i < chs.length; i++) {
            int index = 0;
            for (int j = 0; j < hexLength; j++) {
                if (chs[i] == hexChars[j]) {
                    index = j;
                    break;
                }
            }
            if (chs[i] == noneHex) {
                break;
            }
            if (i > 0) {
                result = result * hexLength + index;
            } else {
                result = index;
            }
        }
        return result;
    }

    public static synchronized String produceCode(int length) {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat(Constants.FORMAT).format(new Date());
        String s = Integer.toString(sequence);
        return datetime + addLeftZero(s, length);
    }

    /**
     * 左填0
     */
    public static String addLeftZero(String s, int length) {
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                        "Numeric value is larger than intended length: " + s
                                + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);
    }


    public static void main(String[] args) {
        System.out.println(parseShareCodeCore(1, 6));
        System.out.println(parseId(parseShareCode(1)));
        System.out.println(randomString(7, false));
    }
}
