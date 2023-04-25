package com.bank.antifraud.service;

import com.bank.antifraud.entity.SuspPhoneTransfer;
import com.bank.antifraud.repository.SuspPhoneTransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой для работы с переводами по номеру телефона
 */
@Service
@AllArgsConstructor
public class PhoneTransferServiceImpl implements PhoneTransferService {
    private final SuspPhoneTransferRepository repository;
    private final ConverterService converter;

    /** Получение перевода по id */
    public SuspPhoneTransfer getTransferById(Long id) {
        Optional<SuspPhoneTransfer> transfer = repository.findById(id);
        return transfer.orElse(null);
    }

    /** Получение всех переводов */
    public List<SuspPhoneTransfer> findAll() {
        return repository.findAll();
    }

    /** Сохранение перевода */
    @Override
    public void saveTransfer(SuspPhoneTransfer transfer) {
        repository.save(transfer);
    }
}
