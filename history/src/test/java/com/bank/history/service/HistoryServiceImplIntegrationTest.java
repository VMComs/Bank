package com.bank.history.service;

import com.bank.history.entity.History;
import com.bank.history.entity.dto.HistoryDTO;
import com.bank.history.entity.dto.HistoryMapper;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * подробное тестирование методов нахождения, сохранения и изменения сущности
 * c использованием базы данных
 *
 */
@SpringBootTest
public class HistoryServiceImplIntegrationTest {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private HistoryServiceImpl historyServiceImpl;

    @BeforeEach
    void setup() {
        historyServiceImpl = new HistoryServiceImpl(historyRepository, historyMapper);
    }

    /**
     * проверка методов репозитория по нахождению и сохранению
     */
    @Test
    void shouldFindAndSaveByIdEntityTest() {
        Long id = 90L;
        History history1 = new History();
        history1.setId(id);
        historyRepository.save(history1);
        HistoryDTO historyDTO1 = historyMapper.toDTO(history1);
        Optional<History> history2 = historyRepository.findById(id);

        assertThat(history1).isNotNull();
        assertThat(history2).isNotNull();

        if (history2.isPresent()) {
            History history3 = history2.get();
            HistoryDTO historyDTO3 = historyMapper.toDTO(history3);

            assertThat(history1).isEqualTo(history3);
            assertThat(history1.getId()).isEqualTo(history3.getId());
            assertThat(historyDTO1).isEqualTo(historyDTO3);
        }
    }

    /**
     * проверка сервиса по нахождению по id, и маппера по правильному переводу
     */
    @Test
    public void canFindByIdTest() {
        History history = new History();
        historyRepository.save(history);
        Long id = history.getId();

        assertThat(history).isNotNull();

        HistoryDTO historyDTO = historyServiceImpl.findById(id);
        History history1 = historyMapper.toEntity(historyDTO);

        assertThat(history1.getId()).isEqualTo(id);
        assertThat(historyDTO.getId()).isEqualTo(id);
    }

    /**
     * другой вариант проверки нахождения по id
     */
    @Test
    void newFindByIdTest() {
        History history = new History();
        historyRepository.save(history);
        Long id = history.getId();
        HistoryDTO historyDTO = historyServiceImpl.findById(id);

        assertTrue(history != null);
        assertTrue(historyDTO != null);
        assertThat(historyDTO.getId()).isEqualTo(id);
    }

    /**
     * тест на ненахождение сущности по id
     */
    @Test
    void notFound() {
        try {
            historyServiceImpl.findById(0L);
        } catch (HistoryNotFoundException exception) {
        }
    }
    /**
     * проверка сервиса на сохранение
     */
    @Test
    void canSaveTest() {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setAccountAuditId(45L);
        historyDTO.setBankInfoAuditId(46L);

        assertTrue(historyDTO != null);

        HistoryDTO historyDTO1 = historyServiceImpl.save(historyDTO);

        assertThat(historyDTO1).isNotNull();
        assertThat(historyDTO1.getAccountAuditId()).isEqualTo(45L);
        assertThat(historyDTO1.getBankInfoAuditId()).isEqualTo(46L);
    }
    /**
     * проверка сервиса на обновление
     */
    @Test
    void shouldUpdateByIdEntityTest() {
        History history1 = new History();
        historyRepository.save(history1);
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setAccountAuditId(1L);
        Long id = history1.getId();
        HistoryDTO historyDTO1 = historyServiceImpl.update(id, historyDTO);

        assertThat(history1).isNotNull();
        assertThat(historyDTO1).isNotNull();
        assertThat(historyDTO1.getId()).isEqualTo(id);

        History history2 = historyMapper.toEntity(historyDTO1);

        assertThat(history2.getId()).isEqualTo(history1.getId());
        assertThat(history2.getAccountAuditId()).isEqualTo(1L);
    }
    /**
     * тестирование получения всех history
     */
    @Test
    void getAllHistoriesTest() {
        List<HistoryDTO> list = historyServiceImpl.getAllHistory();
        History history1 = new History();
        historyRepository.save(history1);
        History history2 = new History();
        historyRepository.save(history2);

        List<HistoryDTO> listAfter = historyServiceImpl.getAllHistory();

        assertThat(history1).isNotNull();
        assertThat(history2).isNotNull();
        assertThat(list.size()).isNotEqualTo(listAfter).isEqualTo((listAfter.size() - 2));

        historyRepository.delete(history1);
        historyRepository.delete(history2);
        listAfter = historyServiceImpl.getAllHistory();

        assertThat(listAfter.size()).isEqualTo(list.size());
        assertThat(list).isNotSameAs(listAfter);
    }

    /**
     * другой варинат проверки на получение всех сущностей
     */
    @Test
    void newGetAllHistoriesTest() {
        List<HistoryDTO> list = historyRepository.findAll().stream().map(historyMapper::toDTO).collect(Collectors.toList());
        History history = new History();
        historyRepository.save(history);
        List<HistoryDTO> listAfter = historyRepository.findAll().stream().map(historyMapper::toDTO).collect(Collectors.toList());

        assertThat(list.size()).isEqualTo(listAfter.size() - 1);
    }

    /**
     * третья модификация проверки на получение всех
     */
    @Test
    void testGetAllHistories() {
        historyRepository.deleteAll();
        History history = new History();
        historyRepository.save(history);
        List<HistoryDTO> list = historyServiceImpl.getAllHistory();

        Assertions.assertEquals(list.size(), 1);
    }
    /**
     * тест на получение всех history, если содержимое таблицы пустое
     */
    @Test
    void allHistoriesNobodyTest() {
        historyRepository.deleteAll();
        List<HistoryDTO> list = historyServiceImpl.getAllHistory();

        Assertions.assertEquals(list.size(), 0);
    }
}
