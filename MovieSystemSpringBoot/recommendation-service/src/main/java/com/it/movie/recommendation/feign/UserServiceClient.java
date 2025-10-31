package com.it.movie.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallback = UserServiceFallback.class)
public interface UserServiceClient {

    @GetMapping("/api/users/{userId}")
    Object getUserById(@PathVariable("userId") Long userId);

    @GetMapping("/api/users/{userId}/preferences")
    Object getUserPreferences(@PathVariable("userId") Long userId);
}

class UserServiceFallback implements UserServiceClient {

    @Override
    public Object getUserById(Long userId) {
        return "User service is temporarily unavailable";
    }

    @Override
    public Object getUserPreferences(Long userId) {
        return "User preferences service is temporarily unavailable";
    }
}