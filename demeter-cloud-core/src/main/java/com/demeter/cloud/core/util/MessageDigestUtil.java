package com.demeter.cloud.core.util;

import com.demeter.cloud.core.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>封装Dcloud项目MessageDigestUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 02:57
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class MessageDigestUtil {
    private static final Integer INDEX_LENGTH = 256;
    /**
     * 定义md5签名
     */
    private static final String[] MD5_DIGITS =
            new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static final char[] digit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * @param bytes
     * @return
     */
    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0, temp = bytes.length; index < temp; index++) {
            builder.append(byteToHex(bytes[index]));
        }
        return builder.toString();
    }

    /**
     * MD5加密字符串
     *
     * @param originTarget 来源目标
     * @return
     */
    public static String md5Encode(String originTarget) {
        String result = null;
        try {
            result = originTarget;
            MessageDigest md = MessageDigest.getInstance("MD5");
            result.trim();
            result = byteArrayToHex(md.digest(result.getBytes(Constants.UNIFIED_CODE)));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /**
     * @param byteHex
     * @return
     */
    private static String byteToHex(byte byteHex) {
        int constIndex = byteHex;
        if (constIndex < 0) {
            constIndex = INDEX_LENGTH + constIndex;
        }
        int digitsIndexOne = constIndex / 16;
        int digitsIndexTwo = constIndex % 16;
        return MD5_DIGITS[digitsIndexOne] + MD5_DIGITS[digitsIndexTwo];
    }

    /**
     * 封装MD5方法
     *
     * @param sourceTarget 参数目标
     * @return
     */
    public static String md5(String sourceTarget) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            char[] sourceArray = sourceTarget.toCharArray();
            byte[] bytes = new byte[sourceArray.length];
            for (int index = 0; index < sourceArray.length; index++) {
                bytes[index] = (byte) sourceArray[index];
                byte[] md5Bytes = messageDigest.digest(bytes);
                StringBuilder builder = new StringBuilder();
                for (byte md5Byte : md5Bytes) {
                    int indexLength = md5Byte & 0xff;
                    if (indexLength < 16) {
                        builder.append("0");
                        builder.append(Integer.toHexString(indexLength));
                        result = builder.toString();
                    }
                }
            }
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            result = StringUtils.EMPTY;
        } catch (Exception exception) {
            exception.printStackTrace();
            result = StringUtils.EMPTY;
        }
        return result;
    }


    public static String encodeMd5(String source) {
        byte[] temp = (source != null ? source : StringUtils.EMPTY).getBytes();
        return encodeMd5(temp);
    }

    public static String encodeMd5(byte[] source) {
        try {
            StringBuilder result = new StringBuilder();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source);
            byte[] b = digest.digest();
            for (int i = 0; i < b.length; i++) {
                char[] ob = new char[2];
                ob[0] = digit[(b[i] >>> 4) & 0X0F];
                ob[1] = digit[b[i] & 0X0F];
                result.append(new String(ob));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            return StringUtils.EMPTY;
        }
    }

    public static void main(String[] args) {
        String data = "189022881112011111118";
        md5Encode(data);
        System.out.println(encodeMd5(data));
        md5Encode(data);
        System.out.println(md5Encode(encodeMd5(data)));
    }
}
