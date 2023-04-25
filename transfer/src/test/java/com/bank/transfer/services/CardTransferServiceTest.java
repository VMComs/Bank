package com.bank.transfer.services;

import com.bank.transfer.dtos.CardTransferDto;
import com.bank.transfer.entities.CardTransfer;
import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.exceptions.TransferNotFoundException;
import com.bank.transfer.repositories.CardTransferRepository;
import com.bank.transfer.utils.AccountSearcherUtil;
import com.bank.transfer.utils.TransferUtil;
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
class CardTransferServiceTest {
    @Mock
    private AccountSearcherUtil searcherUtil;
    @Mock
    private TransferUtil transferUtil;
    @Mock
    private CardTransferRepository cardTransferRepository;
    @InjectMocks
    private CardTransferService transferService;

    private List<CardTransfer> getCardTransfers() {
        CardTransfer cardTransfer1 = CardTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("чмок в пупок")
                .cardNumber(12345L)
                .build();
        CardTransfer cardTransfer42 = CardTransfer.builder()
                .id(42L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .cardNumber(12345L)
                .build();

        List<CardTransfer> transfers = new ArrayList<>();
        transfers.add(cardTransfer1);
        transfers.add(cardTransfer42);

        return transfers;
    }
    @Test
    void getAllCardTransfers() {
        doReturn(getCardTransfers()).when(cardTransferRepository).findAll();

        List<CardTransferDto> actualResult = transferService.getAllTransfers();

        assertThat(actualResult.size()).isEqualTo(2);
    }
    @Test
    void getCardTransferById() {
        CardTransfer cardTransfer42 = CardTransfer.builder()
                .id(42L)
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .cardNumber(12345L)
                .build();
        Optional<CardTransfer> transfer = Optional.of(cardTransfer42);

        doReturn(transfer).when(cardTransferRepository).findById(42L);

        CardTransferDto expectedResult = CardTransferDto.builder()
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .cardNumber(12345L)
                .build();

        CardTransferDto actualResult = transferService.getTransferById(42L);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void getCardTransferByIdFailed() {
        Optional<CardTransfer> empty = Optional.empty();
        doReturn(empty).when(cardTransferRepository).findById(any());

        Exception exception = catchException(() -> transferService.getTransferById(100500L));

        assertThat(exception).isInstanceOf(TransferNotFoundException.class);
    }
    private List<CardTransfer> getCardTransfersBySenderId69() {
        CardTransfer cardTransfer1 = CardTransfer.builder()
                .id(46L)
                .accountDetailsId(69L)
                .amount(250.0)
                .purpose("на шаверму")
                .cardNumber(12345L)
                .build();
        CardTransfer cardTransfer2 = CardTransfer.builder()
                .id(47L)
                .accountDetailsId(69L)
                .amount(250.0)
                .purpose("на еще одну шаверму")
                .cardNumber(12345L)
                .build();

        List<CardTransfer> transfers = new ArrayList<>();
        transfers.add(cardTransfer1);
        transfers.add(cardTransfer2);

        return transfers;
    }
    @Test
    void getCardTransfersBySenderId() {
        doReturn(getCardTransfersBySenderId69()).when(cardTransferRepository).findAllByAccountDetailsId(69L);
        doReturn(true).when(cardTransferRepository).existsByAccountDetailsId(69L);

        List<CardTransferDto> actualResult = transferService.getTransfersBySenderId(69L);

        assertThat(actualResult.size()).isEqualTo(2);
    }
    @Test
    void getCardTransfersBySenderIdFailed() {
        doReturn(false).when(cardTransferRepository).existsByAccountDetailsId(any());

        Exception exception = catchException(() -> transferService.getTransfersBySenderId(100500L));

        assertThat(exception).isInstanceOf(AccountNotFoundException.class);
    }

}