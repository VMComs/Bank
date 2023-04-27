package com.bank.authorization.controller;

import com.bank.authorization.dto.AuditDTO;
import com.bank.authorization.exception.AuditNotCreatedException;
import com.bank.authorization.service.AuditService;
import com.bank.authorization.util.ErrBindingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

/**
 * REST-Controller для сущности Audit
 * Все методы возвращают ResponseEntity
 * Методы Post и Put принимают AuditDTO
 * Методы Post и Put могут выбросить ошибку UserNotCreatedException(с сообщением, обработанным errBindingResult)
 * Метод GET вернет ошибку UserNotFoundException в случае несуществующей audit
 * Выброшенные ошибки обрабатываются ErrorHandler, и возвращают сообщение ошибки и HttpStatus
 * Методы:
 * GET - getAll()
 * GET - getUById(id)
 * POST - add(audit)
 * PUT - update(id, audit)
 * DELETE - delete(id)
 */

@Slf4j
@RestController
public class AuditRestController {

    private final AuditService auditService;
    private final ErrBindingResult errBindingResult;

    @Autowired
    public AuditRestController(AuditService auditService, ErrBindingResult errBindingResult) {
        this.auditService = auditService;
        this.errBindingResult = errBindingResult;
    }

    @GetMapping("/audit")
    public ResponseEntity<List<AuditDTO>> getAll() {
        log.info("Call getAll() method in controller {}", this.getClass());
        return ResponseEntity.ok(auditService.getAll());
    }

    @GetMapping("/audit/{id}")
    public ResponseEntity<AuditDTO> getById(@PathVariable("id") long id) {
        log.info("Call getById() method in controller {}, id = {}", this.getClass(), id);
        return ResponseEntity.ok(auditService.getById(id));
    }

    @PostMapping("/audit")
    public ResponseEntity<String> add(@RequestBody @Valid AuditDTO auditDTO,
                                      BindingResult bindingResult) throws AuditNotCreatedException {
        log.info("Call add() method in controller {}, AuditDTO = {}", this.getClass(), auditDTO);
        if (bindingResult.hasErrors()) {
            throw new AuditNotCreatedException(errBindingResult.getErrorsFromBindingResult(bindingResult));
        }
        auditService.add(auditDTO);
        return ResponseEntity.ok("Audit has been added");
    }

    @PutMapping("/audit/{id}")
    public  ResponseEntity<String> update(@PathVariable("id") long id,
                                          @RequestBody @Valid AuditDTO auditDTO,
                                          BindingResult bindingResult) throws AuditNotCreatedException {
        log.info("Call update() method in controller {}, id = {}, AuditDTO = {}", this.getClass(), id, auditDTO);
        if (bindingResult.hasErrors()) {
            throw new AuditNotCreatedException(errBindingResult.getErrorsFromBindingResult(bindingResult));
        }
        auditService.update(auditDTO, id);
        return ResponseEntity.ok(String.format("Audit with id-%s has been updated", id));
    }

    @DeleteMapping("/audit/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        log.info("Call delete() method in controller {}, id = {}", this.getClass(), id);
        auditService.delete(id);
        return ResponseEntity.ok(String.format("Audit with id-%s has been deleted", id));
    }
}
