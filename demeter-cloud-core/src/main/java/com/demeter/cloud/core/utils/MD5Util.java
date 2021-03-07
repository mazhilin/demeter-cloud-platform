package com.demeter.cloud.core.utils;

import com.demeter.cloud.core.constant.Constants;
import com.demeter.cloud.framework.utils.CheckEmptyUtil;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * <p>封装Dcloud项目MD5Util类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-04 09:44
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class MD5Util {
    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String md5(String str) {
        if (str != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(str.getBytes());
                // 将得到的字节数组变成字符串返回
                StringBuffer resultSb = new StringBuffer();
                String bool;
                for (int i = 0; i < results.length; i++) {
                    int n = results[i];
                    if (n < 0) {
                        n = 256 + n;
                    }
                    int d1 = n / 16;
                    int d2 = n % 16;
                    bool = hexDigits[d1] + hexDigits[d2];
                    resultSb.append(bool);
                }
                return resultSb.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String md5Password(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算->加盐
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            return "";
        }
    }


    /**
     * 校验加盐后是否和原文一致
     *
     * @param password
     * @param md5
     * @return
     * @author lixiang
     * @time 2018-1-13 下午8:45:39
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加盐MD5
     *
     * @param password
     * @return
     * @author lixiang
     * @time 2018-1-13 下午8:45:04
     */
    public static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(9999)).append(r.nextInt(9999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 用户密码加密，盐值为 ：私盐+公盐
     *
     * @param password 密码
     * @param salt     私盐
     * @return MD5加密字符串
     */
    public static String encryptPassword(String password, String salt) {
        return encodeByMD5(Constants.PUBLIC_SALT + password + salt);
    }

    /**
     * 密码加密 为了避免登录时前端传过来的密码是明文，所以前端密码传过来就经过了一次md5加密，所以登录密码只需要再执行一次
     * encodeByMD5(PUBLIC_SALT + pwd);即是数据库密码，而普通的明文密码，需要两次md5加密。
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        String pwdMd5 = encodeByMD5Real(password);
        return encodeByMD5(Constants.PUBLIC_SALT + pwdMd5);
    }

    /**
     * md5加密算法（结果转大写）
     *
     * @param originString
     * @return
     */
    public synchronized static String encodeByMD5(String originString) {
        String res = encodeByMD5Real(originString);
        if (CheckEmptyUtil.isNotEmpty(res)) {
            return res.toUpperCase();
        }
        return res;
    }

    /**
     * md5加密算法（单纯md5）
     *
     * @param originString
     * @return
     */
    public synchronized static String encodeByMD5Real(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String agrs[]) {


        System.out.println(MD5Util.encryptPassword("123456@Abc"));
    }
}
