package io.devil.mixed.domain.obj;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.devil.mixed.converter.TableConverter;
import io.devil.mixed.dao.mapper.ObjectMapper;
import io.devil.mixed.dao.mapper.ObjectTagMapper;
import io.devil.mixed.dao.mapper.TagMapper;
import io.devil.mixed.dao.table.ObjectTable;
import io.devil.mixed.dao.table.ObjectTagTable;
import io.devil.mixed.dao.table.TagTable;
import io.devil.mixed.utils.MyBatisUtils;
import io.devil.mixed.utils.UUIDUtils;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.devil.mixed.constant.ObjectConstants.STORAGE_DIR;

/**
 * @author Devil
 * @date 2024/8/2 15:56
 */
@Service
public class ObjectService {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectTagMapper objectTagMapper;
    @Resource
    private TagMapper tagMapper;

    @CommandHandler
    public void handle(StorageObjUploadCmd cmd) {
        // 写入文件
        String name = cmd.name();
        String type = StringUtils.EMPTY;
        if (cmd.name().contains(".")) {
            name = cmd.name().substring(0, cmd.name().lastIndexOf("."));
            type = cmd.name().substring(cmd.name().lastIndexOf(".") + 1);
        }
        String fileName = name + "-" + UUIDUtils.shortRandomID();
        if (StringUtils.isNotBlank(type)) {
            fileName = fileName + "." + type;
        }
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(STORAGE_DIR + fileName)) {
            fos.write(cmd.bytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 写入数据
        ObjectTable objectTable = new ObjectTable();
        objectTable.setName(fileName);
        objectMapper.insert(objectTable);
        // 写入关联数据
        batchInsertTags(objectTable.getId().toString(), cmd.tagIds());
    }

    @CommandHandler
    public void hande(StorageObjUpdateCmd cmd) {
        objectTagMapper.delete(Wrappers.<ObjectTagTable>lambdaQuery()
            .eq(ObjectTagTable::getObjectId, cmd.objectId())
        );
        batchInsertTags(cmd.objectId(), cmd.tagIds());
    }

    private void batchInsertTags(String objectId, List<String> tagIds) {
        if (CollectionUtils.isEmpty(tagIds)) {
            return;
        }
        List<ObjectTagTable> objectTagTables = tagIds.stream().map(tagId -> {
            ObjectTagTable table = new ObjectTagTable();
            table.setObjectId(Long.valueOf(objectId));
            table.setTagId(Long.valueOf(tagId));
            return table;
        }).toList();
        MyBatisUtils.batchInsert(objectTagTables, objectTagMapper);
    }

    @QueryHandler
    public StorageObjQuery.Response handle(StorageObjQuery query) {
        List<ObjectTagTable> objectTagTables = objectTagMapper.selectList(Wrappers.lambdaQuery()); // todo 缓存一下，增加性能
        List<ObjectTable> objectTables = objectMapper.selectList(Wrappers.lambdaQuery());
        List<Long> tagIds = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(query.getTagIds())) {
            objectTagTables = objectTagTables.stream()
                .filter(o -> query.getTagIds().contains(o.getTagId()))
                .toList();

            Set<Long> objectIds = objectTagTables.stream()
                .map(ObjectTagTable::getObjectId)
                .collect(Collectors.toSet());

            tagIds = objectTagTables.stream().map(ObjectTagTable::getTagId).toList();

            objectTables = objectTables.stream().filter(o-> objectIds.contains(o.getId())).toList();
        }

        List<TagTable> tagTables = tagMapper.selectList(Wrappers.<TagTable>lambdaQuery()
            .in(CollectionUtils.isNotEmpty(tagIds), TagTable::getId, tagIds)
        );

        StorageObjQuery.Response response = new StorageObjQuery.Response();
        response.setStorageObjs(TableConverter.convertToObjs(objectTables, objectTagTables, tagTables));
        return response;
    }

}
