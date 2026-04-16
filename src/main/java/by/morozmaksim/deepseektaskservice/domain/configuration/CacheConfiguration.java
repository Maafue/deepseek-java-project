package by.morozmaksim.deepseektaskservice.domain.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(){
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
        caffeineCacheManager.setCacheNames(Arrays.asList("tasks"));
        return caffeineCacheManager;
    }

    @Bean
    Caffeine<Object, Object> caffeineCacheBuilder(){
        return Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.MINUTES);
    }
}
