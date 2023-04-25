package com.bank.history.service;

import com.bank.history.entity.History;
import com.bank.history.entity.dto.HistoryDTO;
import com.bank.history.entity.dto.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * класс тестов для проверки методов сервиса
 */
@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {
    private HistoryServiceImpl historyServiceImpl;
    private AutoCloseable autoCloseable;
    @Mock
    private HistoryMapper historyMapper;
    @Mock
    private HistoryRepository historyRepository;
    @Mock
    private History history;

    /**
     * при каждом вызове метода класса выполняется метод для создания тестируемого обьекта
     * Создаю обьект сервиса, чтобы не дублировать код методов сервиса
     */
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        historyServiceImpl = new HistoryServiceImpl(historyRepository, historyMapper);
    }
    /**
     * запускается после каждого выполненного метода теста, закрывает ресурсы, может кидать исключение
     *
     */
    @AfterEach
    void setDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * используется метод verify() для определения действительно ли отработал метод JPA Repository для поиска всех записей
     * Проверяю вызывался ли он один только раз only()
     */
    @Test
    void shouldGetAllHistoryTest() {
        historyServiceImpl.getAllHistory();

        verify(historyRepository, only()).findAll();
    }

    /**
     * 1.создаю обьект history с заданным id и сохраняю его
     * 2.затем нахожу обьект с таким же id, используя связку given-willReturn
     * 3. используя возможности класса ArgumentCaptor перехватываю аргумент для проверки
     */
    @Test
    void shouldSaveTest() {
        HistoryDTO historyDTO1 = new HistoryDTO();
        historyDTO1.setId(40L);

        historyServiceImpl.save(historyDTO1);

        given(historyRepository.findById(40L)).willReturn(Optional.of(history));

        historyServiceImpl.findById(40L);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(historyRepository).findById(argumentCaptor.capture());
        Long capId = argumentCaptor.getValue();

        assertThat(capId).isNotNull();
        assertThat(capId).isEqualTo(40L);

    }

    /**
     * задаю id, который будет взят у объекта history, использую связку given-willReturn,
     * так проще решить проблему с Optional
     * ArgumentCaptor позволяет перехватить аргумент, переданный методу для проверки
     * verify на методе findById с capture() используется для захвата id
     * getValue() дает возможность получить значение id, после чего можно сравнивать
     */
    @Test
    void shouldFindByIdTest() {
        Long id = history.getId();
        given(historyRepository.findById(id)).willReturn(Optional.of(history));

        historyServiceImpl.findById(id);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(historyRepository).findById(argumentCaptor.capture());
        Long capId = argumentCaptor.getValue();

        assertThat(capId).isEqualTo(id);
        assertThat(capId).isNotNull();
    }
}