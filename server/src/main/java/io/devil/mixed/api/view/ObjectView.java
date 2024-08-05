package io.devil.mixed.api.view;

import lombok.Data;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/3 09:42
 */
@Data
public class ObjectView {

    private String id;

    private String name;

    private List<TagView> tags;

}
