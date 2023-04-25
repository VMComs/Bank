package com.bank.history.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "profileAuditFeignClient", url = "http:/localhost:8089/api/profile")
public interface ProfileClient {
    @GetMapping(path = "/api/profile/.../{id}")
    Long getId (@PathVariable Long id);
}
