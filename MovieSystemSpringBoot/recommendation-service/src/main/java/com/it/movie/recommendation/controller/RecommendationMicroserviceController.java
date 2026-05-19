package com.it.movie.recommendation.controller;

import com.it.movie.recommendation.entity.Movie;
import com.it.movie.recommendation.service.HybridRecommendationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐微服务 — 兼容初版 /getHybridRecommendations、/loveRecommend
 */
@RestController
@RequestMapping("/microservice/api/recommend")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class RecommendationMicroserviceController {

    @Autowired
    private HybridRecommendationService recommendationService;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "recommendation-service");
        result.put("algorithms", Arrays.asList("协同过滤", "内容推荐", "混合推荐"));
        return result;
    }

    /**
     * 猜你喜欢（协同过滤），返回格式与初版 loveRecommend 一致
     */
    @GetMapping("/love")
    @PostMapping("/love")
    public Map<String, Object> loveRecommend(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(required = false) Integer user_id,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = resolveUserId(user_id, request);
        if (userId == null) {
            result.put("data", 400);
            result.put("message", "请先登录");
            return result;
        }
        List<Movie> list = recommendationService.getLoveRecommendMovies(userId);
        result.put("list", list);
        result.put("data", 200);
        result.put("source", "microservice");
        result.put("algorithm", "collaborative-filtering");
        return result;
    }

    /**
     * 混合推荐，返回电影 ID 列表（与初版 getHybridRecommendations 一致）
     */
    @GetMapping("/hybrid")
    @PostMapping("/hybrid")
    public Object hybridRecommend(@RequestParam("user_id") Integer user_id) {
        return recommendationService.getHybridRecommendationIds(user_id);
    }

    /** 兼容初版 POST 路径别名 */
    @PostMapping("/getHybridRecommendations")
    public Object getHybridRecommendations(@RequestParam("user_id") Integer user_id) {
        return recommendationService.getHybridRecommendationIds(user_id);
    }

    /**
     * 返回带详情的混合推荐列表
     */
    @GetMapping("/hybrid/detail")
    public Map<String, Object> hybridRecommendDetail(@RequestParam("user_id") Integer user_id) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> ids = recommendationService.getHybridRecommendationIds(user_id);
        result.put("data", 200);
        result.put("ids", ids);
        result.put("list", recommendationService.getMoviesByIds(ids));
        result.put("algorithm", "hybrid-recommendation");
        result.put("source", "microservice");
        return result;
    }

    private Integer resolveUserId(Integer userId, HttpServletRequest request) {
        if (userId != null) {
            return userId;
        }
        Object session = request.getSession().getAttribute("sessionmember");
        if (session instanceof Map<?, ?> map && map.get("id") != null) {
            return Integer.valueOf(map.get("id").toString());
        }
        if (session != null) {
            try {
                var method = session.getClass().getMethod("getId");
                Object id = method.invoke(session);
                if (id != null) {
                    return Integer.valueOf(id.toString());
                }
            } catch (ReflectiveOperationException ignored) {
                // ignore
            }
        }
        return null;
    }
}
