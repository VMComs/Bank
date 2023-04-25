package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.BranchRepository;
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


@Tag("branch")
@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {


    private BranchRepository mockRepository;

    private BranchServiceImpl service;

    private final Branch ENTITY = new Branch("address", 0L, "city", Time.valueOf(LocalTime.of(12, 0, 0)),
            Time.valueOf(LocalTime.of(12, 0, 0)));

    @BeforeEach
    void prepare() {
        this.mockRepository = Mockito.mock(BranchRepository.class);
        this.service = new BranchServiceImpl(mockRepository);
    }

    @Nested
    @DisplayName("Поиск отделения по ID")
    @Tag("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Отделение с существующим ID найдено")
        void findByCorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findById(0L)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске несуществующего отделения")
        void exceptionWhenFindByIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение списка всех отделений")
    @Tag("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Список отделений пуст, если в него не добавлены объекты")
        void listIsEmptyByDefault() {

            when(mockRepository.findAll()).thenReturn(Collections.emptyList());

            assertThat(service.findAll()).isEqualTo(Collections.emptyList());
        }

        @Test
        @DisplayName("Список отделений не пуст, если в него добавлены объекты")
        void listIsNotEmpty() {

            final List<Branch> expectedResult = List.of(ENTITY);

            when(mockRepository.findAll()).thenReturn(expectedResult);

            assertThat(service.findAll()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Удаление отделения по ID")
    @Tag("delete")
    class DeleteTest {

        @Test
        @DisplayName("Отделение с существующим ID удалено")
        void deletedWithCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            service.delete(1L);

            verify(mockRepository).delete(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при удалении несуществующего отделения")
        void exceptionWhenDeletedWithIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Сохранение отделения")
    @Tag("save")
    class SaveTest {

        @Test
        @DisplayName("Отделение сохранено")
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
}
