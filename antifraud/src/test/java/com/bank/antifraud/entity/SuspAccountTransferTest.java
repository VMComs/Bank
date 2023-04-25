package com.bank.antifraud.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SuspAccountTransferTest {

    @Test
    void testConstructor() {
        SuspAccountTransfer testSuspAccountTransfer = new SuspAccountTransfer("Suspicious operation");
        testSuspAccountTransfer.setId(1L);
        assertEquals(1L, testSuspAccountTransfer.getId().longValue());
    }

    @Test
    void testConstructor2() {
        SuspAccountTransfer testSuspAccountTransfer = new SuspAccountTransfer("Suspicious operation");
        testSuspAccountTransfer.setAccountTransferId(1);
        testSuspAccountTransfer.setBlocked(true);
        testSuspAccountTransfer.setBlockedReason("Suspicious operation");
        testSuspAccountTransfer.setId(1L);
        testSuspAccountTransfer.setSuspicious(true);
        testSuspAccountTransfer.setSuspiciousReason("Suspicious operation");
        assertEquals(1, testSuspAccountTransfer.getAccountTransferId());
        assertEquals("Suspicious operation", testSuspAccountTransfer.getBlockedReason());
        assertEquals(1L, testSuspAccountTransfer.getId().longValue());
        assertEquals("Suspicious operation", testSuspAccountTransfer.getSuspiciousReason());
        assertTrue(testSuspAccountTransfer.isBlocked());
        assertTrue(testSuspAccountTransfer.isSuspicious());
    }
}

