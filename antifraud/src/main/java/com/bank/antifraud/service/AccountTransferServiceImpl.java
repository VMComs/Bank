package com.bank.antifraud.service;


import com.bank.antifraud.entity.SuspAccountTransfer;
import com.bank.antifraud.repository.SuspAccountTransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой для работы с переводами по аккаунту
 */
@Service
@AllArgsConstructor
public class AccountTransferServiceImpl implements AccountTransferService {

    private final SuspAccountTransferRepository repository;
    private final ConverterService converter;

    /** Получение всех переводов */
    @Override
    public List<SuspAccountTransfer> findAll() {
        return repository.findAll();
    }

    /** Сохранение перевода */
    @Override
    public void saveTransfer(SuspAccountTransfer transfer) {
        repository.save(transfer);
    }

    /** Получение перевода по id */
    public SuspAccountTransfer getTransferById(Long id) {
        Optional<SuspAccountTransfer> transfer = repository.findById(id);
        return transfer.orElse(null);
    }

}
