package io.devil.mixed.domain.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/3 09:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageObjQuery {
    private List<String> tagIds;

    @Data
    public static class Response {
        private List<StorageObj> storageObjs;
    }
}
