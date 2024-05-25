package com.example.pricecompareredis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 관련 설정
 * - connection, serialize, ...
 */
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    // (start) connectionFactory 를 사용할 템플릿 작성
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());       // 앞서 만든 connectionFactory 로 접속을 하겠다 !!
        redisTemplate.setKeySerializer(new StringRedisSerializer());        // key 로 받은 Object 를 직렬화 !!
        redisTemplate.setValueSerializer(new StringRedisSerializer());      // value 로 받은 Object 를 직렬화 !!

        return redisTemplate;
    }
}
