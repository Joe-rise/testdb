package com.joe.testdb.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    /**
     * 组装日志
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!StringUtils.isBlank(request.getContentType()) && request.getContentType().contains("application/json")) {
            String queryString = request.getQueryString();
            String body = null;
            if (request instanceof RequestWrapper) {
                body = StringUtils.removePattern(((RequestWrapper) request).getPayload(), "\\s*|\t|\r|\n");
            }
            log.info("url:"+request.getRequestURI()+" body:"+body + "queryString:" + queryString);
        }
        return true;
    }
    /**
     * 请求结束后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
