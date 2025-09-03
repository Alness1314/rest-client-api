package com.alness.restclient.api.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.ObjectFactory;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@Configuration
@Import(FeignClientsConfiguration.class)
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

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}
