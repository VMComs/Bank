package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;

import java.util.Collection;

public interface CertificateService {

    Certificate findById(Long id);

    Certificate findByBankDetails(BankDetails bankDetails);

    Collection<Certificate> findAll();

    void delete(Long id);

    void save(Certificate certificate);
}
