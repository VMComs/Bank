package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;

import java.util.Collection;

public interface LicenseService {

    License findById(Long id);

    License findByBankDetails(BankDetails bankDetails);

    Collection<License> findAll();

    void delete(Long id);

    void save(License license);

}
