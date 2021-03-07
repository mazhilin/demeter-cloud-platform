package com.demeter.cloud.core.utils;

import com.demeter.cloud.core.constant.Constants;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>封装Dcloud项目IpUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-16 23:32
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class IpAddressUtil {
    public static String client(HttpServletRequest request) {
        String xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            xff = request.getRemoteAddr();
        }
        return xff;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
            if (!checkIP(ipAddress)){
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "127.0.0.1";
        }
        return ipAddress;
    }


    private static boolean checkIP(String ip) {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)
                || ip.split(".").length != 4) {
            return false;
        }
        return true;
    }

    /**
     * 判断当前操作系统是否是WINDOWS系统
     *
     * @return
     */
    public static Boolean isWindows() {
        String osName = System.getProperty(Constants.OS_NAME);
        boolean checkExist =
                (StringUtils.isNotEmpty(osName) && osName.toUpperCase().contains("WINDOWS"));
        return checkExist ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 获取本机ip及主机名称
     *
     * @return
     */
    public static Map<String, String> getLocalHost() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
        }
        String ipAddress = address.getHostAddress();
        String hostName = address.getHostName();
        Map<String, String> resultMap = Maps.newLinkedHashMap();
        resultMap.put("ipAddress", ipAddress);
        resultMap.put("hostName", hostName);
        return resultMap;
    }

    /**
     * 获取用户IP地址
     *
     * @param request 请求对象
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader(Constants.FORWARDED_IP);
        boolean checkExist =
                ipAddress == null
                        || ipAddress.length() == 0
                        || Constants.UNKNOWN.equalsIgnoreCase(ipAddress);
        if (checkExist) {
            ipAddress = request.getHeader(Constants.PROXY_IP);
        }
        if (checkExist) {
            ipAddress = request.getHeader(Constants.WL_PROXY_IP);
        }
        if (checkExist) {
            ipAddress = request.getHeader(Constants.HTTP_CLIENT_IP);
        }
        if (checkExist) {
            ipAddress = request.getHeader(Constants.HTTP_FORWARDED_IP);
        }
        if (checkExist) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals(Constants.IP_FOUR_LOCALHOST)
                    || ipAddress.equals(Constants.IP_SIX_LOCALHOST)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(Constants.IP_SEPARATOR) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(Constants.IP_SEPARATOR));
            }
        }
        return ipAddress;
    }

    /**
     * @return String 返回类型
     * @author marklin
     * @title: getRequestParamsAppendStr
     * @description: 将post请求参数 拼接生成字符串
     */
    public static String getRequestParams(Map<String, String[]> requestParams) {
        StringBuilder builder = new StringBuilder(StringUtils.EMPTY);
        String name;
        String[] values;
        String valueStr = StringUtils.EMPTY;
        for (Iterator<String> iterator = requestParams.keySet().iterator(); iterator.hasNext(); ) {
            name = iterator.next();
            values = requestParams.get(name);
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 将密码字段 加密存储
            boolean checkExist =
                    Constants.PASSWORD.equals(name)
                            || Constants.CONFIRM_PASSWORD.equals(name);
            if (checkExist) {
                builder.append(name).append("=").append(MessageDigestUtil.md5(valueStr));
            } else {
                builder.append(name).append("=").append(valueStr);
            }
            builder.append("&");
        }
        return builder.toString();
    }
}
