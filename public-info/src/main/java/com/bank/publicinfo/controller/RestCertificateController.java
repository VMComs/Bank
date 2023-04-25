package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.service.CertificateService;
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
 * REST-контроллер RestCertificateController. Сущность - Certificate (сертификат).
 * <b>ENTITY_CLASS_NAME</b> - имя класса сущности
 * <b>DTO_CLASS_NAME</b> - имя класса объекта передачи данных
 *
 * @see BasicRestController
 * @see Certificate
 * @see CertificateDto
 * @see CertificateService
 * @see EntityDtoMapper
 */

@Slf4j
@RestController
@RequestMapping
public class RestCertificateController implements BasicRestController<CertificateDto> {

    private final CertificateService service;
    private final EntityDtoMapper<Certificate, CertificateDto> mapper;

    public RestCertificateController(CertificateService service, EntityDtoMapper<Certificate, CertificateDto> mapper) {
        this.service = service;
        mapper.setEntityClassName(Certificate.class.getCanonicalName());
        mapper.setDtoClassName(CertificateDto.class.getCanonicalName());
        this.mapper = mapper;
    }

    @GetMapping(value = "/certificate/all")
    @Override
    public ResponseEntity<List<CertificateDto>> getList() {
        log.info("Вызов метода getList() в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDtoList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/certificate/id={id}")
    @Override
    public ResponseEntity<CertificateDto> getById(@PathVariable("id") Long id) {
        log.info("Вызов метода getById() |id = " + id + "| в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDto(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/certificate/new")
    @Override
    public ResponseEntity<CertificateDto> create(@RequestBody @Valid CertificateDto dto, BindingResult bindingResult) {
        log.info("Вызов метода create() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/admin/certificate/edit")
    @Override
    public ResponseEntity<CertificateDto> update(@RequestBody @Valid CertificateDto dto, BindingResult bindingResult) {
        log.info("Вызов метода update() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/admin/certificate/id={id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.info("Вызов метода deleteById() |id = " + id + "| в контроллере " + this.getClass());
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
