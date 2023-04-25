package com.bank.account.controller;

import com.bank.account.model.dto.AuditDTO;
import com.bank.account.model.entity.Audit;
import com.bank.account.service.AuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс Контроллеров, взаимодейтсвующих с пользователем по API.
 */

@RestController("/audit_for_account")
public class AuditControllerRest {
    private final AuditService auditService;

    public AuditControllerRest(AuditService auditService) {
        this.auditService = auditService;
    }

    // Метод реализован для проверки через Postman
    @GetMapping("/audits")
    public ResponseEntity<List<Audit>> getAllAudit() {
        List <Audit> audits = auditService.getAll();
        return new ResponseEntity<>(audits, HttpStatus.OK);
    }

    @GetMapping("/audit/{id}")
    public ResponseEntity <Audit> getByIdAudit(@PathVariable Long id){
        return new ResponseEntity <> (auditService.getByID(id), HttpStatus.OK);
    }

    @PostMapping("/newAudit")
    public ResponseEntity <Audit> createAudit (@RequestBody AuditDTO auditDTO) {
        auditService.save(auditDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity <Audit> editAudit (@RequestBody AuditDTO auditDTO, @PathVariable Long id) {
        auditService.update(auditDTO, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Audit> deleteAudit (@PathVariable Long id){
        auditService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
