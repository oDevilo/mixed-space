package io.devil.mixed.config;

import io.devil.mixed.context.RequestContext;
import io.devil.mixed.utils.UUIDUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 链路入口
 *
 * @author devil
 * @date 2019/7/31
 */
@Slf4j
public class TraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestContext.reset();
        RequestContext.traceId(UUIDUtils.randomID());
        RequestContext.cacheLong(RequestContext.START_TIME_KEY, System.currentTimeMillis());
        // 后面可以放开，现在放开日志太多
        // log.warn("uid: {} uri:{}", RequestContext.currentUserId(), request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // controller执行后 view返回前执行，如果controller抛异常则不执行此方法
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // view组装后执行，所以在 BaseExceptionHandler 之后
//        Long startTime = RequestContext.getCacheLong(RequestContext.START_TIME_KEY);
//        long cost = System.currentTimeMillis() - (startTime == null ? 0L : startTime);
//        if (cost > 1000) {
//            log.warn("uid: {} uri:{} cost:{}", RequestContext.currentUserId(), request.getRequestURI(), cost);
//        }
        RequestContext.reset();
    }
}
