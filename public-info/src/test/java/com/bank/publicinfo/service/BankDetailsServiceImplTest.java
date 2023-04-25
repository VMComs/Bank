package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.BankDetailsRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Tag("bank_details")
@ExtendWith(MockitoExtension.class)
class BankDetailsServiceImplTest {


    private BankDetailsRepository mockRepository;

    private BankDetailsServiceImpl service;

    private final BankDetails ENTITY = new BankDetails(1L, 0L, 0L, 0L, "city", "jointStockCompany", "name");

    @BeforeEach
    void prepare() {
        this.mockRepository = Mockito.mock(BankDetailsRepository.class);
        this.service = new BankDetailsServiceImpl(mockRepository);
    }

    @Nested
    @DisplayName("Поиск реквизитов по ID")
    @Tag("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Реквизиты с существующим ID найдены")
        void findByCorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findById(0L)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске несуществующих реквизитов")
        void exceptionWhenFindByIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение списка всех реквизитов")
    @Tag("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Список реквизитов пуст, если в него не добавлены объекты")
        void listIsEmptyByDefault() {

            when(mockRepository.findAll()).thenReturn(Collections.emptyList());

            assertThat(service.findAll()).isEqualTo(Collections.emptyList());
        }

        @Test
        @DisplayName("Список реквизитов не пуст, если в него добавлены объекты")
        void listIsNotEmpty() {

            final List<BankDetails> expectedResult = List.of(ENTITY);

            when(mockRepository.findAll()).thenReturn(expectedResult);

            assertThat(service.findAll()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Удаление реквизитов по ID")
    @Tag("delete")
    class DeleteTest {

        @Test
        @DisplayName("Реквизиты с существующим ID удалены")
        void deletedWithCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            service.delete(1L);

            verify(mockRepository).delete(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при удалении несуществующих реквизитов")
        void exceptionWhenDeletedWithIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Сохранение реквизитов")
    @Tag("save")
    class SaveTest {

        @Test
        @DisplayName("Реквизиты сохранены")
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
