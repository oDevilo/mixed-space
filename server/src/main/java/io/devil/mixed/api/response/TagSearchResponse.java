package io.devil.mixed.api.response;

import io.devil.mixed.api.view.TagView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/3 09:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagSearchResponse {

    private List<TagView> tags;
}
