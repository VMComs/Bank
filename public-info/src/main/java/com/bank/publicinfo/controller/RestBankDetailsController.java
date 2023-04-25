package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.service.BankDetailsService;
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
 * REST-контроллер RestBankDetailsController. Сущность - BankDetails (банковские реквизиты).
 * <b>ENTITY_CLASS_NAME</b> - имя класса сущности
 * <b>DTO_CLASS_NAME</b> - имя класса объекта передачи данных
 *
 * @see BasicRestController
 * @see BankDetails
 * @see BankDetailsDto
 * @see BankDetailsService
 * @see EntityDtoMapper
 */

@Slf4j
@RestController
@RequestMapping
public class RestBankDetailsController implements BasicRestController<BankDetailsDto> {

    private final BankDetailsService service;
    private final EntityDtoMapper<BankDetails, BankDetailsDto> mapper;

    public RestBankDetailsController(BankDetailsService service, EntityDtoMapper<BankDetails, BankDetailsDto> mapper) {
        this.service = service;
        mapper.setEntityClassName(BankDetails.class.getCanonicalName());
        mapper.setDtoClassName(BankDetailsDto.class.getCanonicalName());
        this.mapper = mapper;
    }

    @GetMapping(value = "/bank-details/all")
    @Override
    public ResponseEntity<List<BankDetailsDto>> getList() {
        log.info("Вызов метода getList() в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDtoList(service.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/bank-details/id={id}")
    @Override
    public ResponseEntity<BankDetailsDto> getById(@PathVariable("id") Long id) {
        log.info("Вызов метода getById() |id = " + id + "| в контроллере " + this.getClass());
        return new ResponseEntity<>(mapper.toDto(service.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/bank-details/new")
    @Override
    public ResponseEntity<BankDetailsDto> create(@RequestBody @Valid BankDetailsDto dto, BindingResult bindingResult) {
        log.info("Вызов метода create() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/admin/bank-details/edit")
    @Override
    public ResponseEntity<BankDetailsDto> update(@RequestBody @Valid BankDetailsDto dto, BindingResult bindingResult) {
        log.info("Вызов метода update() |DTO = " + dto + "| в контроллере " + this.getClass());
        if (bindingResult.hasErrors()) {
            throw new NotExecutedException(getBindingResultErrors(bindingResult));
        }
        service.save(mapper.toEntity(dto));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/admin/bank-details/id={id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        log.info("Вызов метода deleteById() |id = " + id + "| в контроллере " + this.getClass());
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
