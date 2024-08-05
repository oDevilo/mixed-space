package io.devil.mixed.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Devil
 * @date 2024/8/2 15:50
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private String id;

    private String name;

}
