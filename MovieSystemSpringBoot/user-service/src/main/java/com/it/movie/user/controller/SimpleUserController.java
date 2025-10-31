package com.it.movie.user.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 简化版用户控制器 - 快速验证微服务功能
 */
@RestController
@RequestMapping("/microservice/api/user/simple")
public class SimpleUserController {
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "user-service");
        result.put("port", 8082);
        result.put("timestamp", System.currentTimeMillis());
        result.put("version", "1.3.0-simple");
        return result;
    }
    
    @GetMapping("/compare-test")
    public Map<String, Object> compareTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("serviceName", "用户微服务");
        result.put("architecture", "microservices");
        result.put("status", "运行正常");
        result.put("features", new String[]{"用户管理", "会员系统", "分布式缓存"});
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
    
    @GetMapping("/admin/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> result = new HashMap<>();
        result.put("totalUsers", 1520);
        result.put("activeUsers", 1203);
        result.put("vipUsers", 234);
        result.put("service", "user-microservice");
        return result;
    }
}