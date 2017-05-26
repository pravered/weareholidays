package com.weareholidays.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.weareholidays.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.DaySummary.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Day.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.TripSettings.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.TripSummary.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Coupon.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.TripPeople.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Trip.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Trip.class.getName() + ".days", jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Trip.class.getName() + ".tripPeople", jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Note.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.CheckIn.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Media.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Album.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Album.class.getName() + ".media", jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Content.class.getName(), jcacheConfiguration);
            cm.createCache(com.weareholidays.domain.Timeline.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
