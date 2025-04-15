package com.example.campus.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.example.campus.result.ResponseResult;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 使用 FastJsonRedisSerializer 进行序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        // 设置键值的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        // 创建默认的缓存配置
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // 设置缓存有效期
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(ResponseResult.class))); // 使用 FastJson 序列化值

        return RedisCacheManager.builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(cacheConfig)  // 设置默认的缓存配置
                .build();
    }
}
