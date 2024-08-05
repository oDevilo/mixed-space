package io.devil.mixed;

import io.devil.mixed.config.WebConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Devil
 * @date 2024/8/2 11:08
 */
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "io.devil.mixed")
@Import({
    WebConfiguration.class
})
@MapperScan(basePackages = {"io.devil.mixed.dao.mapper"})
public class WebApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
            .web(WebApplicationType.SERVLET)
            .sources(WebApplication.class)
            .build()
            .run(args);
    }
}
