package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspCardTransfer;
import com.bank.antifraud.repository.SuspCardTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой для работы с переводами по карте
 */
@Service
@RequiredArgsConstructor
public class CardTransferServiceImpl implements CardTransferService {
    private final SuspCardTransferRepository repository;
    private final ConverterService converter;

    /** Получение перевода по id */
    public SuspCardTransfer getTransferById(Long id) {
        Optional<SuspCardTransfer> transfer = repository.findById(id);
        return transfer.orElse(null);
    }

    /** Получение всех переводов */
    @Override
    public List<SuspCardTransfer> findAll() {
        return repository.findAll();
    }

    /** Сохранение перевода */
    @Override
    public void saveTransfer(SuspCardTransfer transfer) {
        repository.save(transfer);
    }

    /** Получение перевода по номеру карты */
    public SuspCardTransfer getTransferByCardNumber(int cardNumber) {
        Optional<SuspCardTransfer> transfer = repository.findByCardNumber(cardNumber);
        return transfer.orElse(null);
    }
}
