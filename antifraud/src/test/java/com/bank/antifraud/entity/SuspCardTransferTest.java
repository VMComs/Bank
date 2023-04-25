package com.bank.antifraud.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SuspCardTransferTest {

    @Test
    void testConstructor() {
        SuspCardTransfer testSuspCardTransfer = new SuspCardTransfer("Suspicious operation");
        testSuspCardTransfer.setId(1L);
        assertEquals(1L, testSuspCardTransfer.getId().longValue());
    }

    @Test
    void testConstructor2() {
        SuspCardTransfer testSuspCardTransfer = new SuspCardTransfer("Suspicious operation");
        testSuspCardTransfer.setCardTransferId(1);
        testSuspCardTransfer.setBlocked(true);
        testSuspCardTransfer.setBlockedReason("Suspicious operation");
        testSuspCardTransfer.setId(1L);
        testSuspCardTransfer.setSuspicious(true);
        testSuspCardTransfer.setSuspiciousReason("Suspicious operation");
        assertEquals(1, testSuspCardTransfer.getCardTransferId());
        assertEquals("Suspicious operation", testSuspCardTransfer.getBlockedReason());
        assertEquals(1L, testSuspCardTransfer.getId().longValue());
        assertEquals("Suspicious operation", testSuspCardTransfer.getSuspiciousReason());
        assertTrue(testSuspCardTransfer.isBlocked());
        assertTrue(testSuspCardTransfer.isSuspicious());
    }
}

