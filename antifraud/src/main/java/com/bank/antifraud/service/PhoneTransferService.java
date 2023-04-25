package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspPhoneTransfer;

import java.util.List;

/**
 * Сервисный слой для работы с переводами по номеру телефона
 */
public interface PhoneTransferService {
    /** Получение всех переводов */
    List<SuspPhoneTransfer> findAll();

    /** Сохранение перевода */
    void saveTransfer(SuspPhoneTransfer transfer);
}
