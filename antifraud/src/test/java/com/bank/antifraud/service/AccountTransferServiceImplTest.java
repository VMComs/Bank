package com.bank.antifraud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.antifraud.entity.SuspAccountTransfer;
import com.bank.antifraud.repository.SuspAccountTransferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountTransferServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AccountTransferServiceImplTest {
    @Autowired
    private AccountTransferServiceImpl accountTransferServiceImpl;

    @MockBean
    private ConverterService converterService;

    @MockBean
    private SuspAccountTransferRepository suspAccountTransferRepository;

    @Test
    void testFindAll() {
        ArrayList<SuspAccountTransfer> suspAccountTransferList = new ArrayList<>();
        when(suspAccountTransferRepository.findAll()).thenReturn(suspAccountTransferList);
        List<SuspAccountTransfer> actualFindAllResult = accountTransferServiceImpl.findAll();
        assertSame(suspAccountTransferList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(suspAccountTransferRepository).findAll();
    }

    @Test
    void testSaveTransfer() {
        SuspAccountTransfer suspAccountTransfer = new SuspAccountTransfer();
        suspAccountTransfer.setAccountTransferId(1);
        suspAccountTransfer.setBlocked(true);
        suspAccountTransfer.setBlockedReason("Suspicious operation");
        suspAccountTransfer.setId(1L);
        suspAccountTransfer.setSuspicious(true);
        suspAccountTransfer.setSuspiciousReason("Suspicious operation");
        when(suspAccountTransferRepository.save((SuspAccountTransfer) any())).thenReturn(suspAccountTransfer);

        SuspAccountTransfer suspAccountTransfer1 = new SuspAccountTransfer();
        suspAccountTransfer1.setAccountTransferId(1);
        suspAccountTransfer1.setBlocked(true);
        suspAccountTransfer1.setBlockedReason("Suspicious operation");
        suspAccountTransfer1.setId(1L);
        suspAccountTransfer1.setSuspicious(true);
        suspAccountTransfer1.setSuspiciousReason("Suspicious operation");
        accountTransferServiceImpl.saveTransfer(suspAccountTransfer1);
        verify(suspAccountTransferRepository).save((SuspAccountTransfer) any());
        assertEquals(1, suspAccountTransfer1.getAccountTransferId());
        assertTrue(suspAccountTransfer1.isSuspicious());
        assertTrue(suspAccountTransfer1.isBlocked());
        assertEquals("Suspicious operation", suspAccountTransfer1.getSuspiciousReason());
        assertEquals(1L, suspAccountTransfer1.getId().longValue());
        assertEquals("Suspicious operation", suspAccountTransfer1.getBlockedReason());
    }

    @Test
    void testGetTransferById() {
        SuspAccountTransfer suspAccountTransfer = new SuspAccountTransfer();
        suspAccountTransfer.setAccountTransferId(1);
        suspAccountTransfer.setBlocked(true);
        suspAccountTransfer.setBlockedReason("Suspicious operation");
        suspAccountTransfer.setId(1L);
        suspAccountTransfer.setSuspicious(true);
        suspAccountTransfer.setSuspiciousReason("Suspicious operation");
        Optional<SuspAccountTransfer> ofResult = Optional.of(suspAccountTransfer);
        when(suspAccountTransferRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(suspAccountTransfer, accountTransferServiceImpl.getTransferById(1L));
        verify(suspAccountTransferRepository).findById((Long) any());
    }
}

