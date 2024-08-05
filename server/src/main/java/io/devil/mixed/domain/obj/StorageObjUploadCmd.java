package io.devil.mixed.domain.obj;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/2 17:28
 *
 * @param name 源文件名称
 */
public record StorageObjUploadCmd(String name, byte[] bytes, List<String> tagIds) {
}
