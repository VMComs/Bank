package com.bank.transfer.repositories;

import com.bank.transfer.entities.PhoneTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс по работе с банковским транзакциями по номеру телефона, имплементирует JpaRepository
 */
public interface PhoneTransferRepository extends JpaRepository<PhoneTransfer, Long> {
    /**
     * Метод, возвращающий список транзакций, совершенных отправителем по его id
     * @param senderId id отправителя
     * @return список транзакций
     */
    List<PhoneTransfer> findAllByAccountDetailsId(Long senderId);

    /**
     * Метод, проверяющий, совершал ли отправитель по заданному id какие-либо переводы
     * @param id id отправителя
     * @return true - если отправитель был найден
     */
    boolean existsByAccountDetailsId(Long id);
}
