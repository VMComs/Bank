package com.bank.transfer.configs;

import com.bank.transfer.handlers.FeignClientErrorDecoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Конфигурационный файл
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.bank.transfer.repositories"})
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
