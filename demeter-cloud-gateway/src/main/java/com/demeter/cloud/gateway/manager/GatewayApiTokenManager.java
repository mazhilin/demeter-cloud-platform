package com.demeter.cloud.gateway.manager;

import com.demeter.cloud.core.util.CharUtil;
import com.demeter.cloud.gateway.web.GatewayApiToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>封装Dcloud项目GatewayApiTokenManager类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-19 02:16
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class GatewayApiTokenManager {
    private static Map<String, GatewayApiToken> tokenMap = new HashMap<>();
    private static Map<Integer, GatewayApiToken> idMap = new HashMap<>();

    public static Integer getUserId(String token) {
        GatewayApiToken apiToken = tokenMap.get(token);
        if (apiToken == null) {
            return null;
        }

        if (apiToken.getExpireTime().isBefore(LocalDateTime.now())) {
            tokenMap.remove(token);
            idMap.remove(apiToken.getUserId());
            return null;
        }

        return apiToken.getUserId();
    }

    public static GatewayApiToken generateToken(Integer id) {
        GatewayApiToken apiToken = null;
        String token = CharUtil.getRandomString(32);
        while (tokenMap.containsKey(token)) {
            token = CharUtil.getRandomString(32);
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusDays(1);

        apiToken = new GatewayApiToken();
        apiToken.setToken(token);
        apiToken.setUpdateTime(update);
        apiToken.setExpireTime(expire);
        apiToken.setUserId(id);
        tokenMap.put(token, apiToken);
        idMap.put(id, apiToken);

        return apiToken;
    }

    public static String getSessionKey(Integer userId) {
        GatewayApiToken apiToken = idMap.get(userId);
        if (apiToken == null) {
            return null;
        }

        if (apiToken.getExpireTime().isBefore(LocalDateTime.now())) {
            tokenMap.remove(apiToken.getToken());
            idMap.remove(userId);
            return null;
        }

        return apiToken.getSessionKey();
    }

    public static void removeToken(Integer userId) {
        GatewayApiToken apiToken = idMap.get(userId);
        String token = apiToken.getToken();
        idMap.remove(userId);
        tokenMap.remove(token);
    }
}
