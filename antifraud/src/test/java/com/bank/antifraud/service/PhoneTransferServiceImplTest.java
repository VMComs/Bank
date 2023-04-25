package com.bank.antifraud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bank.antifraud.entity.SuspPhoneTransfer;
import com.bank.antifraud.repository.SuspPhoneTransferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PhoneTransferServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PhoneTransferServiceImplTest {
    @MockBean
    private ConverterService converterService;

    @Autowired
    private PhoneTransferServiceImpl phoneTransferServiceImpl;

    @MockBean
    private SuspPhoneTransferRepository suspPhoneTransferRepository;

    @Test
    void testGetTransferById() {
        SuspPhoneTransfer suspPhoneTransfer = new SuspPhoneTransfer();
        suspPhoneTransfer.setBlocked(true);
        suspPhoneTransfer.setBlockedReason("Suspicious operation");
        suspPhoneTransfer.setId(1L);
        suspPhoneTransfer.setPhoneTransferId(1);
        suspPhoneTransfer.setSuspicious(true);
        suspPhoneTransfer.setSuspiciousReason("Suspicious operation");
        Optional<SuspPhoneTransfer> ofResult = Optional.of(suspPhoneTransfer);
        when(suspPhoneTransferRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(suspPhoneTransfer, phoneTransferServiceImpl.getTransferById(1L));
        verify(suspPhoneTransferRepository).findById((Long) any());
    }

    @Test
    void testFindAll() {
        ArrayList<SuspPhoneTransfer> suspPhoneTransferList = new ArrayList<>();
        when(suspPhoneTransferRepository.findAll()).thenReturn(suspPhoneTransferList);
        List<SuspPhoneTransfer> actualFindAllResult = phoneTransferServiceImpl.findAll();
        assertSame(suspPhoneTransferList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(suspPhoneTransferRepository).findAll();
    }

    @Test
    void testSaveTransfer() {
        SuspPhoneTransfer suspPhoneTransfer = new SuspPhoneTransfer();
        suspPhoneTransfer.setBlocked(true);
        suspPhoneTransfer.setBlockedReason("Suspicious operation");
        suspPhoneTransfer.setId(1L);
        suspPhoneTransfer.setPhoneTransferId(1);
        suspPhoneTransfer.setSuspicious(true);
        suspPhoneTransfer.setSuspiciousReason("Suspicious operation");
        when(suspPhoneTransferRepository.save((SuspPhoneTransfer) any())).thenReturn(suspPhoneTransfer);

        SuspPhoneTransfer suspPhoneTransfer1 = new SuspPhoneTransfer();
        suspPhoneTransfer1.setBlocked(true);
        suspPhoneTransfer1.setBlockedReason("Suspicious operation");
        suspPhoneTransfer1.setId(1L);
        suspPhoneTransfer1.setPhoneTransferId(1);
        suspPhoneTransfer1.setSuspicious(true);
        suspPhoneTransfer1.setSuspiciousReason("Suspicious operation");
        phoneTransferServiceImpl.saveTransfer(suspPhoneTransfer1);
        verify(suspPhoneTransferRepository).save((SuspPhoneTransfer) any());
        assertEquals("Suspicious operation", suspPhoneTransfer1.getBlockedReason());
        assertTrue(suspPhoneTransfer1.isSuspicious());
        assertTrue(suspPhoneTransfer1.isBlocked());
        assertEquals("Suspicious operation", suspPhoneTransfer1.getSuspiciousReason());
        assertEquals(1, suspPhoneTransfer1.getPhoneTransferId());
        assertEquals(1L, suspPhoneTransfer1.getId().longValue());
    }
}

