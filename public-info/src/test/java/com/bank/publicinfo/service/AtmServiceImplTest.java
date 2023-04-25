package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.AtmRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Tag("atm")
@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    private AtmRepository mockRepository;

    private AtmServiceImpl service;

    private final Branch BRANCH = new Branch("address", 44444L, "city", Time.valueOf(LocalTime.of(12, 0, 0)),
            Time.valueOf(LocalTime.of(12, 0, 0)));

    private final Atm ENTITY = new Atm("address", false);

    @BeforeEach
    void prepare() {
        this.mockRepository = Mockito.mock(AtmRepository.class);
        this.service = new AtmServiceImpl(mockRepository);
    }

    @Nested
    @DisplayName("Поиск банкомата по ID")
    @Tag("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Банкомат с существующим ID найден")
        void findByCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findById(1L)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске несуществующего банкомата")
        void exceptionWhenFindByIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение списка всех банкоматов")
    @Tag("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Список банкоматов пуст, если в него не добавлены объекты")
        void listIsEmptyByDefault() {

            when(mockRepository.findAll()).thenReturn(Collections.emptyList());

            assertThat(service.findAll()).isEqualTo(Collections.emptyList());
        }

        @Test
        @DisplayName("Список банкоматов не пуст, если в него добавлены объекты")
        void listIsNotEmpty() {

            final List<Atm> expectedResult = List.of(new Atm("address", false));

            when(mockRepository.findAll()).thenReturn(expectedResult);

            assertThat(service.findAll()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Удаление банкомата по ID")
    @Tag("delete")
    class DeleteTest {

        @Test
        @DisplayName("Банкомат с существующим ID удален")
        void deletedWithCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            service.delete(1L);

            verify(mockRepository).delete(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при удалении несуществующего банкомата")
        void exceptionWhenDeletedWithIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Сохранение банкомата")
    @Tag("save")
    class SaveTest {

        @Test
        @DisplayName("Банкомат сохранен")
        void saved() {

            when(mockRepository.save(ENTITY)).thenReturn(ENTITY);

            service.save(ENTITY);

            verify(mockRepository).save(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotExecutedException, если сохраняемая сущность уже есть")
        void savedThrowsNotExecutedException() {

            when(mockRepository.existsById(ENTITY.getId())).thenReturn(true);

            assertThatThrownBy(() -> service.save(ENTITY)).isInstanceOf(NotExecutedException.class);
        }
    }

    @Nested
    @DisplayName("Поиск банкомата по отделению банка")
    @Tag("branch")
    class FindByBranchTest {

        @Test
        @DisplayName("Банкомат с существующим отделением найден")
        void testFindByBranch() {

            when(mockRepository.findByBranch(BRANCH)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findByBranch(BRANCH)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске банкомата по несуществующему отделению")
        void absentWhenFindByIncorrectBranch() {

            when(mockRepository.findByBranch(BRANCH)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findByBranch(BRANCH)).isInstanceOf(NotFoundException.class);
        }
    }
}
