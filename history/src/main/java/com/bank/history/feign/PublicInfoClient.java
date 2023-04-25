package com.bank.history.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "publicInfoAuditFeignClient", url = "http:/localhost:8091/api/public-info")
public interface PublicInfoClient {
    @GetMapping(path = "/api/public-info/.../{id}")
    Long getId (@PathVariable Long id);
}
