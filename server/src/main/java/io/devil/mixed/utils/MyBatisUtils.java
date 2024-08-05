package io.devil.mixed.utils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class MyBatisUtils {

    private static final Log log = LogFactory.getLog(MyBatisUtils.class);

    private static final int BATCH_SIZE = 512;

    private static final Map<Class<?>, String> TABLE_NAME_CACHE = new ConcurrentHashMap<>(16);

    private static volatile SqlSessionFactory sqlSessionFactory;

    private static final Object LOCK = new Object();

    public static String parseTableName(Class<?> poClass) {
        if (TABLE_NAME_CACHE.containsKey(poClass)) {
            return TABLE_NAME_CACHE.get(poClass);
        }

        return TABLE_NAME_CACHE.computeIfAbsent(poClass, c -> {
            TableName[] tableNames = poClass.getAnnotationsByType(TableName.class);
            if (tableNames.length == 0) {
                throw new RuntimeException("此对象无" + TableName.class.getName());
            }

            return tableNames[0].value();
        });
    }

    /**
     * 此方法内部不保证在事务内执行，需要在外部调用的时候，保证在事务内调用
     */
    public static <T, M extends BaseMapper<T>> boolean batchInsert(List<T> entities, M mapper) {
        if (CollectionUtils.isEmpty(entities)) {
            return false;
        }
        String sqlStatement = getSqlStatement(mapper, SqlMethod.INSERT_ONE);
        return executeBatch(entities, mapper, BATCH_SIZE, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    /**
     * 执行批量操作
     *
     * @param list      数据集合
     * @param batchSize 批量大小
     * @param consumer  执行方法
     * @param <E>       泛型
     * @return 操作结果
     * @since 3.3.1
     */
    private static  <E, M extends BaseMapper<E>> boolean executeBatch(Collection<E> list, M mapper, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(getSqlSessionFactory(mapper), log, list, batchSize, consumer);
    }

    /**
     * see com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
     */
    private static <T, M extends BaseMapper<T>> SqlSessionFactory getSqlSessionFactory(M mapper) {
        if (sqlSessionFactory == null) {
            synchronized (LOCK) {
                if (sqlSessionFactory == null) {
                    MybatisMapperProxy<?> mybatisMapperProxy = (MybatisMapperProxy<?>) Proxy.getInvocationHandler(mapper);
                    SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate) mybatisMapperProxy.getSqlSession();
                    sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
                }
            }
        }
        return sqlSessionFactory;
    }

    private static <M> String getSqlStatement(M mapper, SqlMethod sqlMethod) {
        // 由于都是代理类，需要获取原始接口类
        Type[] genericInterfaces = AopUtils.getTargetClass(mapper).getGenericInterfaces();
        return SqlHelper.getSqlStatement((Class<?>) genericInterfaces[0], sqlMethod);
    }


}