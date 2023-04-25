package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.LicenseRepository;
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


@Tag("license")
@ExtendWith(MockitoExtension.class)
class LicenseServiceImplTest {


    private LicenseRepository mockRepository;

    private LicenseServiceImpl service;

    private final BankDetails BANK_DETAILS = new BankDetails(0L, 0L, 0L, 0L, "city", "jointStockCompany", "name");

    private final License ENTITY = new License(new Byte[]{(byte) 0b0}, BANK_DETAILS);

    @BeforeEach
    void prepare() {
        this.mockRepository = Mockito.mock(LicenseRepository.class);
        this.service = new LicenseServiceImpl(mockRepository);
    }

    @Nested
    @DisplayName("Поиск лицензии по ID")
    @Tag("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Лицензия с существующим ID найден")
        void findByCorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findById(0L)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске несуществующей лицензии")
        void exceptionWhenFindByIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение списка всех лицензий")
    @Tag("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Список лицензий пуст, если в него не добавлены объекты")
        void listIsEmptyByDefault() {

            when(mockRepository.findAll()).thenReturn(Collections.emptyList());

            assertThat(service.findAll()).isEqualTo(Collections.emptyList());
        }

        @Test
        @DisplayName("Список лицензий не пуст, если в него добавлены объекты")
        void listIsNotEmpty() {

            final List<License> expectedResult = List.of(ENTITY);

            when(mockRepository.findAll()).thenReturn(expectedResult);

            assertThat(service.findAll()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Удаление лицензии по ID")
    @Tag("delete")
    class DeleteTest {

        @Test
        @DisplayName("Лицензия с существующим ID удалена")
        void deletedWithCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            service.delete(1L);

            verify(mockRepository).delete(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при удалении несуществующей лицензии")
        void exceptionWhenDeletedWithIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Сохранение лицензии")
    @Tag("save")
    class SaveTest {

        @Test
        @DisplayName("Лицензия сохранена")
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
    @DisplayName("Поиск лицензии по банковским реквизитам")
    @Tag("branch")
    class FindByBranchTest {

        @Test
        @DisplayName("Лицензия с существующими реквизитами найдена")
        void testFindByBranch() {

            when(mockRepository.findByBankDetails(BANK_DETAILS)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findByBankDetails(BANK_DETAILS)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске лицензии по несуществующим реквизитам")
        void absentWhenFindByIncorrectBranch() {

            when(mockRepository.findByBankDetails(BANK_DETAILS)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findByBankDetails(BANK_DETAILS)).isInstanceOf(NotFoundException.class);
        }
    }
}
