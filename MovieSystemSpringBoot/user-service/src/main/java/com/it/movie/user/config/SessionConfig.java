package com.it.movie.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Session配置 - 实现跨服务Session共享
 * 使用Redis作为Session存储，解决微服务间会话兼容性问题
 */
@Configuration
@EnableRedisHttpSession(
    maxInactiveIntervalInSeconds = 86400, // 24小时过期
    redisNamespace = "movie:session"       // Session命名空间
)
public class SessionConfig {
    
    // Spring Session会自动配置Redis连接
    // 使用application.yml中的Redis配置
    
}