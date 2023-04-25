package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.service.AuditService;
import com.bank.publicinfo.service.EntityDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.bank.publicinfo.util.ErrorBindingResult.getBindingResultErrors;

/**
 * REST-контроллер RestAuditController. Сущность - Audit (аудит).
 * <b>ENTITY_CLASS_NAME</b> - имя класса сущности
 * <b>DTO_CLASS_NAME</b> - имя класса объекта передачи данных
 *
 * @see BasicRestController
 * @see Audit
 * @see AuditDto
 * @see AuditService
 * @see EntityDtoMapper
 */

@Slf4j
@RestController
@RequestMapping
public class RestAuditController implements BasicRestController<AuditDto> {

    private final AuditService service;
    private final EntityDtoMapper<Audit, AuditDto> mapper;

    public RestAuditController(AuditService service, EntityDtoMapper<Audit, AuditDto> mapper) {
        this.service = service;
        mapper.setEntityClassName(Audit.class.getCanonicalName());
        mapper.setDtoClassName(AuditDto.class.getCanonicalName());
        this.mapper = mapper;
    }

    @GetMapping(value = "/audit/all")
    @Override
    public ResponseEntity<List<AuditDto>> getList() {
        log.info("Вызов метода getList() в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDtoList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/audit/id={id}")
    @Override
    public ResponseEntity<AuditDto> getById(@PathVariable("id") Long id) {
        log.info("Вызов метода getById() |id = " + id + "| в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDto(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/audit/new")
    @Override
    public ResponseEntity<AuditDto> create(@RequestBody @Valid AuditDto dto, BindingResult bindingResult) {
        log.info("Вызов метода create() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/admin/audit/edit")
    @Override
    public ResponseEntity<AuditDto> update(@RequestBody @Valid AuditDto dto, BindingResult bindingResult) {
        log.info("Вызов метода update() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/admin/audit/id={id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.info("Вызов метода deleteById() |id = " + id + "| в контроллере " + this.getClass());
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
