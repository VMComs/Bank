package com.bank.account.repository;

import com.bank.account.model.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс, реализующий репозиторий для обращения к БД для сущности Audit
 */

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

}
