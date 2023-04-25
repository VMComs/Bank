package com.bank.transfer.services;

import com.bank.transfer.dtos.AbstractTransferDto;
import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.exceptions.TransferFailedException;
import com.bank.transfer.exceptions.TransferNotFoundException;

import java.util.List;

/**
 * Интерфейс определяющий бизнес-логику работы банковских переводов
 */
public interface TransferService<D extends AbstractTransferDto> {

    /**
     * Метод, выполняющий транзакцию:
     * @param transferDto        - запрос банковской транзакции, включает в себя:
     *                           отправителя, получателя, сумму и цель перевода
     */
    D doTransfer(D transferDto) throws TransferFailedException;
    /**
     * Метод, возвращающий список всех банковских переводов
     * @return список банковских переводов по номеру телефона
     */
    List<D> getAllTransfers();
    /**
     * Метод, возвращающий экземпляр банковского перевода по id данного перевода
     * @param id - id банковской транзакции
     * @throws TransferNotFoundException - если по указанному id транзакция не была найдена
     * @return экземпляр банковского перевода по номеру телефона
     */
    D getTransferById(Long id) throws TransferNotFoundException;
    /**
     * Метод, возвращающий список переводов, совершенные отправителем
     * @throws AccountNotFoundException - если по указанному id отправитель не был найден
     * @param id - id отправителя
     * @return список банковских переводов по номеру телефона
     */
    List<D> getTransfersBySenderId(Long id) throws AccountNotFoundException;
}
