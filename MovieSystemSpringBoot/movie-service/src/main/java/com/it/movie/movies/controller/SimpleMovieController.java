package com.it.movie.movies.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 简化版电影控制器 - 快速验证微服务功能
 */
@RestController
@RequestMapping("/microservice/api/movie/simple")
public class SimpleMovieController {
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "movie-service");
        result.put("port", 8083);
        result.put("timestamp", System.currentTimeMillis());
        result.put("version", "1.3.0-simple");
        return result;
    }
    
    @GetMapping("/compare-test")
    public Map<String, Object> compareTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("serviceName", "电影微服务");
        result.put("architecture", "microservices");
        result.put("status", "运行正常");
        result.put("features", new String[]{"电影管理", "分类系统", "评分统计"});
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
    
    @GetMapping("/admin/list")
    public Map<String, Object> getMovieList(@RequestParam(defaultValue = "通过") String shstatus,
                                           @RequestParam(defaultValue = "1000") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        result.put("movieCount", 156);
        result.put("status", shstatus);
        result.put("pageSize", pageSize);
        result.put("service", "movie-microservice");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
}