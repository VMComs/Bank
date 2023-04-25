package com.bank.history.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * класс тестирования сеттеров и геттеров History
 */

class HistoryTest {
    private History history;
    private Long id;

    @BeforeEach
    void setup() {
        history = new History();
        id = 1L;
    }

    @Test
    void getId() {

        history.setId(id);

        Long result = history.getId();

        assertThat(result).isNotNull().isEqualTo(id);

    }

    @Test
    void getTransferAuditId() {
        history.setTransferAuditId(id);

        Long result = history.getTransferAuditId();

        assertThat(result).isNotNull().isEqualTo(id);
    }

    /**
     * многократная проверка сеттера
     */
    @Test
    void setId() {
        history.setId(id);
        history.setId(5L);
        history.setId(id);

        assertThat(history.getId()).isEqualTo(id);

        history.setId(3L);

        assertThat(history.getId()).isEqualTo(3L);
    }

    @Test
    void setTransferAuditId() {
        history.setTransferAuditId(id);

        assertThat(history.getTransferAuditId()).isEqualTo(id);
    }
}