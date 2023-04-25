package com.bank.transfer.utils;

import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.exceptions.AccountServiceException;
import com.bank.transfer.exceptions.ServiceNotAvailableException;
import com.bank.transfer.feign.AccountFeignClient;
import com.bank.transfer.feign.ProfileFeignClient;
import com.bank.transfer.pojos.Account;
import com.bank.transfer.pojos.Profile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Утилитный класс, необходимый для поиска аккаунтов по номеру счета/карты/телефона
 */
@Component
@AllArgsConstructor
public class AccountSearcherUtil {
    private final ProfileFeignClient profileService;
    private final AccountFeignClient accountService;

    /**
     * Метод, возвращающий аккаунт по id
     * @param id id аккаунта
     * @return аккаунт
     * @throws AccountNotFoundException если аккаунт не был найден
     */
    public Account getAccountById(Long id) throws AccountNotFoundException {
        return accountService.getAccountById(id);
    }

    /**
     * Метод, возвращающий аккаунт по номеру банковского счета
     * @param accountNumber номер банковского счета
     * @return аккаунт
     * @throws AccountNotFoundException если аккаунт не был найден
     */
    public Account getAccountByAccountNumber(Long accountNumber) throws AccountNotFoundException {
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    /**
     * Метод, возвращающий аккаунт по номеру карты
     * Хз в каком микросервисе искать данную инфу
     * @param cardNumber номер карты
     * @return аккаунт
     * @throws AccountNotFoundException если аккаунт не был найден
     */
    public Account getAccountByCardNumber(Long cardNumber) throws AccountNotFoundException {
        return null;
    }

    /**
     * Метод, возвращающий аккаунт по номеру телефона
     * @param phoneNumber номер банковского счета
     * @return аккаунт
     * @throws AccountNotFoundException если аккаунт не был найден
     * @throws AccountServiceException если проблемы со стороны аккаунт-сервиса
     */
    public Account getAccountByPhoneNumber(Long phoneNumber) throws AccountNotFoundException,
            ServiceNotAvailableException {
        final Profile recipientProfile = profileService.getProfileByPhoneNumber(phoneNumber);
        return accountService.getAccountByProfileId(recipientProfile.getId());
    }
}
