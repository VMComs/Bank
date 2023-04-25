package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.service.LicenseService;
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
 * REST-контроллер RestLicenseController. Сущность - License (лицензия).
 * <b>ENTITY_CLASS_NAME</b> - имя класса сущности
 * <b>DTO_CLASS_NAME</b> - имя класса объекта передачи данных
 *
 * @see BasicRestController
 * @see License
 * @see LicenseDto
 * @see LicenseService
 * @see EntityDtoMapper
 */

@Slf4j
@RestController
@RequestMapping
public class RestLicenseController implements BasicRestController<LicenseDto> {

    private final LicenseService service;
    private final EntityDtoMapper<License, LicenseDto> mapper;

    public RestLicenseController(LicenseService service, EntityDtoMapper<License, LicenseDto> mapper) {
        this.service = service;
        mapper.setEntityClassName(License.class.getCanonicalName());
        mapper.setDtoClassName(LicenseDto.class.getCanonicalName());
        this.mapper = mapper;
    }

    @GetMapping(value = "/license/all")
    @Override
    public ResponseEntity<List<LicenseDto>> getList() {
        log.info("Вызов метода getList() в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDtoList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/license/id={id}")
    @Override
    public ResponseEntity<LicenseDto> getById(@PathVariable("id") Long id) {
        log.info("Вызов метода getById() |id = " + id + "| в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDto(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/license/new")
    @Override
    public ResponseEntity<LicenseDto> create(@RequestBody @Valid LicenseDto dto, BindingResult bindingResult) {
        log.info("Вызов метода create() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/admin/license/edit")
    @Override
    public ResponseEntity<LicenseDto> update(@RequestBody @Valid LicenseDto dto, BindingResult bindingResult) {
        log.debug("Вызов метода update() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/admin/license/id={id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.debug("Вызов метода deleteById() |id = " + id + "| в контроллере " + this.getClass());
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
