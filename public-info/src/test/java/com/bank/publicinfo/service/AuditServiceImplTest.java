package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.*;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.AuditRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Tag("audit")
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    private AuditRepository mockRepository;

    private AuditServiceImpl service;

    private final Audit ENTITY = new Audit(EntityType.ENTITY_TYPE_ONE, OperationType.OPERATION_TYPE_ONE, "createdBy",
            Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 12, 0, 0, 0)), "entityJson");

    @BeforeEach
    void prepare() {
        this.mockRepository = Mockito.mock(AuditRepository.class);
        this.service = new AuditServiceImpl(mockRepository);
    }

    @Nested
    @DisplayName("Поиск аудита по ID")
    @Tag("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Аудит с существующим ID найден")
        void findByCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findById(1L)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске несуществующего аудита")
        void exceptionWhenFindByIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение списка всех аудитов")
    @Tag("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Список аудитов пуст, если в него не добавлены объекты")
        void listIsEmptyByDefault() {

            when(mockRepository.findAll()).thenReturn(Collections.emptyList());

            assertThat(service.findAll()).isEqualTo(Collections.emptyList());
        }

        @Test
        @DisplayName("Список аудитов не пуст, если в него добавлены объекты")
        void listIsNotEmpty() {

            final List<Audit> expectedResult = List.of(ENTITY);

            when(mockRepository.findAll()).thenReturn(expectedResult);

            assertThat(service.findAll()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Удаление аудита по ID")
    @Tag("delete")
    class DeleteTest {

        @Test
        @DisplayName("Аудит с существующим ID удален")
        void deletedWithCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            service.delete(1L);

            verify(mockRepository).delete(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при удалении несуществующего аудита")
        void exceptionWhenDeletedWithIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Сохранение аудита")
    @Tag("save")
    class SaveTest {

        @Test
        @DisplayName("Аудит сохранен")
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
