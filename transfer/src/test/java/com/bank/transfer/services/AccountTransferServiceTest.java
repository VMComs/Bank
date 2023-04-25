package com.bank.transfer.services;

import com.bank.transfer.dtos.AccountTransferDto;
import com.bank.transfer.entities.AccountTransfer;
import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.exceptions.TransferNotFoundException;
import com.bank.transfer.pojos.Account;
import com.bank.transfer.repositories.AccountTransferRepository;
import com.bank.transfer.utils.AccountSearcherUtil;
import com.bank.transfer.utils.TransferUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTransferServiceTest {
    @Mock
    private AccountTransferRepository repository;
    @Mock
    private AccountSearcherUtil searcherUtil;
    @Mock
    private TransferUtil transferUtil;
    @InjectMocks
    private AccountTransferService transferService;


    private List<AccountTransfer> getAccountTransfers() {
        AccountTransfer accountTransfer1 = AccountTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("чмок в пупок")
                .accountNumber(12345L)
                .build();
        AccountTransfer accountTransfer2 = AccountTransfer.builder()
                .id(2L)
                .accountDetailsId(1L)
                .amount(150.0)
                .purpose("просто так")
                .accountNumber(100500L)
                .build();
        AccountTransfer accountTransfer42 = AccountTransfer.builder()
                .id(42L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .accountNumber(12345L)
                .build();


        List<AccountTransfer> transfers = new ArrayList<>();
        transfers.add(accountTransfer1);
        transfers.add(accountTransfer2);
        transfers.add(accountTransfer42);

        return transfers;
    }
    @Test
    void getAllAccountTransfers() {
        doReturn(getAccountTransfers()).when(repository).findAll();

        List<AccountTransferDto> actualResult = transferService.getAllTransfers();

        assertThat(actualResult.size()).isEqualTo(3);
    }
    @Test
    void getAccountTransferById() {
        AccountTransfer accountTransfer42 = AccountTransfer.builder()
                .id(42L)
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .accountNumber(12345L)
                .build();
        Optional<AccountTransfer> transfer = Optional.of(accountTransfer42);

        doReturn(transfer).when(repository).findById(42L);

        AccountTransferDto expectedResult = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .accountNumber(12345L)
                .build();

        AccountTransferDto actualResult = transferService.getTransferById(42L);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void getAccountTransferByIdFailed() {
        Optional<AccountTransfer> empty = Optional.empty();
        doReturn(empty).when(repository).findById(any());

        Exception exception = catchException(() -> transferService.getTransferById(100500L));

        assertThat(exception).isInstanceOf(TransferNotFoundException.class);
    }
    private List<AccountTransfer> getAccountTransfersBySenderId69() {
        AccountTransfer accountTransfer1 = AccountTransfer.builder()
                .id(104L)
                .accountDetailsId(69L)
                .amount(1300.0)
                .purpose("на пиво")
                .accountNumber(12345L)
                .build();

        List<AccountTransfer> transfers = new ArrayList<>();
        transfers.add(accountTransfer1);

        return transfers;
    }
    @Test
    void getAccountTransfersBySenderId() {
        doReturn(getAccountTransfersBySenderId69()).when(repository).findAllByAccountDetailsId(69L);
        doReturn(true).when(repository).existsByAccountDetailsId(69L);

        List<AccountTransferDto> actualResult = transferService.getTransfersBySenderId(69L);

        assertThat(actualResult.size()).isEqualTo(1);
    }
    @Test
    void getAccountTransfersBySenderIdFailed() {
        doReturn(false).when(repository).existsByAccountDetailsId(any());

        Exception exception = catchException(() -> transferService.getTransfersBySenderId(100500L));

        assertThat(exception).isInstanceOf(AccountNotFoundException.class);
    }

}