package com.bank.history.entity.dto;

import com.bank.history.entity.History;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
/*
 * Класс для тестирования  работы маппера из сущности в дто и обратно
 * Поскольку не создавался отдельно маппер с List, используется аннотация над классом и внедрение через обьявление.
 */
@SpringBootTest
class HistoryMapperTest {
    @Autowired
    private HistoryMapper historyMapper;
    /**
     * тестирование перевода в DTO
     */
    @Test
    void shouldToDTOTest() {
        History history1 = new History();
        history1.setId(50L);
        history1.setAccountAuditId(50l);

        HistoryDTO historyDTO1 = historyMapper.toDTO(history1);

        assertThat(historyDTO1).isNotNull();

        HistoryDTO historyDTO2 = new HistoryDTO();
        historyDTO2.setId(50L);
        historyDTO2.setAccountAuditId(50L);

        assertThat(historyDTO1).isEqualTo(historyDTO2);

        HistoryDTO historyDTO3 = new HistoryDTO();
        historyDTO3.setId(10L);
        historyDTO3.setAccountAuditId(10L);

        assertThat(historyDTO1).isNotEqualTo(historyDTO3);
    }

    /**
     * тестирование перевода в Entity
     */
    @Test
    void shouldToEntityTest() {
        HistoryDTO historyDTO1 = new HistoryDTO();
        historyDTO1.setId(30L);
        historyDTO1.setBankInfoAuditId(30L);

        History history1 = historyMapper.toEntity(historyDTO1);

        assertThat(history1).isNotNull();

        History history2 = new History();
        history2.setId(30L);
        history2.setBankInfoAuditId(30L);

        assertThat(history2).isEqualTo(history1);

        HistoryDTO historyDTO2 =new HistoryDTO();
        historyDTO2.setId(70L);
        historyDTO2.setBankInfoAuditId(70L);

        History history3 = historyMapper.toEntity(historyDTO2);

        assertThat(history1).isNotEqualTo(history3);
    }
}