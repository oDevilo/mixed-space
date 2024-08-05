package io.devil.mixed.domain.obj;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/2 17:28
 */
public record StorageObjUpdateCmd(String objectId, List<String> tagIds) {
}
