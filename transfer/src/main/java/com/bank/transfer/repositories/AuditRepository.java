package com.bank.transfer.repositories;

import com.bank.transfer.entities.AuditEntity;
import com.bank.transfer.entities.AuditOperationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс по работе с записями аудита, имплементирует JpaRepository
 */
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    /**
     * Метод, возвращающий список записей аудита по успешности выполнения транзакции
     * @param operationType тип выполнения транзакции(успешная/неудачная)
     * @return список записей аудита
     */
    List<AuditEntity> findAllByOperationType(AuditOperationType operationType);

}
