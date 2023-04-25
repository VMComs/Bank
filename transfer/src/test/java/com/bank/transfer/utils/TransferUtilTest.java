package com.bank.transfer.utils;

import com.bank.transfer.exceptions.InsufficientFundsException;
import com.bank.transfer.feign.AccountFeignClient;
import com.bank.transfer.pojos.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferUtilTest {
    @Mock
    private AccountFeignClient accountService;
    @Mock
    private AccountSearcherUtil searcherUtil;
    @InjectMocks
    private TransferUtil transferUtil;

    @Test
    void getAndCheckSender() {
        Account expectedResult = Account.builder()
                .id(1L)
                .money(300.0)
                .negativeBalance(false)
                .build();
        doReturn(expectedResult).when(searcherUtil).getAccountById(1L);

        Account actualResult = transferUtil.getAndCheckSender(1L, 100.0);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void getAndCheckInsufficientFundsException() {
        Account senderWithoutMoney = Account.builder()
                .id(1L)
                .money(0.0)
                .negativeBalance(false)
                .build();
        doReturn(senderWithoutMoney).when(searcherUtil).getAccountById(1L);

        Exception exception = catchException(() -> transferUtil.getAndCheckSender(1L, 100.0));

        assertThat(exception).isInstanceOf(InsufficientFundsException.class);
    }

    @Test
    void moneyTransfer() {
        Account sender = Account.builder()
                .money(100.0)
                .build();
        Account recipient = Account.builder()
                .money(150.0)
                .build();
        Double amount = 50.0;

        transferUtil.moneyTransfer(sender, recipient, amount);

        assertThat(sender.getMoney()).isEqualTo(50.0);
        assertThat(recipient.getMoney()).isEqualTo(200.0);
    }
}