package io.devil.mixed.domain;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author Devil
 * @date 2024/8/2 16:03
 */
@Component
public class Cmd implements ApplicationContextAware {

    private static CommandGateway GATEWAY;

    public static <R, T> CompletableFuture<R> send(T object) {
        return GATEWAY.send(object);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GATEWAY = applicationContext.getBean(CommandGateway.class);
    }
}
