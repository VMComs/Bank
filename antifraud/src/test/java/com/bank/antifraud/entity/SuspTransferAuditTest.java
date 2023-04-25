package com.bank.antifraud.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

class SuspTransferAuditTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link SuspTransferAudit#SuspTransferAudit()}
     *   <li>{@link SuspTransferAudit#setCreatedAt(Timestamp)}
     *   <li>{@link SuspTransferAudit#setCreatedBy(String)}
     *   <li>{@link SuspTransferAudit#setEntityJson(String)}
     *   <li>{@link SuspTransferAudit#setEntityType(String)}
     *   <li>{@link SuspTransferAudit#setId(Long)}
     *   <li>{@link SuspTransferAudit#setModifiedAt(Timestamp)}
     *   <li>{@link SuspTransferAudit#setModifiedBy(String)}
     *   <li>{@link SuspTransferAudit#setNewEntityJson(String)}
     *   <li>{@link SuspTransferAudit#setOperationType(String)}
     *   <li>{@link SuspTransferAudit#getCreatedBy()}
     *   <li>{@link SuspTransferAudit#getEntityJson()}
     *   <li>{@link SuspTransferAudit#getEntityType()}
     *   <li>{@link SuspTransferAudit#getId()}
     *   <li>{@link SuspTransferAudit#getModifiedBy()}
     *   <li>{@link SuspTransferAudit#getNewEntityJson()}
     *   <li>{@link SuspTransferAudit#getOperationType()}
     * </ul>
     */
    @Test
    void testConstructor() {
        SuspTransferAudit actualSuspTransferAudit = new SuspTransferAudit();
        actualSuspTransferAudit.setCreatedAt(mock(Timestamp.class));
        actualSuspTransferAudit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        actualSuspTransferAudit.setEntityJson("Entity Json");
        actualSuspTransferAudit.setEntityType("Entity Type");
        actualSuspTransferAudit.setId(1L);
        actualSuspTransferAudit.setModifiedAt(mock(Timestamp.class));
        actualSuspTransferAudit.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        actualSuspTransferAudit.setNewEntityJson("New Entity Json");
        actualSuspTransferAudit.setOperationType("Operation Type");
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualSuspTransferAudit.getCreatedBy());
        assertEquals("Entity Json", actualSuspTransferAudit.getEntityJson());
        assertEquals("Entity Type", actualSuspTransferAudit.getEntityType());
        assertEquals(1L, actualSuspTransferAudit.getId().longValue());
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualSuspTransferAudit.getModifiedBy());
        assertEquals("New Entity Json", actualSuspTransferAudit.getNewEntityJson());
        assertEquals("Operation Type", actualSuspTransferAudit.getOperationType());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link SuspTransferAudit#SuspTransferAudit(Long, String, String, String, String, Timestamp, Timestamp, String, String)}
     *   <li>{@link SuspTransferAudit#setCreatedAt(Timestamp)}
     *   <li>{@link SuspTransferAudit#setCreatedBy(String)}
     *   <li>{@link SuspTransferAudit#setEntityJson(String)}
     *   <li>{@link SuspTransferAudit#setEntityType(String)}
     *   <li>{@link SuspTransferAudit#setId(Long)}
     *   <li>{@link SuspTransferAudit#setModifiedAt(Timestamp)}
     *   <li>{@link SuspTransferAudit#setModifiedBy(String)}
     *   <li>{@link SuspTransferAudit#setNewEntityJson(String)}
     *   <li>{@link SuspTransferAudit#setOperationType(String)}
     *   <li>{@link SuspTransferAudit#getCreatedBy()}
     *   <li>{@link SuspTransferAudit#getEntityJson()}
     *   <li>{@link SuspTransferAudit#getEntityType()}
     *   <li>{@link SuspTransferAudit#getId()}
     *   <li>{@link SuspTransferAudit#getModifiedBy()}
     *   <li>{@link SuspTransferAudit#getNewEntityJson()}
     *   <li>{@link SuspTransferAudit#getOperationType()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        SuspTransferAudit actualSuspTransferAudit = new SuspTransferAudit(1L, "Entity Type", "Operation Type",
                "Jan 1, 2020 8:00am GMT+0100", "Jan 1, 2020 9:00am GMT+0100", mock(Timestamp.class), mock(Timestamp.class),
                "New Entity Json", "Entity Json");
        actualSuspTransferAudit.setCreatedAt(mock(Timestamp.class));
        actualSuspTransferAudit.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        actualSuspTransferAudit.setEntityJson("Entity Json");
        actualSuspTransferAudit.setEntityType("Entity Type");
        actualSuspTransferAudit.setId(1L);
        actualSuspTransferAudit.setModifiedAt(mock(Timestamp.class));
        actualSuspTransferAudit.setModifiedBy("Jan 1, 2020 9:00am GMT+0100");
        actualSuspTransferAudit.setNewEntityJson("New Entity Json");
        actualSuspTransferAudit.setOperationType("Operation Type");
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualSuspTransferAudit.getCreatedBy());
        assertEquals("Entity Json", actualSuspTransferAudit.getEntityJson());
        assertEquals("Entity Type", actualSuspTransferAudit.getEntityType());
        assertEquals(1L, actualSuspTransferAudit.getId().longValue());
        assertEquals("Jan 1, 2020 9:00am GMT+0100", actualSuspTransferAudit.getModifiedBy());
        assertEquals("New Entity Json", actualSuspTransferAudit.getNewEntityJson());
        assertEquals("Operation Type", actualSuspTransferAudit.getOperationType());
    }
}

