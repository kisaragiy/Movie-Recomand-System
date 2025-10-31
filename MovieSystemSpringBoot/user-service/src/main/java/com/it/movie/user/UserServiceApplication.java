package com.it.movie.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 用户微服务启动类
 * 
 * 功能特性：
 * - 与原系统并行运行，路径隔离
 * - 支持用户注册、登录、管理
 * - 集成Eureka服务发现
 * - 支持分布式缓存
 * - 数据库事务管理
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.it.movie.user.dao")
@EnableTransactionManagement
@EnableCaching
public class UserServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        
        System.out.println("🚀 ========================================");
        System.out.println("🚀   用户微服务启动成功！                ");
        System.out.println("🚀 ========================================");
        System.out.println("🌐 服务端口: http://localhost:8082");
        System.out.println("📋 健康检查: http://localhost:8082/microservice/api/user/health");
        System.out.println("🔐 登录接口: POST http://localhost:8082/microservice/api/user/login");
        System.out.println("👤 注册接口: POST http://localhost:8082/microservice/api/user/register");
        System.out.println("📊 用户统计: GET http://localhost:8082/microservice/api/user/admin/stats");
        System.out.println("🔍 功能对比: GET http://localhost:8082/microservice/api/user/compare-test");
        System.out.println("🚀 ========================================");
        System.out.println("⚠️  注意：当前运行在并行模式，不影响原系统功能");
        System.out.println("🚀 ========================================");
    }
}