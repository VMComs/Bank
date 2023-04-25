package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;

import java.util.Collection;

public interface AuditService {

    Audit findById(Long id);

    Collection<Audit> findAll();

    void delete(Long id);

    void save(Audit audit);
}
