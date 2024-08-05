package io.devil.mixed.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author Brozen
 * @since 1.0
 */
public class UUIDUtils {

    /**
     * 生成一个UUID，去除中连接线-
     */
    public static String randomID() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "").toUpperCase();
    }

    /**
     * 短UUID
     * 获取UUID的第一段字符串，短UUID可能重复
     */
    public static String shortRandomID() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
