package com.bank.transfer.services;

import com.bank.transfer.entities.AuditOperationType;
import com.bank.transfer.dtos.AccountTransferDto;
import com.bank.transfer.entities.AccountTransfer;
import com.bank.transfer.exceptions.TransferFailedException;
import com.bank.transfer.pojos.Account;
import com.bank.transfer.repositories.AccountTransferRepository;
import com.bank.transfer.support.IntegrationTestBase;
import com.bank.transfer.utils.AccountSearcherUtil;
import com.bank.transfer.utils.TransferUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;


public class TransferIT extends IntegrationTestBase {
    @Autowired
    private AccountTransferService transferService;
    @Autowired
    private AccountTransferRepository repository;
    @Autowired
    private TransferUtil transferUtil;
    @Autowired
    private AccountSearcherUtil searcherUtil;
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private AuditService auditService;

    @RegisterExtension
    static WireMockExtension ACCOUNT_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8085))
            .build();
    @RegisterExtension
    static WireMockExtension PROFILE_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8089))
            .build();

    @Test
    void doTransfer() throws JsonProcessingException {
        // Получаем валидные данные транзакции
        final Long senderId = 100L;
        final Long recipientId = 99L;
        final Long accountNumber = 1111_1111_1111_1111L;
        Double amount = 300.0;

        AccountTransfer transfer = AccountTransfer.builder()
                .accountDetailsId(senderId)
                .accountNumber(accountNumber)
                .amount(amount)
                .purpose("IT test")
                .build();

        //Запрашиваем отправителя перевода
        Account senderExpected = Account.builder()
                .id(senderId)
                .money(1000.0)
                .build();
        ACCOUNT_SERVICE.stubFor(get("/api/account/" + senderId)
                .willReturn(okJson(jsonMapper.writeValueAsString(senderExpected))));

        Account sender = transferUtil.getAndCheckSender(senderId, amount);

        assertThat(sender).isEqualTo(senderExpected);

        // Запрашиваем получателя перевода
        Account recipientExpected = Account.builder()
                .id(recipientId)
                .accountNumber(accountNumber)
                .money(0.0)
                .build();
        ACCOUNT_SERVICE.stubFor(get("/api/account?account_number=" + accountNumber)
                .willReturn(okJson(jsonMapper.writeValueAsString(recipientExpected))));

        Account recipient = searcherUtil.getAccountByAccountNumber(accountNumber);

        assertThat(recipient).isEqualTo(recipientExpected);

        // Переводим деньги
        transferUtil.moneyTransfer(sender, recipient, amount);

        assertThat(sender.getMoney()).isEqualTo(700L);
        assertThat(recipient.getMoney()).isEqualTo(300);

        // Отправляем данные на сервер
        ACCOUNT_SERVICE.stubFor(patch(urlMatching("/api/account/" + senderId))
                .willReturn(aResponse().withStatus(200)));
        ACCOUNT_SERVICE.stubFor(patch(urlMatching("/api/account/" + recipientId))
                .willReturn(aResponse().withStatus(200)));

        transferUtil.uploadAccounts(sender, recipient);


        // Записываем транзакцию
        repository.save(transfer);

        assertThat(repository.existsByAccountDetailsId(senderId)).isTrue();

        // Сообщаем о транзакции сервису аудита
        auditService.toAudit(transfer, AuditOperationType.SUCCESS_TRANSFER);

        assertThat(auditService.getAllAudits().size()).isEqualTo(1);
        assertThat(auditService.getAllAudits().get(0).getOperationType())
                .isEqualTo(AuditOperationType.SUCCESS_TRANSFER);
        assertThat(auditService.getAllAudits().get(0).getCreatedBy())
                .isEqualTo("Account with id " + senderId);
    }

    @Test
    void doTransferFailed() throws JsonProcessingException {
        // Получаем валидные данные транзакции
        final Long senderId = 100L;
        final Long recipientId = 99L;
        final Long accountNumber = 1111_1111_1111_1111L;
        Double amount = 300.0;

        AccountTransferDto transferDto = AccountTransferDto.builder()
                .accountDetailsId(senderId)
                .accountNumber(accountNumber)
                .amount(amount)
                .purpose("IT test")
                .build();

        //Запрашиваем отправителя перевода
        Account senderExpected = Account.builder()
                .id(senderId)
                .money(1000.0)
                .build();
        ACCOUNT_SERVICE.stubFor(get("/api/account/" + senderId)
                .willReturn(okJson(jsonMapper.writeValueAsString(senderExpected))));

        // Запрашиваем получателя перевода
        Account recipientExpected = Account.builder()
                .id(recipientId)
                .accountNumber(accountNumber)
                .money(0.0)
                .build();
        ACCOUNT_SERVICE.stubFor(get("/api/account?account_number=" + accountNumber)
                .willReturn(okJson(jsonMapper.writeValueAsString(recipientExpected))));


        // Отправляем данные на сервер, получаем ошибку
        ACCOUNT_SERVICE.stubFor(patch(urlMatching("/api/account/" + senderId))
                .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));
        ACCOUNT_SERVICE.stubFor(patch(urlMatching("/api/account/" + recipientId))
                .willReturn(aResponse().withStatus(200)));

        Assertions.assertThrows(TransferFailedException.class,
                () -> transferService.doTransfer(transferDto));

        // Проверяем, что транзакция откатилась назад и не сохранилась
        assertThat(repository.findAll().size()).isEqualTo(0);
        assertThat(repository.existsByAccountDetailsId(senderId)).isFalse();

        // Проверяем запись в сервисе аудите

        assertThat(auditService.getAllAudits().size()).isEqualTo(1);
        assertThat(auditService.getAllAudits().get(0).getOperationType())
                .isEqualTo(AuditOperationType.FAILED_TRANSFER);
        assertThat(auditService.getAllAudits().get(0).getCreatedBy())
                .isEqualTo("Account with id " + senderId);
    }
}
