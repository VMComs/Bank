package com.bank.history.service;
import com.bank.history.entity.History;
import com.bank.history.entity.dto.HistoryDTO;
import com.bank.history.entity.dto.HistoryMapper;
import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * класс тестов, альтернативных тем, что описаны в классе HistoryServiceImplTest
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class HistoryServiceImplSecondTest {
    @Mock
    private HistoryRepository historyRepository;
    @Mock
    private History history;
    @Mock
    private HistoryDTO historyDTO;
    @Mock
    private HistoryServiceImpl historyService;
    /**
     * проверка поиска по id
     */
    @Test
    void shouldFindIdTest() {
        Long id = history.getId();
        historyService.findById(id);

        verify(historyService).findById(id);
    }
    /**
     * проверка метода поиска по id на исключение
     */
    @Test
    void whenFindIdExceptTest() {
        Long id = history.getId();
        when(historyService.findById(id)).thenThrow(HistoryNotFoundException.class);

        assertThrows(HistoryNotFoundException.class, () -> historyService.findById(id));
    }
    /**
     * проверка на выполнение метода save()
     */
    @Test
    void shouldSaveOnceTest() {
        historyService.save(historyDTO);

        verify(historyService).save(historyDTO);
    }
    /**
     * проверка на выполнение метода редактирования при редактировании сущности
     */
    @Test
    void shouldUpdateTest() {
        Long id = history.getId();
        historyDTO.setAccountAuditId(1L);
        historyService.update(id, historyDTO);

        verify(historyService).update(id, historyDTO);
    }
    /**
     * проверка на исключение метода update
     */
    @Test
    void whenUpdateExceptTest() {
        Long id = history.getId();
        historyDTO.setAccountAuditId(1L);
        when(historyService.update(id, historyDTO)).thenThrow(HistoryNotFoundException.class);

        assertThrows(HistoryNotFoundException.class, () -> historyService.update(id, historyDTO));
    }
    /**
     * проверка получения всех history
     */
    @Test
    void shouldGetAllHistoryTest() {
        historyService.getAllHistory();

        verify(historyService, only()).getAllHistory();
    }
}