package com.bank.history.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "accountAuditFeignClient", url = "http:/localhost:8085/api/account")
public  interface AccountClient {
    @GetMapping(path = "/api/account//audit_for_account/{id}")
    Long getId(@PathVariable Long id);
}
