package com.it.movie.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 熔断降级处理器
 * 当微服务不可用时返回友好的错误信息
 */
@RestController
public class FallbackController {
    
    /**
     * 用户服务熔断降级
     */
    @RequestMapping("/fallback/user")
    public Mono<Map<String, Object>> userFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "FALLBACK");
        response.put("service", "user-service");
        response.put("message", "用户服务暂时不可用，请稍后重试");
        response.put("timestamp", LocalDateTime.now());
        response.put("data", null);
        response.put("code", 503);
        return Mono.just(response);
    }
    
    /**
     * 电影服务熔断降级
     */
    @RequestMapping("/fallback/movie")
    public Mono<Map<String, Object>> movieFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "FALLBACK");
        response.put("service", "movie-service");
        response.put("message", "电影服务暂时不可用，请稍后重试");
        response.put("timestamp", LocalDateTime.now());
        response.put("data", null);
        response.put("code", 503);
        return Mono.just(response);
    }
    
    /**
     * 推荐服务熔断降级
     */
    @RequestMapping("/fallback/recommend")
    public Mono<Map<String, Object>> recommendFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "FALLBACK");
        response.put("service", "recommendation-service");
        response.put("message", "推荐服务暂时不可用，为您提供热门推荐");
        response.put("timestamp", LocalDateTime.now());
        
        // 提供默认的热门推荐数据
        Map<String, Object> fallbackData = new HashMap<>();
        fallbackData.put("type", "热门推荐");
        fallbackData.put("movies", new String[]{
            "肖申克的救赎", "霸王别姬", "阿甘正传", "泰坦尼克号", "这个杀手不太冷"
        });
        fallbackData.put("reason", "基于历史热门数据");
        
        response.put("data", fallbackData);
        response.put("code", 200);
        return Mono.just(response);
    }
    
    /**
     * 通用熔断降级处理
     */
    @RequestMapping("/fallback/generic")
    public Mono<Map<String, Object>> genericFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "FALLBACK");
        response.put("service", "unknown");
        response.put("message", "服务暂时不可用，请稍后重试");
        response.put("timestamp", LocalDateTime.now());
        response.put("data", null);
        response.put("code", 503);
        return Mono.just(response);
    }
}