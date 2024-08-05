package io.devil.mixed.dao;

import java.util.function.Supplier;

/**
 * @author Devil
 * @since 2023/12/29
 */
public interface TransactionInvoker {

    /**
     * 包装在事务中的处理
     * @param supplier 处理逻辑
     * @param <T> 返回类型
     * @return 返回数据
     */
    <T> T invoke(Supplier<T> supplier);
}