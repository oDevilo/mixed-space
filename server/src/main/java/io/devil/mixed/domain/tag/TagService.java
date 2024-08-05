package io.devil.mixed.domain.tag;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.devil.mixed.converter.TableConverter;
import io.devil.mixed.dao.mapper.TagMapper;
import io.devil.mixed.dao.table.TagTable;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Devil
 * @date 2024/8/2 15:56
 */
@Service
public class TagService {

    @Resource
    private TagMapper tagMapper;

    @CommandHandler
    public void handle(TagCreateCmd cmd) {
        TagTable tagTable = tagMapper.selectOne(Wrappers.<TagTable>lambdaQuery().eq(TagTable::getName, cmd.name()));
        if (tagTable == null) {
            tagTable = new TagTable();
            tagTable.setName(cmd.name());
            tagMapper.insert(tagTable);
        }
    }

    @QueryHandler
    public TagQuery.Response handle(TagQuery query) {
        List<TagTable> tagTables = tagMapper.selectList(Wrappers.<TagTable>lambdaQuery()
            .eq(StringUtils.isNotBlank(query.getName()), TagTable::getName, query.getName())
        );
        TagQuery.Response response = new TagQuery.Response();
        response.setTags(TableConverter.convertToTags(tagTables));
        return response;
    }

}
