package com.sparta.msa_exam.order.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connection) {

        //Redis DB 와의 Connection
        //Redis 를 이용해 Spring Cache를 구현 할 때
        //Redis 관련 설정을 모아두는 클래스
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                //null 을 캐싱하는지
                .disableCachingNullValues()
                //기본 캐시 유지 시간
                .entryTtl(Duration.ofSeconds(60))
                //캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java())
                );

        return RedisCacheManager
                .builder(connection)
                .cacheDefaults(config)
                .build();

    }
}
