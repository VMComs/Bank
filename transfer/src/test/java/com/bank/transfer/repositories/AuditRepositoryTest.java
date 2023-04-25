package com.bank.transfer.repositories;

import com.bank.transfer.entities.AccountTransfer;
import com.bank.transfer.entities.AuditEntity;
import com.bank.transfer.entities.AuditOperationType;
import com.bank.transfer.entities.CardTransfer;
import com.bank.transfer.entities.PhoneTransfer;
import com.bank.transfer.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class AuditRepositoryTest extends IntegrationTestBase {
    @Autowired
    private AuditRepository repository;
    private void insertAudits() {
        AuditEntity audit1 = AuditEntity.builder()
                .id(1L)
                .entityType(AccountTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 100")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        AuditEntity audit2 = AuditEntity.builder()
                .id(2L)
                .entityType(CardTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 140")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        AuditEntity audit3 = AuditEntity.builder()
                .id(3L)
                .entityType(PhoneTransfer.class.getSimpleName())
                .operationType(AuditOperationType.FAILED_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 42")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        repository.save(audit1);
        repository.save(audit2);
        repository.save(audit3);
        repository.flush();
    }
    @Test
    void save() {
        AuditEntity expectedResult = AuditEntity.builder()
                .id(1L)
                .entityType(AccountTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 100")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        AuditEntity actualResult = repository.save(expectedResult);

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void findById() {
        insertAudits();
        AuditEntity expectedResult = AuditEntity.builder()
                .id(1L)
                .entityType(AccountTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 100")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();

        Optional<AuditEntity> actualResult = repository.findById(1L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(expectedResult.getId());
        assertThat(actualResult.get().getEntityType()).isEqualTo(expectedResult.getEntityType());
        assertThat(actualResult.get().getOperationType()).isEqualTo(expectedResult.getOperationType());
        assertThat(actualResult.get().getCreatedBy()).isEqualTo(expectedResult.getCreatedBy());
        assertThat(actualResult.get().getEntityJson()).isEqualTo(expectedResult.getEntityJson());
    }

    @Test
    void findAllByOperationType() {
        insertAudits();

        List<AuditEntity> actualResult = repository.findAllByOperationType(AuditOperationType.FAILED_TRANSFER);

        assertThat(actualResult.size()).isEqualTo(1);
    }
}