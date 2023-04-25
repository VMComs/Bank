package com.bank.transfer.services;

import com.bank.transfer.dtos.PhoneTransferDto;
import com.bank.transfer.entities.PhoneTransfer;
import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.exceptions.TransferNotFoundException;
import com.bank.transfer.repositories.PhoneTransferRepository;
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
class PhoneTransferServiceTest {
    @Mock
    private AccountSearcherUtil searcherUtil;
    @Mock
    private TransferUtil transferUtil;
    @Mock
    private PhoneTransferRepository phoneTransferRepository;
    @InjectMocks
    private PhoneTransferService transferService;

    private List<PhoneTransfer> getPhoneTransfers() {
        PhoneTransfer phoneTransfer1 = PhoneTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(42.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - сумма данного перевода")
                .phoneNumber(12345L)
                .build();
        PhoneTransfer phoneTransfer2 = PhoneTransfer.builder()
                .id(2L)
                .accountDetailsId(1L)
                .amount(500.0)
                .purpose("чмок в пупок")
                .phoneNumber(12345L)
                .build();
        PhoneTransfer phoneTransfer3 = PhoneTransfer.builder()
                .id(3L)
                .accountDetailsId(1L)
                .amount(3100.0)
                .purpose("чмок в пупок")
                .phoneNumber(12345L)
                .build();
        PhoneTransfer phoneTransfer42 = PhoneTransfer.builder()
                .id(42L)
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .phoneNumber(12345L)
                .build();

        List<PhoneTransfer> transfers = new ArrayList<>();
        transfers.add(phoneTransfer1);
        transfers.add(phoneTransfer2);
        transfers.add(phoneTransfer3);
        transfers.add(phoneTransfer42);

        return transfers;
    }
    @Test
    void getAllPhoneTransfers() {
        doReturn(getPhoneTransfers()).when(phoneTransferRepository).findAll();

        List<PhoneTransferDto> actualResult = transferService.getAllTransfers();

        assertThat(actualResult.size()).isEqualTo(4);
    }
    @Test
    void getPhoneTransferById() {
        PhoneTransfer phoneTransfer42 = PhoneTransfer.builder()
                .id(42L)
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .phoneNumber(12345L)
                .build();
        Optional<PhoneTransfer> transfer = Optional.of(phoneTransfer42);

        doReturn(transfer).when(phoneTransferRepository).findById(42L);

        PhoneTransferDto expectedResult = PhoneTransferDto.builder()
                .accountDetailsId(1L)
                .amount(708.0)
                .purpose("ответ на самый главный вопрос жизни, вселенной и всего такого - номер данного перевода")
                .phoneNumber(12345L)
                .build();

        PhoneTransferDto actualResult = transferService.getTransferById(42L);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void getPhoneTransferByIdFailed() {
        Optional<PhoneTransfer> empty = Optional.empty();
        doReturn(empty).when(phoneTransferRepository).findById(any());

        Exception exception = catchException(() -> transferService.getTransferById(100500L));

        assertThat(exception).isInstanceOf(TransferNotFoundException.class);
    }
    private List<PhoneTransfer> getPhoneTransfersBySenderId69() {
        PhoneTransfer phoneTransfer1 = PhoneTransfer.builder()
                .id(842L)
                .accountDetailsId(69L)
                .amount(1700.0)
                .purpose("не придумал, просто так)")
                .phoneNumber(12345L)
                .build();
        PhoneTransfer phoneTransfer2 = PhoneTransfer.builder()
                .id(843L)
                .accountDetailsId(69L)
                .amount(1400.0)
                .purpose("не придумал, просто так)")
                .phoneNumber(12345L)
                .build();
        PhoneTransfer phoneTransfer3 = PhoneTransfer.builder()
                .id(844L)
                .accountDetailsId(69L)
                .amount(1100.0)
                .purpose("не придумал, просто так)")
                .phoneNumber(12345L)
                .build();

        List<PhoneTransfer> transfers = new ArrayList<>();
        transfers.add(phoneTransfer1);
        transfers.add(phoneTransfer2);
        transfers.add(phoneTransfer3);

        return transfers;
    }
    @Test
    void getPhoneTransfersBySenderId() {
        doReturn(getPhoneTransfersBySenderId69()).when(phoneTransferRepository).findAllByAccountDetailsId(69L);
        doReturn(true).when(phoneTransferRepository).existsByAccountDetailsId(69L);

        List<PhoneTransferDto> actualResult = transferService.getTransfersBySenderId(69L);

        assertThat(actualResult.size()).isEqualTo(3);
    }
    @Test
    void getPhoneTransfersBySenderIdFailed() {
        doReturn(false).when(phoneTransferRepository).existsByAccountDetailsId(any());

        Exception exception = catchException(() -> transferService.getTransfersBySenderId(100500L));

        assertThat(exception).isInstanceOf(AccountNotFoundException.class);
    }
}