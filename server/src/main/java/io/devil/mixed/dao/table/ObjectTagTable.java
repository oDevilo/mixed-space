package io.devil.mixed.dao.table;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Devil
 * @date 2024/8/2 14:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ms_object_tag")
public class ObjectTagTable extends BaseTable {

    private Long objectId;

    private Long tagId;

}
