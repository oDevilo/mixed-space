package io.devil.mixed.dao.table;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Devil
 * @date 2024/2/8 22:40
 */
@Getter
@Setter
public class BaseTable {

    @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private Long id;

    private Date gmtCreated;

    private Date gmtUpdated;

    private Boolean isDeleted;

}
