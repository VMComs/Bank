package com.bank.transfer.repository;

import com.bank.transfer.entity.CardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс по работе с банковским транзакциями по номеру карты, имплементирует JpaRepository
 */
public interface CardTransferRepository extends JpaRepository<CardTransfer, Long> {
    /**
     * Метод, возвращающий список транзакций, совершенных отправителем по его id
     * @param senderId id отправителя
     * @return список транзакций
     */
    List<CardTransfer> findAllByAccountDetailsId(Long senderId);

    /**
     * Метод, проверяющий, совершал ли отправитель по заданному id какие-либо переводы
     * @param id id отправителя
     * @return true - если отправитель был найден
     */
    boolean existsByAccountDetailsId(Long id);
}
