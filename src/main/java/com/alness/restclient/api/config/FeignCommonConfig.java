package com.alness.restclient.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignCommonConfig {
    @Bean
    public feign.Logger.Level feignLoggerLevel() {
        return feign.Logger.Level.BASIC;
    }

    @Bean
    public feign.codec.ErrorDecoder errorDecoder() {
        return (methodKey,
                response) -> new IllegalStateException("Peer API error " + response.status() + " en " + methodKey);
    }
}
