package com.bank.authorization.controllers;

import com.bank.authorization.entity.Role;
import com.bank.authorization.exception.RoleNotCreatedException;
import com.bank.authorization.service.RoleService;
import com.bank.authorization.util.ErrBindingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * REST-Controller для сущности Role
 * Все методы возвращают ResponseEntity
 * Методы Post и Put принимают сущность Role
 * Методы Post и Put могут выбросить ошибку RoleNotCreatedException(с сообщением, обработанным errBindingResult)
 * Метод GET вернет ошибку RoleNotFoundException в случае несуществующей role
 * Выброшенные ошибки обрабатываются ErrorHandler, и возвращают сообщение ошибки и HttpStatus
 * Методы:
 * GET - getAll()
 * GET - getById(id)
 * POST - add(role)
 * PUT - update(id, role)
 * DELETE - delete(id)
 */

@Slf4j
@RestController
public class RoleRestController {

    private final RoleService roleService;
    private final ErrBindingResult errBindingResult;
    @Autowired
    public RoleRestController(RoleService roleService, ErrBindingResult errBindingResult) {
        this.roleService = roleService;
        this.errBindingResult = errBindingResult;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAll() {
        log.info("Call getAll() method in controller {}", this.getClass());
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/roles/{id}")
    ResponseEntity<Role> getById(@PathVariable("id") long id) {
        log.info("Call getById() method in controller {}, id = {}", this.getClass(), id);
        return new ResponseEntity<>(roleService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<String> add(@RequestBody @Valid Role role,
                                              BindingResult bindingResult) throws RoleNotCreatedException {
        log.info("Call add() method in controller {}, Role = {}", this.getClass(), role);
        if (bindingResult.hasErrors()) {
            throw new RoleNotCreatedException(errBindingResult.getErrorsFromBindingResult(bindingResult));
        }
        roleService.add(role);
        return new ResponseEntity<>(String.format("Role %s has been added", role.toString()), HttpStatus.OK);
    }

    @PutMapping("/roles/{id}")
    public  ResponseEntity<String> update(@PathVariable("id") long id,
                                                @RequestBody @Valid Role role,
                                                BindingResult bindingResult) throws RoleNotCreatedException {
        log.info("Call update() method in controller {}, id = {}, Role = {}", this.getClass(), id, role);
        if (bindingResult.hasErrors()) {
            throw new RoleNotCreatedException(errBindingResult.getErrorsFromBindingResult(bindingResult));
        }
        roleService.update(id, role);
        return new ResponseEntity<>(String.format("Role with id-%s has been updated", id), HttpStatus.OK);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        log.info("Call delete() method in controller {}, id = {}", this.getClass(), id);
        roleService.delete(id);
        return new ResponseEntity<>(String.format("Role with id-%s has been deleted", id), HttpStatus.OK);
    }
}
