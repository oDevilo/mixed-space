package io.devil.mixed.api.controller;

import io.devil.mixed.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务监控检查
 *
 * @author Devil
 * @date 2024/1/31 18:38
 */
@Slf4j
@RestController
public class HealthController {

    /**
     * 就绪探针
     * 可以在容器启动后等待 5 秒后每 3 秒执行一次访问
     */
    @GetMapping("/api/v1/health/ready")
    public Response<Boolean> ready() {
        return Response.success(true);
    }

    /**
     * 启动探针
     * 可以在容器启动后等待 10 秒后每 5 秒执行一次访问
     */
    @GetMapping("/api/v1/health/start")
    public Response<Boolean> start() {
        return Response.success(true);
    }

    /**
     * 存活探针
     * 可以在容器启动后等待 15 秒后每 10 秒执行一次访问
     */
    @GetMapping("/api/v1/health/live")
    public Response<Boolean> live() {
        return Response.success(true);
    }


}
