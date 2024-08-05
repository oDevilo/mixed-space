package io.devil.mixed.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Devil
 * @date 2024/8/3 09:51
 */
@Data
@Builder
@AllArgsConstructor
public class TagQuery {

    private String name;

    @Data
    public static class Response {
        private List<Tag> tags;
    }
}
