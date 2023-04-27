package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.service.TransferService;
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
 * Rest контроллер банковских переводов по номеру счета
 */
@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountTransferController {
    private final TransferService<AccountTransferDto> transferService;

    /**
     * Rest-метод, принимающий POST запрос с dto транзакции:
     * @param transferDto        - запрос банковской транзакции, включает в себя тип перевода,
     *                             отправителя, получателя, сумму и цель перевода
     * @return в случае успешной транзакции - Http ответ ACCEPTED
     */
    @PostMapping
    public ResponseEntity<HttpStatus> doAccountTransfer(@Valid @RequestBody AccountTransferDto transferDto) {
        transferService.doTransfer(transferDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    /**
     * Rest-метод, возвращающий список всех банковских транзакций по номеру счета
     * @return список банковских транзакций по номеру счета
     */
    @GetMapping
    public ResponseEntity<List<AccountTransferDto>> getAccountTransfers() {
        final List<AccountTransferDto> transfers = transferService.getAllTransfers();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
    /**
     * Rest-метод, возвращающий банковскую транзакцию по номеру счета по ее id номеру
     * @return список банковских транзакций по номеру счета
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountTransferDto> getAccountTransferById(@PathVariable Long id) {
        final AccountTransferDto transfer = transferService.getTransferById(id);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }
    /**
     * Rest-метод, возвращающий банковскую транзакцию по номеру счета по id отправителя
     * @return список банковских транзакций по номеру счета
     */
    @GetMapping(params = {"sender_id"})
    public ResponseEntity<List<AccountTransferDto>> getAccountTransfersBySenderId(@RequestParam("sender_id") Long id) {
        final List<AccountTransferDto> transfers = transferService.getTransfersBySenderId(id);
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
}
