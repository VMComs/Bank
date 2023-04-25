package com.bank.antifraud.repository;

import com.bank.antifraud.entity.SuspPhoneTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для подозрительных переводов по номеру телефона
 */
@Repository
public interface SuspPhoneTransferRepository extends JpaRepository<SuspPhoneTransfer, Integer> {
    Optional<SuspPhoneTransfer> findById(Long id);
}
