package in.testuniversity.config;

import javax.cache.Caching;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfig {

	@Bean
	public javax.cache.CacheManager jCacheManager() {
		
			javax.cache.CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
			
			//Define cache configurations
			CacheConfiguration<Object, Object> cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(
						Object.class, Object.class,
		                ResourcePoolsBuilder.heap(100))
					.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(java.time.Duration.ofMinutes(30)))
					.build();
								
			cacheManager.createCache("topicsCache", Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig));
			cacheManager.createCache("questionsCache", Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig));

			return cacheManager;		
	}
	
    @Bean
    public org.springframework.cache.CacheManager cacheManager(javax.cache.CacheManager jCacheManager) {
        return new JCacheCacheManager(jCacheManager);
    }
}
