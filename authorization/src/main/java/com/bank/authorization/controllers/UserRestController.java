package com.bank.authorization.controllers;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.exception.UserNotCreatedException;
import com.bank.authorization.feign.ProfileFeignClient;
import com.bank.authorization.pojos.Profile;
import com.bank.authorization.service.UserService;
import com.bank.authorization.util.ErrBindingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

/**
 * REST-Controller для сущности User
 * Все методы возвращают ResponseEntity
 * Методы Post и Put принимают UserDTO
 * Методы Post и Put могут выбросить ошибку UserNotCreatedException(с сообщением, обработанным errBindingResult)
 * Метод GET вернет ошибку UserNotFoundException в случае несуществующей role
 * Выброшенные ошибки обрабатываются ErrorHandler, и возвращают сообщение ошибки и HttpStatus
 * Методы:
 * GET - getAllUsers()
 * GET - getById(id)
 * GET - getProfileByUsername(username) - обращение к profile для получения объекта Profile по полю email
 * GET - getAuthUser(authentication) - возвращает объект Profile авторизованного пользователя
 * POST - add(user)
 * PUT - update(id, user)
 * DELETE - delete(id)
 */

@Slf4j
@RestController
public class UserRestController {

    private final UserService userService;
    private final ErrBindingResult errBindingResult;
    private final ProfileFeignClient profileFeignClient;

    @Autowired
    public UserRestController(UserService userService, ErrBindingResult errBindingResult,
                              ProfileFeignClient profileFeignClient) {
        this.userService = userService;
        this.errBindingResult = errBindingResult;
        this.profileFeignClient = profileFeignClient;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll() {
        log.info("Call getAll() method in controller {}", this.getClass());
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        log.info("Call getById() method in controller {}, id = {}", this.getClass(), id);
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public Profile getProfileByUsername(@RequestParam String email) {
        log.info("Call getProfileByUsername() method in controller {}, email = {}", this.getClass(), email);
        return profileFeignClient.getProfileByUsername(email).getBody();
    }

    @GetMapping("/userView")
    public ResponseEntity<Profile> getAuthUser(Authentication auth) {
        log.info("Call getAuthUser() method in controller {}, Authentication {}", this.getClass(), auth.getName());
        return ResponseEntity.ok(getProfileByUsername(auth.getName()));
    }

    @PostMapping("/users")
    public ResponseEntity<String> add(@RequestBody @Valid UserDTO userDTO,
                                              BindingResult bindingResult) throws UserNotCreatedException {
        log.info("Call add() method in controller {}, UserDTO = {}", this.getClass(), userDTO);
        if (bindingResult.hasErrors()) {
            throw new UserNotCreatedException(errBindingResult.getErrorsFromBindingResult(bindingResult));
        }
        userService.add(userDTO);
        return ResponseEntity.ok("User has been added");
    }

    @PutMapping("/users/{id}")
    public  ResponseEntity<String> update(@PathVariable("id") long id,
                                               @RequestBody @Valid UserDTO userDTO,
                                               BindingResult bindingResult) throws UserNotCreatedException {
        log.info("Call update() method in controller {}, id = {}, UserDTO = {}", this.getClass(), id, userDTO);
        if (bindingResult.hasErrors()) {
            throw new UserNotCreatedException(errBindingResult.getErrorsFromBindingResult(bindingResult));
        }
        userService.update(id, userDTO);
        return ResponseEntity.ok(String.format("User with id-%s has been updated", id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        log.info("Call delete() method in controller {}, id = {}", this.getClass(), id);
        userService.delete(id);
        return ResponseEntity.ok(String.format("User with id-%s has been deleted", id));
    }
}
