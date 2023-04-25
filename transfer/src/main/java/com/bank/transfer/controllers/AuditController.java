package com.bank.transfer.controllers;

import com.bank.transfer.entities.AuditEntity;
import com.bank.transfer.entities.AuditOperationType;
import com.bank.transfer.services.AuditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest контроллер аудита банковских переводов
 */
@RestController
@AllArgsConstructor
@RequestMapping("/audit")
public class AuditController {
    private final AuditService auditService;
    /**
     * Метод, возвращающий список всех записей аудита
     * @return список записей аудита
     */
    @GetMapping
    public ResponseEntity<List<AuditEntity>> getAudits() {
        return new ResponseEntity<>(auditService.getAllAudits(), HttpStatus.OK);
    }
    /**
     * Метод, возвращающий запись аудита по ее id
     * @param id id аудита
     * @return список записей аудита
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuditEntity> getAuditById(@PathVariable Long id) {
        return new ResponseEntity<>(auditService.getAuditById(id), HttpStatus.OK);
    }
    /**
     * Метод, возвращающий список записей аудита неуспешных транзакций
     * @return список записей аудита
     */
    @GetMapping("/failed")
    public ResponseEntity<List<AuditEntity>> getFailedTransfers() {
        return new ResponseEntity<>(auditService.getAuditsByType(AuditOperationType.FAILED_TRANSFER), HttpStatus.OK);
    }
    /**
     * Метод, возвращающий список записей аудита успешных транзакций
     * @return список записей аудита
     */
    @GetMapping("/success")
    public ResponseEntity<List<AuditEntity>> getSuccessTransfers() {
        return new ResponseEntity<>(auditService.getAuditsByType(AuditOperationType.SUCCESS_TRANSFER), HttpStatus.OK);
    }
}
