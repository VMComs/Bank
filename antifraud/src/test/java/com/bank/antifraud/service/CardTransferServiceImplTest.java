package com.bank.antifraud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.antifraud.entity.SuspCardTransfer;
import com.bank.antifraud.repository.SuspCardTransferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CardTransferServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CardTransferServiceImplTest {
    @Autowired
    private CardTransferServiceImpl cardTransferServiceImpl;

    @MockBean
    private ConverterService converterService;

    @MockBean
    private SuspCardTransferRepository suspCardTransferRepository;

    @Test
    void testGetTransferById() {
        SuspCardTransfer suspCardTransfer = new SuspCardTransfer();
        suspCardTransfer.setBlocked(true);
        suspCardTransfer.setBlockedReason("Suspicious operation");
        suspCardTransfer.setCardTransferId(1);
        suspCardTransfer.setId(1L);
        suspCardTransfer.setSuspicious(true);
        suspCardTransfer.setSuspiciousReason("Suspicious operation");
        Optional<SuspCardTransfer> ofResult = Optional.of(suspCardTransfer);
        when(suspCardTransferRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(suspCardTransfer, cardTransferServiceImpl.getTransferById(1L));
        verify(suspCardTransferRepository).findById((Long) any());
    }

    @Test
    void testFindAll() {
        ArrayList<SuspCardTransfer> suspCardTransferList = new ArrayList<>();
        when(suspCardTransferRepository.findAll()).thenReturn(suspCardTransferList);
        List<SuspCardTransfer> actualFindAllResult = cardTransferServiceImpl.findAll();
        assertSame(suspCardTransferList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(suspCardTransferRepository).findAll();
    }

    @Test
    void testSaveTransfer() {
        SuspCardTransfer suspCardTransfer = new SuspCardTransfer();
        suspCardTransfer.setBlocked(true);
        suspCardTransfer.setBlockedReason("Suspicious operation");
        suspCardTransfer.setCardTransferId(1);
        suspCardTransfer.setId(1L);
        suspCardTransfer.setSuspicious(true);
        suspCardTransfer.setSuspiciousReason("Suspicious operation");
        when(suspCardTransferRepository.save((SuspCardTransfer) any())).thenReturn(suspCardTransfer);

        SuspCardTransfer suspCardTransfer1 = new SuspCardTransfer();
        suspCardTransfer1.setBlocked(true);
        suspCardTransfer1.setBlockedReason("Suspicious operation");
        suspCardTransfer1.setCardTransferId(1);
        suspCardTransfer1.setId(1L);
        suspCardTransfer1.setSuspicious(true);
        suspCardTransfer1.setSuspiciousReason("Suspicious operation");
        cardTransferServiceImpl.saveTransfer(suspCardTransfer1);
        verify(suspCardTransferRepository).save((SuspCardTransfer) any());
        assertEquals("Suspicious operation", suspCardTransfer1.getBlockedReason());
        assertTrue(suspCardTransfer1.isSuspicious());
        assertTrue(suspCardTransfer1.isBlocked());
        assertEquals("Suspicious operation", suspCardTransfer1.getSuspiciousReason());
        assertEquals(1L, suspCardTransfer1.getId().longValue());
        assertEquals(1, suspCardTransfer1.getCardTransferId());
    }

    @Test
    void testGetTransferByCardNumber() {
        SuspCardTransfer suspCardTransfer = new SuspCardTransfer();
        suspCardTransfer.setBlocked(true);
        suspCardTransfer.setBlockedReason("Suspicious operation");
        suspCardTransfer.setCardTransferId(1);
        suspCardTransfer.setId(1L);
        suspCardTransfer.setSuspicious(true);
        suspCardTransfer.setSuspiciousReason("Suspicious operation");
        Optional<SuspCardTransfer> ofResult = Optional.of(suspCardTransfer);
        when(suspCardTransferRepository.findByCardNumber(anyInt())).thenReturn(ofResult);
        assertSame(suspCardTransfer, cardTransferServiceImpl.getTransferByCardNumber(10));
        verify(suspCardTransferRepository).findByCardNumber(anyInt());
    }
}

