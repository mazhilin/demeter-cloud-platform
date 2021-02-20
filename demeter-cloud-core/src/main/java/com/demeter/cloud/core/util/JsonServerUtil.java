package com.demeter.cloud.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.demeter.cloud.core.constant.Formats;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>封装Dcloud项目FastjsonUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-09 22:13
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class JsonServerUtil {
    public static final SerializeConfig serializeConfig;
    private static final SerializerFeature[] features = {
            /** Map集合字段排序策略 */
            SerializerFeature.MapSortField,
            /** 格式美化JSON输出 */
            SerializerFeature.PrettyFormat,
            /** 禁止循环引用检测 */
            SerializerFeature.DisableCircularReferenceDetect,
            /** list字段如果为null，输出为[]，而不是null */
            SerializerFeature.WriteNullListAsEmpty,
            /** 数值字段如果为null，输出为0，而不是null */
            SerializerFeature.WriteNullNumberAsZero,
            /** Boolean字段如果为null，输出为false，而不是null */
            SerializerFeature.WriteNullBooleanAsFalse,
            /** 字符类型字段如果为null，输出为""，而不是null */
            SerializerFeature.WriteNullStringAsEmpty
    };

    static {
        fastjsonConfig();
        serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
    }

    private JsonServerUtil() {}

    public static JsonServerUtil getInstance() {
        return JsonServerHolder.jsonServerInstance;
    }

    private static FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat(Formats.DATE_TIME_TO_PM);
        fastJsonConfig.setCharset(Charset.forName(StandardCharsets.UTF_8.name()));
        ValueFilter valueFilter =
                (object, str, target) -> {
                    if (Objects.isNull(target)) {
                        target = StringUtils.EMPTY;
                    }
                    return target;
                };
        ParserConfig.getGlobalInstance().addAccept("com.mall.cloud.");
        fastJsonConfig.setSerializeFilters(valueFilter);
        // 解决Long转json精度丢失的问题
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        return fastJsonConfig;
    }

    /**
     * JSON数据转换成Array对象
     *
     * @param jsonData JSON数据
     * @param targetObject 目标对象
     * @param jsonData JSON数据
     * @param targetObject 目标对象
     * @return 返回结果
     */
    public <T> List<T> convertToList(String jsonData, Class<T> targetObject) {
        return JSON.parseArray(jsonData, targetObject);
    }

    /**
     * JSON数据转换成Map对象
     *
     * @param jsonData JSON数据
     * @return
     */
    public Map convertToMap(String jsonData) {
        return JSONObject.parseObject(jsonData);
    }

    /**
     * JSON数据转换成指定的java对象
     *
     * @param jsonData JSON数据
     * @param targetObject 目标对象
     * @return 返回结果
     */
    public <T> T convertToObject(String jsonData, Class<T> targetObject) {
        return JSON.parseObject(jsonData, targetObject);
    }

    /**
     * JSON数据转换成String对象
     *
     * @param jsonData JSON数据
     * @return 返回结果
     */
    public String convertToString(String jsonData) {
        return JSONObject.parseObject(jsonData).toString();
    }

    /**
     * Object[] 转为Json数据
     *
     * @param array 数组
     * @return 返回结果
     */
    public String parseToJson(Object[] array) {
        return JSONObject.toJSONString(array, serializeConfig, features);
    }

    /**
     * List对象转为JSON数据
     *
     * @param lists list对象
     * @return 返回结果
     */
    public <T> String parseToJson(List<T> lists) {
        return JSONObject.toJSONString(lists, serializeConfig, features);
    }

    /**
     * Map对象转为JSON数据
     *
     * @param maps map对象
     * @return 返回结果
     */
    public <K, V> String parseToJson(Map<K, V> maps) {
        return JSONObject.toJSONString(maps, serializeConfig, features);
    }

    /**
     * Object 转为Json数据
     *
     * @param object 对象
     * @return 返回结果
     */
    public String parseToJson(Object object) {
        return JSON.toJSONString(object, serializeConfig, features);
    }

    /**
     * String 对象转换成JSON数据
     *
     * @param object
     * @return
     */
    public String parseToJson(String object) {
        return JSON.toJSON(object, serializeConfig).toString();
    }

    /**
     * JSON数据转换成Array对象
     *
     * @param jsonData JSON数据
     * @param targetObject 目标对象
     * @return 返回结果
     */
    public <T> Object[] convertToArray(String jsonData, Class<T> targetObject) {
        return JSON.parseArray(jsonData, targetObject).toArray();
    }

    private static class JsonServerHolder {
        private static final JsonServerUtil jsonServerInstance = new JsonServerUtil();
    }
}
