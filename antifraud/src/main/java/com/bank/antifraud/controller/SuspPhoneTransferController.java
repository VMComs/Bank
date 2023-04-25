package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspPhoneTransferDTO;
import com.bank.antifraud.entity.SuspPhoneTransfer;
import com.bank.antifraud.service.ConverterService;
import com.bank.antifraud.service.PhoneTransferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для транзакций по номеру телефона
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/phone-transfers")
public class SuspPhoneTransferController {
    private final PhoneTransferServiceImpl transferService;
    private final ConverterService converter;

    /** Получение всех переводов */
    @GetMapping("/phone-transfers")
    public ResponseEntity<List<SuspPhoneTransferDTO>> getAllTransfers() {
        List<SuspPhoneTransfer> allTransfers = transferService.findAll();
        return ResponseEntity.ok(ConverterService.convertToList(allTransfers, converter::convertToDTO));
    }

    /** Получение перевода по Id */
    @GetMapping("/phone-transfers/{id}")
    public ResponseEntity<SuspPhoneTransferDTO> getTransfer(@PathVariable("id") Long id) {
        if(transferService.getTransferById(id)==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            SuspPhoneTransferDTO transferDTO = converter.convertToDTO(transferService.getTransferById(id));
            return ResponseEntity.ok(transferDTO);
        }
    }

    /** Отправка информации об обработанном переводе */
    @PostMapping("/phone-transfers")
    public ResponseEntity<SuspPhoneTransferDTO> addTransfer(@RequestBody SuspPhoneTransferDTO transferDTO) {
        SuspPhoneTransfer newTransfer = converter.convertToEntity(transferDTO);
        transferService.saveTransfer(newTransfer);
        return ResponseEntity.ok(converter.convertToDTO(newTransfer));
    }
}
