package io.devil.mixed.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 前后端交互 时间统一用时间戳
 *
 * @author Devil
 * @since 2021/7/26
 */
@Slf4j
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * json 返回结果处理
     */
    @Bean
    public ObjectMapper jacksonObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
//        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Formatters.YMD_HMS)));
//        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Formatters.YMD_HMS)));
//        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Formatters.YMD_HMS)));
//        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Formatters.YMD_HMS)));
//        module.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(Formatters.YMD_HMS)));
//        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Formatters.YMD_HMS)));
//        module.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        return Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .dateFormat(new SimpleDateFormat(Formatters.YMD_HMS))
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new DateFormatter(Formatters.YMD_HMS));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        PathInterceptor pathInterceptor = new PathInterceptor();
        registry.addInterceptor(pathInterceptor)
                .addPathPatterns("/**");

        TraceInterceptor traceInterceptor = new TraceInterceptor();
        registry.addInterceptor(traceInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/health/**");
    }

}
