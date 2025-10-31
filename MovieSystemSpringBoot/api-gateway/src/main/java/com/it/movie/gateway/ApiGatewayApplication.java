package com.it.movie.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API网关服务
 * 
 * 企业级网关功能：
 * 1. 统一入口和路由
 * 2. 身份认证和授权
 * 3. 限流和熔断
 * 4. 日志和监控
 * 5. 跨域处理
 * 6. 请求/响应转换
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("🚀 API网关服务启动成功！");
        System.out.println("🌐 网关地址: http://localhost:9999");
    }
}