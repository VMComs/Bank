package com.bank.history.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "authorizationAuditFeignClient", url = "http:/localhost:8087/api/authorization")
public interface AuthorizationClient {
    @GetMapping(path = "/api/authorization/.../{id}")
    Long getId (@PathVariable Long id);
}
