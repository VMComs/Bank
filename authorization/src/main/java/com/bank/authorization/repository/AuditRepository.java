package com.bank.authorization.repository;

import com.bank.authorization.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA-репозиторий для сущности Audit
 */

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
