package com.demeter.cloud.core.utils;

import com.demeter.cloud.core.constant.Constants;
import io.netty.handler.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * <p>封装Dcloud项目MessageEncodeUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-20 02:59
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class MessageEncodeUtil {
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Base64解码.
     */
    public static final byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * Hex解码.
     *
     * @throws DecoderException
     */
    public static final byte[] decodeHex(String input) throws DecoderException {
        if (Objects.nonNull(input)) {
            try {
                return Hex.decodeHex(input.toCharArray());
            } catch (org.apache.commons.codec.DecoderException e) {
                e.printStackTrace();
            }
        }
        return new byte[]{};
    }

    /**
     * Base62编码。
     */
    public static final String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * Base64编码.
     */
    public static final String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * 16进制编码.
     */
    public static final String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static final String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Html 转码.
     */
    public static final String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Xml 转码.
     */
    @SuppressWarnings("deprecation")
    public static final String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Html 解码.
     */
    public static final String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 解码.
     */
    public static final String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     *
     * @throws UnsupportedEncodingException
     */
    public static final String urlDecode(String part) throws UnsupportedEncodingException {
        return URLDecoder.decode(part, Constants.UNIFIED_CODE);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     *
     * @throws UnsupportedEncodingException
     */
    public static final String urlEncode(String part) throws UnsupportedEncodingException {
        return URLEncoder.encode(part, Constants.UNIFIED_CODE);
    }

    /**
     * 将字符转为Unicode码表示，实际上他的用处是我们可以在程序中用文字生成i18n文件的内容。
     * 例子"输入长度请小于"转换之后得到"\u8F93\u5165\u957F\u5EA6\u8BF7\u5C0F\u4E8E"
     * 这串东西给到i18n的配置文件，比如error_zh_TW.properties中是可以自动显示回正确的中文的。
     * 如果要程序将unicode转成中文，只需要"\u8F93\u5165\u957F\u5EA6\u8BF7\u5C0F\u4E8E".
     * toString()即可。
     *
     * @param s
     * @return
     */
    public static String string2unicode(String s) {
        int in;
        String st = "";
        for (int i = 0; i < s.length(); i++) {
            // 得到字符的unicode值，也就是ascii值。
            in = s.codePointAt(i);
            if (in > 127) {
                st = st + "\\u" + Integer.toHexString(in).toUpperCase();
            } else {
                // 如果ascii码值小于等于127，表明这个字符是英文键盘中的一个字符，
                // 这个时候不要再转码了，直接输出。
                st = st + s.charAt(i);
            }
        }
        return st;
    }

    public static void main(String[] args) throws DecoderException {
        System.err.println(string2unicode("ã"));
        System.err.println("\u00E3".toString());
        System.err.println(decodeHex("05f5e0ff"));
    }
}
