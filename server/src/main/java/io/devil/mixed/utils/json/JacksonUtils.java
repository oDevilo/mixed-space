/*
 * Copyright 2020-2024 OpenFlow Team (https://github.com/openflow-io).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.devil.mixed.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.devil.mixed.utils.time.Formatters;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author Devil
 */
public class JacksonUtils {

    public static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {};

    public static final ObjectMapper MAPPER = newObjectMapper();

    public static final String DEFAULT_NONE_OBJECT = "{}";

    public static final String DEFAULT_NONE_ARRAY = "[]";

    /**
     * 生成新的{@link ObjectMapper}
     */
    public static ObjectMapper newObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 注册JDK8的日期API处理模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 注册LocalDateTime的类型处理，可以通过limbo.jackson.date-time-pattern环境变量指定
        String dateTimePattern = System.getProperty("limbo.jackson.date-time-pattern", Formatters.YMD_HMS);
        javaTimeModule.addSerializer(new LocalDateTimeSerializer(dateTimePattern));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimePattern));
        mapper.registerModule(javaTimeModule);

        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //在序列化时日期格式默认为 yyyy-MM-dd HH:mm:ss
        mapper.setDateFormat(new SimpleDateFormat(Formatters.YMD_HMS));
        mapper.getDeserializationConfig().with(new SimpleDateFormat(Formatters.YMD_HMS));

        //在序列化时忽略值为 null 的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }


    /**
     * 将对象转换为JSON字符串
     */
    public static <T> String toJSONString(T t, ObjectMapper mapper) {
        Objects.requireNonNull(t);
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Jackson序列化失败！type=" + t.getClass().getName(), e);
        }
    }

    /**
     * 将对象转换为JSON字符串
     */
    public static <T> String toJSONString(T t) {
        if (t == null) {
            return "";
        }
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Jackson序列化失败！type=" + t.getClass().getName(), e);
        }
    }

    /**
     * 将对象转换为JSON字符串
     */
    public static <T> String toJSONString(T t, String defaultValue) {
        if (t == null) {
            return defaultValue;
        }
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Jackson序列化失败！type=" + t.getClass().getName(), e);
        }
    }


    /**
     * 解析JSON字符串为指定类型
     */
    public static <T> T parseObject(String json, Class<T> type) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        Objects.requireNonNull(type);
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalStateException("Jackson反序列化失败！type=" + type.getName(), e);
        }
    }


    /**
     * 解析JSON字符串为指定类型，可以指定泛型以及多重嵌套泛型。
     */
    public static <T> T parseObject(String json, TypeReference<T> type) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        Objects.requireNonNull(type);
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalStateException("Jackson反序列化失败！type=" + type.getType().getTypeName(), e);
        }
    }

    public static <T> T convert(Object object, TypeReference<T> type) {
        if (object == null) {
            return null;
        }
        Objects.requireNonNull(type);
        try {
            return MAPPER.convertValue(object, type);
        } catch (Exception e) {
            throw new IllegalStateException("Jackson反序列化失败！type=" + type.getType().getTypeName(), e);
        }
    }


}