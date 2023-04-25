package com.bank.transfer.controllers;

import com.bank.transfer.dtos.PhoneTransferDto;
import com.bank.transfer.services.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest контроллер банковских переводов по номеру телефона
 */
@RestController
@RequestMapping("/phone")
@AllArgsConstructor
public class PhoneTransferController {
    private final TransferService<PhoneTransferDto> transferService;

    /**
     * Rest-метод, принимающий POST запрос с dto транзакции:
     * @param transferDto        - запрос банковской транзакции, включает в себя тип перевода,
     *                             отправителя, получателя, сумму и цель перевода
     * @return в случае успешной транзакции - Http ответ ACCEPTED
     */
    @PostMapping
    public ResponseEntity<HttpStatus> doPhoneTransfer(@Valid @RequestBody PhoneTransferDto transferDto) {
        transferService.doTransfer(transferDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    /**
     * Rest-метод, возвращающий список всех банковских транзакций по номеру телефона
     * @return список банковских транзакций по номеру телефона
     */
    @GetMapping
    public ResponseEntity<List<PhoneTransferDto>> getPhoneTransfers() {
        final List<PhoneTransferDto> transfers = transferService.getAllTransfers();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
    /**
     * Rest-метод, возвращающий банковскую транзакцию по номеру телефона по ее id номеру
     * @return список банковских транзакций по номеру счета
     */
    @GetMapping("/{id}")
    public ResponseEntity<PhoneTransferDto> getPhoneTransferById(@PathVariable Long id) {
        final PhoneTransferDto transfer = transferService.getTransferById(id);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }
    /**
     * Rest-метод, возвращающий банковскую транзакцию по номеру счета по id отправителя
     * @return список банковских транзакций по номеру счета
     */
    @GetMapping(params = {"sender_id"})
    public ResponseEntity<List<PhoneTransferDto>> getPhoneTransfersBySenderId(@RequestParam("sender_id") Long id) {
        final List<PhoneTransferDto> transfers = transferService.getTransfersBySenderId(id);
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
}
