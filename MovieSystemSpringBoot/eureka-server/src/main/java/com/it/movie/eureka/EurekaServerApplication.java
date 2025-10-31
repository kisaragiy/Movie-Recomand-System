package com.it.movie.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务注册中心
 * 
 * 微服务架构的核心组件，负责：
 * 1. 服务注册与发现
 * 2. 健康检查
 * 3. 负载均衡支持
 * 4. 故障转移
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("🚀 Eureka服务注册中心启动成功！");
        System.out.println("📋 管理界面: http://localhost:8761");
    }
}