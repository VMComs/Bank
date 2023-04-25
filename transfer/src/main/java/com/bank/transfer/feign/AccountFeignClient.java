package com.bank.transfer.feign;

import com.bank.transfer.pojos.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign клиент для взаимодействия с account микросервисом
 */
@FeignClient(name = "account-app", path = "/api/account", url = "${services.account.url}")
public interface AccountFeignClient {
    /**
     * Метод, в котором мы ищем аккаунт по номеру лицевого счета
     * @param accountNumber - номер лицевого счета
     * @return Account
     */
    @GetMapping
    Account getAccountByAccountNumber(@RequestParam(name = "account_number") Long accountNumber);
    /**
     * Метод, в котором мы ищем аккаунт по id профиля
     * @param profileId id профиля
     * @return Account
     */
    @GetMapping
    Account getAccountByProfileId(@RequestParam(name = "profile_id") Long profileId);
    /**
     * Метод, в котором мы ищем аккаунт по id
     * @param id id аккаунта
     * @return Account
     */
    @GetMapping("{id}")
    Account getAccountById(@PathVariable Long id);
    /**
     * Отправляет обновленный аккаунт в аккаунт-сервис
     * @param account аккаунт получателя/отправителя
     */
    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    Account patchAccount(@PathVariable Long id, Account account);
}
