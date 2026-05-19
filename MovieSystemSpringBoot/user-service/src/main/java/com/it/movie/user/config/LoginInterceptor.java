package com.it.movie.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Session 登录校验拦截器（与初版登录态校验一致）
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/microservice/api/user/login",
            "/microservice/api/user/register",
            "/microservice/api/user/check-username",
            "/microservice/api/user/forgot-password",
            "/microservice/api/user/health",
            "/microservice/api/user/compare-test"
    );

  private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String path = request.getRequestURI();
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        Object session = request.getSession().getAttribute("sessionmember");
        if (session != null) {
            return true;
        }
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> body = new HashMap<>();
        body.put("data", 401);
        body.put("message", "未登录，请先登录");
        response.getWriter().write(objectMapper.writeValueAsString(body));
        return false;
    }
}
