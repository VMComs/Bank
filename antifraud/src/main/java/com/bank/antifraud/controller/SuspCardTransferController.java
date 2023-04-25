package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspCardTransferDTO;
import com.bank.antifraud.entity.SuspCardTransfer;
import com.bank.antifraud.service.CardTransferServiceImpl;
import com.bank.antifraud.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для транзакций по карте
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/card-transfers")
public class SuspCardTransferController {

    private final CardTransferServiceImpl transferService;
    private final ConverterService converter;

    /** Получение всех переводов */
    @GetMapping("/card-transfers")
    public ResponseEntity<List<SuspCardTransferDTO>> getAllTransfers() {
        List<SuspCardTransfer> allTransfers = transferService.findAll();
        return ResponseEntity.ok(ConverterService.convertToList(allTransfers, converter::convertToDTO));
    }

    /** Получение перевода по Id */
    @GetMapping("/card-transfers/{id}")
    public ResponseEntity<SuspCardTransferDTO> getTransferById(@PathVariable("id") Long id) {
        if(transferService.getTransferById(id)==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            SuspCardTransferDTO transferDTO = converter.convertToDTO(transferService.getTransferById(id));
            return ResponseEntity.ok(transferDTO);
        }
    }

    /** Отправка информации об обработанном переводе */
    @PostMapping("/card-transfers")
    public ResponseEntity<SuspCardTransferDTO> addTransfer(@RequestBody SuspCardTransferDTO transferDTO) {
        SuspCardTransfer newTransfer = converter.convertToEntity(transferDTO);
        transferService.saveTransfer(newTransfer);
        return ResponseEntity.ok(converter.convertToDTO(newTransfer));
    }

    /** Получение переводов по номеру карты */
    @GetMapping("/card-transfers/{cardNumber}")
    public ResponseEntity<SuspCardTransferDTO> getTransfersByCardNumber(@PathVariable int cardNumber) {
        if (transferService.getTransferByCardNumber(cardNumber) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            SuspCardTransferDTO cardTransfer = converter.convertToDTO(transferService.getTransferByCardNumber(cardNumber));
            return ResponseEntity.ok(cardTransfer);
        }
    }
}

