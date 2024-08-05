package io.devil.mixed.domain.obj;

import io.devil.mixed.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/2 15:50
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageObj {

    private String id;

    private String name;

    private List<Tag> tags;
}
