package com.bank.account.feign;


import com.bank.publicinfo.dto.BankDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * FeignClient для обращения к микросервису Public-info
 * Метод getById возвращает детали банка из базы данных микросервиса publicinfo по полю id
 * Для работы метода необходимо наличие у publicinfo REST-метода(GET), который возвращает сущность по Id
 * Аннотация @GetMapping содержит путь, который отображается на тот же самый путь в аннотации @RequestMapping
 * в микросервисе publicinfo и указывает, что это будет GET запрос.
 * Путь сккопирован из микросервиса, который уже был MR
 * Если нужно использовать внешнюю веб-службу, которая не является частью архитектуры микросервисов
 * и не зарегистрирована в службе Eureka, то можно использовать URL в качестве параметра аннотации @FeignClient.
 */



@FeignClient(name = "publicinfo")
public interface PublicInfoFeignClient {
    @GetMapping(value = "/bank-details/id={id}")
    BankDetailsDto getById(@PathVariable Long id);
}