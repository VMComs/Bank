package com.bank.antifraud.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SuspPhoneTransferTest {

    @Test
    void testConstructor() {
        SuspPhoneTransfer testSuspPhoneTransfer = new SuspPhoneTransfer("Suspicious operation");
        testSuspPhoneTransfer.setId(1L);
        assertEquals(1L, testSuspPhoneTransfer.getId().longValue());
    }

    @Test
    void testConstructor2() {
        SuspPhoneTransfer testSuspPhoneTransfer = new SuspPhoneTransfer("Suspicious operation");
        testSuspPhoneTransfer.setPhoneTransferId(1);
        testSuspPhoneTransfer.setBlocked(true);
        testSuspPhoneTransfer.setBlockedReason("Suspicious operation");
        testSuspPhoneTransfer.setId(1L);
        testSuspPhoneTransfer.setSuspicious(true);
        testSuspPhoneTransfer.setSuspiciousReason("Suspicious operation");
        assertEquals(1, testSuspPhoneTransfer.getPhoneTransferId());
        assertEquals("Suspicious operation", testSuspPhoneTransfer.getBlockedReason());
        assertEquals(1L, testSuspPhoneTransfer.getId().longValue());
        assertEquals("Suspicious operation", testSuspPhoneTransfer.getSuspiciousReason());
        assertTrue(testSuspPhoneTransfer.isBlocked());
        assertTrue(testSuspPhoneTransfer.isSuspicious());
    }
}

