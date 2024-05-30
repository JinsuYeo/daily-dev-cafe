package com.jsyeo.dailydevcafe.config;

import com.jsyeo.dailydevcafe.dto.response.ResponseDto;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class EhcacheConfiguration {

    @Bean
    public CacheManager getCacheManager() {

        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfiguration<Long, ResponseDto> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, ResponseDto.class,
                        ResourcePoolsBuilder.heap(1000))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)))
                .build();

        CacheConfiguration<String, ResponseDto> cacheConfiguration2 = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ResponseDto.class,
                        ResourcePoolsBuilder.heap(1000))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)))
                .build();

        Cache<Long, ResponseDto> postCache = cacheManager.createCache("postCache",
                Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration));

        Cache<String, ResponseDto> postsCache = cacheManager.createCache("postsCache",
                Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration2));

        return cacheManager;
    }
}
