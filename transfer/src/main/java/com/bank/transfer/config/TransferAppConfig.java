package com.bank.transfer.config;

import com.bank.transfer.handler.FeignClientErrorDecoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Конфигурационный файл
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.bank.transfer.repository"})
public class TransferAppConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }
    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
