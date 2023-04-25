package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspAccountTransferDTO;
import com.bank.antifraud.entity.SuspAccountTransfer;
import com.bank.antifraud.service.AccountTransferServiceImpl;
import com.bank.antifraud.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для транзакций по аккаунту
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account-transfers")
public class SuspAccountTransferController {

    private final AccountTransferServiceImpl transferService;
    private final ConverterService converter;

    /** Получение всех переводов */
    @GetMapping("/account-transfers")
    public ResponseEntity<List<SuspAccountTransferDTO>> getAllTransfers() {
        List<SuspAccountTransfer> allTransfers = transferService.findAll();
        return ResponseEntity.ok(ConverterService.convertToList(allTransfers, converter::convertToDTO));
    }

    /** Получение перевода по Id */
    @GetMapping("/account-transfers/{id}")
    public ResponseEntity<SuspAccountTransferDTO> getTransferById(@PathVariable("id") Long id) {
        if(transferService.getTransferById(id)==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            SuspAccountTransferDTO transferDTO = converter.convertToDTO(transferService.getTransferById(id));
            return ResponseEntity.ok(transferDTO);
        }
    }

    /** Отправка информации об обработанном переводе */
    @PostMapping("/account-transfers")
    public ResponseEntity<SuspAccountTransferDTO> addTransfer(@RequestBody SuspAccountTransferDTO transferDTO) {
        SuspAccountTransfer newTransfer = converter.convertToEntity(transferDTO);
        transferService.saveTransfer(newTransfer);
        return ResponseEntity.ok(converter.convertToDTO(newTransfer));
    }
}
