package com.bank.history.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "transferAuditFeignClient", url = "http:/localhost:8092/api/transfer")
public interface TransferClient {
    @GetMapping(path = "/api/transfer/.../{id}")
    Long getId (@PathVariable Long id);
}
