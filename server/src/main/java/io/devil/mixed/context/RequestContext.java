package io.devil.mixed.context;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 环境上下文 提供线程级别的各种能力
 * 在整个生命周期中存活
 *
 * @author Devil
 * @date 2024/4/28 13:52
 */
public class RequestContext {
    /**
     * 链路ID
     */
    private static final ThreadLocal<String> traceId = new ThreadLocal<>();

    /**
     * 链路缓存
     */
    private static final ThreadLocal<Map<String, Object>> cache = new ThreadLocal<>();


    /**
     * 请求开始时间
     */
    public static final String START_TIME_KEY = "startTime";

    public static Long getCacheLong(String key) {
        Object o = getCache(key);
        if (o == null) {
            return null;
        }
        return (Long) o;
    }

    public static Object getCache(String key) {
        Map<String, Object> map = cache.get();
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    public static void cacheLong(String key, Long value) {
        cache(key, value);
    }

    public static void cache(String key, Object value) {
        Map<String, Object> map = cache.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            cache.set(map);
        }
        map.put(key, value);
    }

    public static String traceId() {
        return traceId.get();
    }

    public static void traceId(String id) {
        traceId.set(id);
        MDC.put("mdc_trace_id_str", " traceId:" + id);
    }

    public static void reset() {
        traceId.remove();
    }

}
