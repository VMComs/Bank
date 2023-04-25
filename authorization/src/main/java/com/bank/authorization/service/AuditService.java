package com.bank.authorization.service;

import com.bank.authorization.dto.AuditDTO;

import java.util.List;

/**
 * Интерфейс, реализующая CRUD операции для сущности Audit
 * Реализация в классе AuditServiceImpl
 */

public interface AuditService {
    List<AuditDTO> getAll();

    AuditDTO getById(Long id);

    void add(AuditDTO auditDTO);

    void update(AuditDTO auditDTO, Long id);

    void delete(Long id);
}

