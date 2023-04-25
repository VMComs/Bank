package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspCardTransfer;

import java.util.List;

/**
 * Сервисный слой для работы с переводами по карте
 */
public interface CardTransferService {
    /** Получение всех переводов */
    List<SuspCardTransfer> findAll();

    /** Сохранение перевода */
    void saveTransfer(SuspCardTransfer transfer);
}
