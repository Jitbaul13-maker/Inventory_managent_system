package com.inv_managemnt.inv_backend.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory factory,
            ObjectMapper mapper
    ){

        RedisCacheConfiguration configuration =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(2))
                        .serializeValuesWith(
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(
                                                new GenericJacksonJsonRedisSerializer(
                                                        mapper
                                                )
                                        ));

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(configuration)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory factory,
            ObjectMapper mapper
    ){
        RedisTemplate<String, Object> template =
                new RedisTemplate<>();

        template.setConnectionFactory(factory);

        StringRedisSerializer keySerializer =
                new StringRedisSerializer();

        GenericJacksonJsonRedisSerializer
                valueSerializer =
                new GenericJacksonJsonRedisSerializer(
                        mapper
                );

        template.setKeySerializer(
                keySerializer
        );

        template.setHashKeySerializer(
                keySerializer
        );

        template.setValueSerializer(
                valueSerializer
        );

        template.setHashValueSerializer(
                valueSerializer
        );

        template.afterPropertiesSet();

        return template;
    }
}
