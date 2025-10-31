package com.it.movie.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 网关配置类
 * 包含限流、熔断、认证等配置
 */
@Configuration
public class GatewayConfig {
    
    /**
     * IP限流解析器
     */
    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
            Objects.requireNonNull(exchange.getRequest().getRemoteAddress())
                   .getAddress().getHostAddress()
        );
    }
    
    /**
     * 用户限流解析器
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            // 从请求头或参数中获取用户ID
            String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
            if (userId == null) {
                userId = exchange.getRequest().getQueryParams().getFirst("userId");
            }
            return Mono.just(userId != null ? userId : "anonymous");
        };
    }
    
    /**
     * 管理员限流解析器
     */
    @Bean
    public KeyResolver adminKeyResolver() {
        return exchange -> {
            String role = exchange.getRequest().getHeaders().getFirst("X-User-Role");
            return Mono.just("admin".equals(role) ? "admin" : "user");
        };
    }
}