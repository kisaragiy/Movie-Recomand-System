package com.it.movie.recommendation.fallback;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务熔断降级处理
 * 当用户服务不可用时提供备用数据
 */
@Component
public class UserServiceFallback {
    
    /**
     * 获取用户信息的降级方法
     */
    public Map<String, Object> getUserByIdFallback(Integer userId) {
        Map<String, Object> fallbackUser = new HashMap<>();
        fallbackUser.put("id", userId);
        fallbackUser.put("uname", "匿名用户");
        fallbackUser.put("status", "FALLBACK");
        fallbackUser.put("message", "用户服务暂时不可用，显示默认信息");
        fallbackUser.put("preferences", getDefaultPreferences());
        
        System.err.println("[FALLBACK] 用户服务熔断，用户ID: " + userId);
        return fallbackUser;
    }
    
    /**
     * 获取用户偏好的降级方法
     */
    public Map<String, Object> getUserPreferencesFallback(Integer userId) {
        Map<String, Object> fallbackPrefs = new HashMap<>();
        fallbackPrefs.put("userId", userId);
        fallbackPrefs.put("preferences", getDefaultPreferences());
        fallbackPrefs.put("status", "FALLBACK");
        fallbackPrefs.put("message", "用户服务不可用，使用默认偏好");
        
        System.err.println("[FALLBACK] 用户偏好获取熔断，用户ID: " + userId);
        return fallbackPrefs;
    }
    
    /**
     * 获取用户观看历史的降级方法
     */
    public Map<String, Object> getUserHistoryFallback(Integer userId) {
        Map<String, Object> fallbackHistory = new HashMap<>();
        fallbackHistory.put("userId", userId);
        fallbackHistory.put("history", getDefaultHistory());
        fallbackHistory.put("status", "FALLBACK");
        fallbackHistory.put("message", "用户服务不可用，使用缓存历史");
        
        System.err.println("[FALLBACK] 用户历史获取熔断，用户ID: " + userId);
        return fallbackHistory;
    }
    
    /**
     * 批量获取用户信息的降级方法
     */
    public Map<String, Object> getBatchUsersFallback(String userIds) {
        Map<String, Object> fallbackResult = new HashMap<>();
        fallbackResult.put("users", new Object[0]); // 空数组
        fallbackResult.put("status", "FALLBACK");
        fallbackResult.put("message", "用户服务不可用，无法获取批量用户信息");
        fallbackResult.put("requestedIds", userIds);
        
        System.err.println("[FALLBACK] 批量用户获取熔断，请求ID: " + userIds);
        return fallbackResult;
    }
    
    /**
     * 获取默认用户偏好
     */
    private Map<String, Object> getDefaultPreferences() {
        Map<String, Object> defaultPrefs = new HashMap<>();
        defaultPrefs.put("genres", new String[]{"动作", "喜剧", "剧情"});
        defaultPrefs.put("actors", new String[]{"成龙", "周星驰", "刘德华"});
        defaultPrefs.put("directors", new String[]{"张艺谋", "冯小刚", "徐克"});
        defaultPrefs.put("rating", "7.0+");
        defaultPrefs.put("year", "2000+");
        defaultPrefs.put("source", "system_default");
        return defaultPrefs;
    }
    
    /**
     * 获取默认观看历史
     */
    private Map<String, Object> getDefaultHistory() {
        Map<String, Object> defaultHistory = new HashMap<>();
        defaultHistory.put("recentMovies", new String[]{"肖申克的救赎", "霸王别姬", "阿甘正传"});
        defaultHistory.put("totalCount", 3);
        defaultHistory.put("lastUpdate", "缓存数据");
        defaultHistory.put("source", "cache_fallback");
        return defaultHistory;
    }
    
    /**
     * 通用错误处理
     */
    public Map<String, Object> handleError(Throwable cause, String operation, Object... params) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "ERROR");
        errorResponse.put("operation", operation);
        errorResponse.put("message", "用户服务调用失败: " + cause.getMessage());
        errorResponse.put("params", params);
        errorResponse.put("timestamp", System.currentTimeMillis());
        
        System.err.println("[FALLBACK] 用户服务错误 - " + operation + ": " + cause.getMessage());
        return errorResponse;
    }
}