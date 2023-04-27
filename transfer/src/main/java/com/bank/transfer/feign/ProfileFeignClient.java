package com.bank.transfer.feign;

import com.bank.transfer.pojo.Profile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign клиент для взаимодействия с profile микросервисом
 */
@FeignClient(name = "profile-app", path = "/api/profile", url = "${services.profile.url}")
public interface ProfileFeignClient {
    /**
     * Метод, в котором мы ищем профиль по номеру телефона
     * @param phoneNumber номер телефона
     * @return Profile
     */
    @GetMapping
    Profile getProfileByPhoneNumber(@RequestParam(name = "phone_number") Long phoneNumber);
    /**
     * Метод, в котором мы ищем профиль по id
     * @param id id профиля
     * @return Profile
     */
    @GetMapping("/{id}")
    Profile getProfileById(@PathVariable Long id);
}
