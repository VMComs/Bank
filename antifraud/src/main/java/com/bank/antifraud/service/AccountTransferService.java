package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspAccountTransfer;

import java.util.List;

/**
 * Сервисный слой для работы с переводами по аккаунту
 */
public interface AccountTransferService {
    /** Получение всех переводов */
    List<SuspAccountTransfer> findAll();

    /** Сохранение перевода */
    void saveTransfer(SuspAccountTransfer transfer);

}
