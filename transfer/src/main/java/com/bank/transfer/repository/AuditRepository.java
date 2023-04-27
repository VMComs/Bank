package com.bank.transfer.repository;

import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.entity.AuditOperationType;
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
