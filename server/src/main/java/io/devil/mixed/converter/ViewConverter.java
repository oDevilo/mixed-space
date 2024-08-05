package io.devil.mixed.converter;

import io.devil.mixed.api.view.ObjectView;
import io.devil.mixed.api.view.TagView;
import io.devil.mixed.domain.obj.StorageObj;
import io.devil.mixed.domain.tag.Tag;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Devil
 * @date 2024/8/3 10:02
 */
public class ViewConverter {

    public static ObjectView convert(StorageObj storageObj) {
        ObjectView objectView = new ObjectView();
        objectView.setId(storageObj.getId());
        objectView.setName(storageObj.getName());
        objectView.setTags(convertTags(storageObj.getTags()));
        return objectView;
    }

    public static List<ObjectView> convertObjs(List<StorageObj> storageObjs) {
        if (CollectionUtils.isEmpty(storageObjs)) {
            return Collections.emptyList();
        }
        return storageObjs.stream().map(ViewConverter::convert).collect(Collectors.toList());
    }

    public static List<TagView> convertTags(List<Tag> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return Collections.emptyList();
        }
        return tags.stream().map(ViewConverter::convert).collect(Collectors.toList());
    }

    public static TagView convert(Tag tag) {
        TagView tagView = new TagView();
        tagView.setId(tag.getId());
        tagView.setName(tag.getName());
        return tagView;
    }

}
