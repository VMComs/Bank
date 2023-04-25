package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.CertificateRepository;
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


@Tag("certificate")
@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {


    private CertificateRepository mockRepository;

    private CertificateServiceImpl service;

    private final BankDetails BANK_DETAILS = new BankDetails(0L, 0L, 0L, 0L, "city", "jointStockCompany", "name");

    private final Certificate ENTITY = new Certificate(new Byte[]{(byte) 0b0}, BANK_DETAILS);

    @BeforeEach
    void prepare() {
        this.mockRepository = Mockito.mock(CertificateRepository.class);
        this.service = new CertificateServiceImpl(mockRepository);
    }

    @Nested
    @DisplayName("Поиск сертификата по ID")
    @Tag("findById")
    class FindByIdTest {

        @Test
        @DisplayName("Сертификат с существующим ID найден")
        void findByCorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findById(0L)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске несуществующего сертификата")
        void exceptionWhenFindByIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Получение списка всех сертификатов")
    @Tag("findAll")
    class FindAllTest {

        @Test
        @DisplayName("Список сертификатов пуст, если в него не добавлены объекты")
        void listIsEmptyByDefault() {

            when(mockRepository.findAll()).thenReturn(Collections.emptyList());

            assertThat(service.findAll()).isEqualTo(Collections.emptyList());
        }

        @Test
        @DisplayName("Список сертификатов не пуст, если в него добавлены объекты")
        void listIsNotEmpty() {

            final List<Certificate> expectedResult = List.of(ENTITY);

            when(mockRepository.findAll()).thenReturn(expectedResult);

            assertThat(service.findAll()).isEqualTo(expectedResult);
        }
    }

    @Nested
    @DisplayName("Удаление сертификата по ID")
    @Tag("delete")
    class DeleteTest {

        @Test
        @DisplayName("Сертификат с существующим ID удален")
        void deletedWithCorrectId() {

            when(mockRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

            service.delete(1L);

            verify(mockRepository).delete(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при удалении несуществующего сертификата")
        void exceptionWhenDeletedWithIncorrectId() {

            when(mockRepository.findById(0L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(0L)).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Сохранение сертификата")
    @Tag("save")
    class SaveTest {

        @Test
        @DisplayName("Сертификат сохранен")
        void saved() {

            when(mockRepository.save(ENTITY)).thenReturn(ENTITY);

            service.save(ENTITY);

            verify(mockRepository).save(ENTITY);
        }
    }

    @Nested
    @DisplayName("Поиск сертификата по банковским реквизитам")
    @Tag("branch")
    class FindByBranchTest {

        @Test
        @DisplayName("Сертификат с существующими реквизитами найден")
        void testFindByBranch() {

            when(mockRepository.findByBankDetails(BANK_DETAILS)).thenReturn(Optional.of(ENTITY));

            assertThat(service.findByBankDetails(BANK_DETAILS)).isEqualTo(ENTITY);
        }

        @Test
        @DisplayName("Выброс NotFoundException при поиске сертификата по несуществующим реквизитам")
        void absentWhenFindByIncorrectBranch() {

            when(mockRepository.findByBankDetails(BANK_DETAILS)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findByBankDetails(BANK_DETAILS)).isInstanceOf(NotFoundException.class);
        }

        @Test
        @DisplayName("Выброс NotExecutedException, если сохраняемая сущность уже есть")
        void savedThrowsNotExecutedException() {

            when(mockRepository.existsById(ENTITY.getId())).thenReturn(true);

            assertThatThrownBy(() -> service.save(ENTITY)).isInstanceOf(NotExecutedException.class);
        }
    }
}
