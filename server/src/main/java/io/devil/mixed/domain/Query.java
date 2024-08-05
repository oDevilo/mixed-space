package io.devil.mixed.domain;

import io.devil.mixed.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Devil
 * @date 2024/8/2 16:03
 */
@Slf4j
@Component
public class Query implements ApplicationContextAware {

    private static QueryGateway GATEWAY;

    public static <R, Q> R query(Q query, Class<R> responseType) {
        CompletableFuture<R> future = GATEWAY.query(query, responseType);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Query get response error query:{}, type:{}", JacksonUtils.toJSONString(query), responseType.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static <R, Q> CompletableFuture<R> asyncQuery(Q query, Class<R> responseType) {
        return GATEWAY.query(query, responseType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GATEWAY = applicationContext.getBean(QueryGateway.class);
    }

}
