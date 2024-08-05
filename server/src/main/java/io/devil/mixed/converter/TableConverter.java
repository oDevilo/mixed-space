package io.devil.mixed.converter;

import io.devil.mixed.dao.table.BaseTable;
import io.devil.mixed.dao.table.ObjectTable;
import io.devil.mixed.dao.table.ObjectTagTable;
import io.devil.mixed.dao.table.TagTable;
import io.devil.mixed.domain.obj.StorageObj;
import io.devil.mixed.domain.tag.Tag;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Devil
 * @date 2024/8/3 10:02
 */
public class TableConverter {

    public static Tag convert(TagTable table) {
        return Tag.builder().id(table.getId().toString()).name(table.getName()).build();
    }

    public static List<Tag> convertToTags(List<TagTable> tables) {
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyList();
        }
        return tables.stream().map(TableConverter::convert).collect(Collectors.toList());
    }

    public static StorageObj convert(ObjectTable table, List<TagTable> tagTables) {
        return StorageObj.builder().id(table.getId().toString()).name(table.getName()).tags(convertToTags(tagTables)).build();
    }

    public static List<StorageObj> convertToObjs(List<ObjectTable> objectTables, List<ObjectTagTable> objectTagTables, List<TagTable> tagTables) {
        if (CollectionUtils.isEmpty(objectTables)) {
            return Collections.emptyList();
        }
        Map<Long, List<TagTable>> objTagMap = new HashMap<>();
        Map<Long, TagTable> tagMap = tagTables.stream().collect(Collectors.toMap(BaseTable::getId, t -> t));
        if (CollectionUtils.isNotEmpty(objectTables) && CollectionUtils.isNotEmpty(tagTables)) {
            Map<Long, List<ObjectTagTable>> group = objectTagTables.stream().collect(Collectors.groupingBy(ObjectTagTable::getObjectId));
            for (Map.Entry<Long, List<ObjectTagTable>> entry : group.entrySet()) {
                objTagMap.put(entry.getKey(), new ArrayList<>());
                for (ObjectTagTable objectTagTable : entry.getValue()) {
                    TagTable tagTable = tagMap.get(objectTagTable.getTagId());
                    if (tagTable != null) {
                        objTagMap.get(entry.getKey()).add(tagTable);
                    }
                }
            }
        }
        return objectTables.stream().map(t -> convert(t, objTagMap.get(t.getId()))).collect(Collectors.toList());
    }

}
