package com.bank.history.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "antiFraudFeignClient", url = "http:/localhost:8086/api/anti-fraud")
public interface AntiFraudClient {
    @GetMapping(path = "/api/anti-fraud/.../{id}")
    Long getId (@PathVariable Long id);
}
