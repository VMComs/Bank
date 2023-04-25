package com.bank.antifraud.repository;

import com.bank.antifraud.entity.SuspAccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для подозрительных переводов между аккаунтами
 */
@Repository
public interface SuspAccountTransferRepository extends JpaRepository<SuspAccountTransfer, Integer> {
    Optional<SuspAccountTransfer> findById(Long id);
}
