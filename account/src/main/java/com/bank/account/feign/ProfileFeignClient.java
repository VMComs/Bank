package com.bank.account.feign;

import com.bank.profile.model.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * FeignClient для обращения к микросервису Profile
 * Метод getById возвращает профиль из базы данных микросервиса profile по полю id
 * Для работы метода необходимо наличие у profile REST-метода(GET), который возвращает сущность по Id
 * Аннотация @GetMapping содержит путь, который отображается на тот же самый путь в аннотации @RequestMapping
 * в микросервисе Profile и указывает, что это будет GET запрос. Метод должен иметь такую-же сигнатуру,
 * что и метод в сервисе Profile. Это важно!
 * Если нужно использовать внешнюю веб-службу, которая не является частью архитектуры микросервисов
 * и не зарегистрирована в службе Eureka, то можно использовать URL в качестве параметра аннотации @FeignClient.
*/



@FeignClient(name = "profile")
public interface ProfileFeignClient {
    @GetMapping("/api/profile/{id}")
    Profile getById(@PathVariable Long id);
}
