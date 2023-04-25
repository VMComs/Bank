package com.bank.antifraud.repository;

import com.bank.antifraud.entity.SuspCardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для подозрительных переводов по карте
 */
@Repository
public interface SuspCardTransferRepository extends JpaRepository<SuspCardTransfer, Integer> {
    Optional<SuspCardTransfer> findById(Long id);

    Optional<SuspCardTransfer> findByCardNumber(int cardNumber);
}
