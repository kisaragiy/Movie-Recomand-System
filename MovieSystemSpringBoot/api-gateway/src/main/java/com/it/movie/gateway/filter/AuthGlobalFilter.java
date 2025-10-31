package com.it.movie.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 全局认证过滤器
 * 处理用户身份认证和权限验证
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    
    /**
     * 不需要认证的路径
     */
    private static final List<String> SKIP_AUTH_PATHS = Arrays.asList(
        "/api/user/login",
        "/api/user/register", 
        "/api/user/health",
        "/api/movie/health",
        "/api/recommend/health",
        "/actuator/health",
        "/fallback"
    );
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        
        // 跳过不需要认证的路径
        if (shouldSkipAuth(path)) {
            return chain.filter(exchange);
        }
        
        // 获取认证token
        String token = getToken(request);
        
        if (token == null || token.isEmpty()) {
            // 对于需要认证但没有token的请求，返回友好提示而不是直接拒绝
            if (isPublicApiPath(path)) {
                // 为公共API添加匿名用户标识
                ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", "anonymous")
                    .header("X-User-Role", "guest")
                    .build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }
            
            return handleUnauthorized(exchange);
        }
        
        // 验证token并提取用户信息
        try {
            // TODO: 这里应该验证JWT token的有效性
            // 暂时使用简单的token验证逻辑
            if (validateToken(token)) {
                // 从token中提取用户信息并添加到请求头
                String userId = extractUserId(token);
                String userRole = extractUserRole(token);
                
                ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Role", userRole)
                    .build();
                
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } else {
                return handleUnauthorized(exchange);
            }
        } catch (Exception e) {
            return handleUnauthorized(exchange);
        }
    }
    
    /**
     * 判断路径是否需要跳过认证
     */
    private boolean shouldSkipAuth(String path) {
        return SKIP_AUTH_PATHS.stream().anyMatch(path::startsWith);
    }
    
    /**
     * 判断是否为公共API路径
     */
    private boolean isPublicApiPath(String path) {
        return path.startsWith("/api/movie/") || 
               path.startsWith("/api/recommend/compare-test") ||
               path.contains("/health");
    }
    
    /**
     * 从请求中获取token
     */
    private String getToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        
        // 也支持从查询参数中获取token
        return request.getQueryParams().getFirst("token");
    }
    
    /**
     * 验证token有效性
     */
    private boolean validateToken(String token) {
        // TODO: 实现真正的JWT验证逻辑
        // 这里暂时使用简单的验证
        return token.length() > 10; // 简单的长度检查
    }
    
    /**
     * 从token中提取用户ID
     */
    private String extractUserId(String token) {
        // TODO: 从JWT token中解析用户ID
        return "user_" + token.hashCode();
    }
    
    /**
     * 从token中提取用户角色
     */
    private String extractUserRole(String token) {
        // TODO: 从JWT token中解析用户角色
        return token.contains("admin") ? "admin" : "user";
    }
    
    /**
     * 处理未授权请求
     */
    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        
        String body = "{\n" +
                "  \"status\": \"UNAUTHORIZED\",\n" +
                "  \"message\": \"访问需要身份验证，请先登录\",\n" +
                "  \"code\": 401,\n" +
                "  \"timestamp\": \"" + java.time.LocalDateTime.now() + "\"\n" +
                "}";
        
        org.springframework.core.io.buffer.DataBuffer buffer = 
            response.bufferFactory().wrap(body.getBytes());
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        
        return response.writeWith(Mono.just(buffer));
    }
    
    @Override
    public int getOrder() {
        return -100; // 高优先级，确保在其他过滤器之前执行
    }
}