package com.it.movie.movies.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 电影微服务控制器
 * 路径前缀: /microservice/api/movie
 * 与原系统并行运行，不冲突
 */
@RestController
@RequestMapping("/microservice/api/movie")
@CrossOrigin(origins = "*")
public class MovieMicroserviceController {
    
    /**
     * 微服务健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("service", "movie-service");
        result.put("status", "UP");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
    
    /**
     * 电影列表（微服务版本）
     * 与原系统路径 /admin/movieList 并行
     */
    @GetMapping("/admin/list")
    public Map<String, Object> getMovieList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "1000") int pageSize,  // 大页面避免分页限制
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String shstatus) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 模拟电影数据 - 实际应该调用数据库
            result.put("data", 200);
            result.put("message", "电影微服务功能正常");
            result.put("source", "microservice");
            result.put("service", "movie-service");
            result.put("movieCount", 25); // 模拟数据
            
            // 返回兼容原系统的格式
            Map<String, Object> pageInfo = new HashMap<>();
            pageInfo.put("pageNum", pageNum);
            pageInfo.put("pageSize", pageSize);
            pageInfo.put("total", 25);
            
            result.put("pageInfo", pageInfo);
            
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "电影微服务错误: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 推荐电影（微服务版本）
     * 与原系统路径 /loveRecommend 并行
     */
    @GetMapping("/recommend")
    public Map<String, Object> getRecommendMovies(@RequestParam(defaultValue = "1") int pageNum) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            result.put("data", 200);
            result.put("message", "电影推荐微服务功能正常");
            result.put("source", "microservice");
            result.put("service", "movie-service");
            
            // 模拟推荐结果
            result.put("recommendCount", 10);
            
        } catch (Exception e) {
            result.put("data", 500);
            result.put("message", "推荐微服务错误: " + e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 微服务功能对比测试接口
     */
    @GetMapping("/compare-test")
    public Map<String, Object> compareTest() {
        Map<String, Object> result = new HashMap<>();
        
        result.put("data", 200);
        result.put("message", "电影微服务功能正常");
        result.put("source", "microservice");
        result.put("service", "movie-service");
        result.put("version", "1.3.0-microservice");
        result.put("features", new String[]{
            "电影列表管理", 
            "电影推荐算法", 
            "内容审核", 
            "分类管理",
            "并行运行兼容"
        });
        
        return result;
    }
}