package com.bank.account.service;

import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.model.dto.AuditDTO;
import com.bank.account.model.entity.Audit;
import com.bank.account.model.mapper.AuditMapper;
import com.bank.account.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс имлементирующий интерфейс, содержащий методы CRUD-операций для сущности Audit.
 */

@Service
public class AuditServiceImpl implements AuditService{
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditRepository auditRepository, AuditMapper auditMapper) {
        this.auditRepository = auditRepository;
        this.auditMapper = auditMapper;
    }

    @Override
    public List<Audit> getAll() {
        return auditRepository.findAll();
    }

    @Override
    public Audit getByID(Long id) {
        Audit audit = auditRepository.findById(id).orElseThrow(()
                -> new AccountNotFoundException("Audit for Account with id " + id  + "not found"));
        return auditMapper.convertToDto(audit);
    }

    @Override
    public void save(AuditDTO auditDTO) {
        Audit audit = auditMapper.convertToEntity(auditDTO);
        auditRepository.save(audit);

    }

    @Override
    public void update(AuditDTO auditDTO, Long id) {
        Audit audit = auditMapper.convertToEntity(auditDTO);
        audit.setId(id);
        auditRepository.save(audit);
    }

    @Override
    public void delete(Long id) {
        auditRepository.delete(getByID(id));
    }
}
