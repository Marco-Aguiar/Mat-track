package com.mattrack.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String DASHBOARD_SUMMARY = "dashboard:summary";
    public static final String DASHBOARD_WEEKLY = "dashboard:weekly";
    public static final String DASHBOARD_TECHNIQUES = "dashboard:techniques";
    public static final String DASHBOARD_CATEGORIES = "dashboard:categories";
    public static final String DASHBOARD_WEIGHT = "dashboard:weight";

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        var jsonSerializer = RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer());

        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(jsonSerializer)
                .disableCachingNullValues();

        var perCacheConfig = Map.of(
                DASHBOARD_SUMMARY, defaultConfig.entryTtl(Duration.ofMinutes(5)),
                DASHBOARD_WEEKLY, defaultConfig.entryTtl(Duration.ofMinutes(10)),
                DASHBOARD_TECHNIQUES, defaultConfig.entryTtl(Duration.ofMinutes(10)),
                DASHBOARD_CATEGORIES, defaultConfig.entryTtl(Duration.ofMinutes(10)),
                DASHBOARD_WEIGHT, defaultConfig.entryTtl(Duration.ofMinutes(5))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(perCacheConfig)
                .build();
    }
}
