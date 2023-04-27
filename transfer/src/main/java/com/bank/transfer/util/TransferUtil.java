package com.bank.transfer.util;

import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.ServiceNotAvailableException;
import com.bank.transfer.exception.InsufficientFundsException;
import com.bank.transfer.exception.TransferFailedException;
import com.bank.transfer.feign.AccountFeignClient;
import com.bank.transfer.pojo.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Утилитный класс с методами, общими для всех видов транзакций
 */
@Slf4j
@Component
@AllArgsConstructor
public class TransferUtil {
    private final AccountFeignClient accountService;
    private final AccountSearcherUtil searcherUtil;

    /**
     * Метод, запрашивающий аккаунт отправителя и проверяющий возможность транзакции
     * @param accountDetailsId id аккаунта отправителя
     * @param amount сумма транзакции
     * @return аккаунт отправителя
     */
    public Account getAndCheckSender(Long accountDetailsId, Double amount) throws AccountNotFoundException,
            InsufficientFundsException {
        log.info("Получаем аккаунт отправителя");
        final Account sender = searcherUtil.getAccountById(accountDetailsId);
        log.info("Проверяем, возможна ли данная транзакция");
        if (sender.isNegativeBalance() || sender.getMoney() < amount) {
            log.warn("У отправителя {} недостаточно денег на счете", sender.getId());
            throw new InsufficientFundsException(
                    String.format("There is not enough %f on your account for a successful bank transfer",
                            amount - sender.getMoney()));
        } else {
            return sender;
        }
    }

    /**
     * В данном методе происходит непосредственно изменение банковских счетов отправителя и получателя
     * @param sender аккаунт отправителя
     * @param recipient аккаунт получателя
     * @param amount сумма транзакции
     */
    public void moneyTransfer(Account sender, Account recipient, Double amount) {
        log.info("Снимаем деньги со счета отправителя");
        sender.setMoney(sender.getMoney() - amount);

        log.info("Пополняем счет получателя");
        recipient.setMoney(recipient.getMoney() + amount);
    }

    /**
     * Метод, загружающий изменения банковских счетов в account сервис
     * @param sender аккаунт отправителя
     * @param recipient аккаунт получателя
     * @throws ServiceNotAvailableException если загрузить данные не удалось
     */
    @Transactional
    public void uploadAccounts(Account sender, Account recipient) throws ServiceNotAvailableException {
        try {
            log.info("Отправляем данные отправителя в account-сервис");
            final Account patchSenderResponse = accountService.patchAccount(sender.getId(), sender);
            log.info("Отправляем данные получателя в account-сервис");
            final Account patchRecipientResponse = accountService.patchAccount(recipient.getId(), recipient);
        } catch (Exception exception) {
            log.warn("Ошибка при отправке данных");
            throw new TransferFailedException(exception.getMessage());
        }
    }
}
