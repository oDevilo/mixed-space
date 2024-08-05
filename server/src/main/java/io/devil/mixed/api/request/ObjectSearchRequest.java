package io.devil.mixed.api.request;

import lombok.Data;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/3 09:30
 */
@Data
public class ObjectSearchRequest {

    private List<String> tagIds;
}
