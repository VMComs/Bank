package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;

import java.util.Collection;

public interface BranchService {

    Branch findById(Long id);

    Collection<Branch> findAll();

    void delete(Long id);

    void save(Branch branch);
}
