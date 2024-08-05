package io.devil.mixed.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 过滤部分路径
 *
 * @author devil
 * @date 2019/7/31
 */
@Slf4j
public class PathInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("/favicon.ico".equals(request.getRequestURI())) {
            return false;
        }
        return true;
    }
}
