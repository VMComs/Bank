package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;

import java.util.Collection;

public interface BankDetailsService {

    BankDetails findById(Long id);

    Collection<BankDetails> findAll();

    void delete(Long id);

    void save(BankDetails bankDetails);
}
