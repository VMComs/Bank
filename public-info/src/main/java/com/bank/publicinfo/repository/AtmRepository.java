package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtmRepository extends JpaRepository<Atm, Long> {
    Optional<Atm> findByBranch(Branch branch);
}
