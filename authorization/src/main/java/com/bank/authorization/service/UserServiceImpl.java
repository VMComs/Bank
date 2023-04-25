package com.bank.authorization.service;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.User;
import com.bank.authorization.exception.UserNotFoundException;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой бизнес-логики сущности User
 * Реализует методы интерфейса UserService
 * Реализуется посредством JpaRepository класса UserRepository
 * Методы:
 * getAll() - возвращает список объектов UserDto
 * getById(id) - возвращает объект UserDto или бросает исключение RoleNotFoundException если в Optional - null
 * getByProfileId - возвращает объект UserDto по полю profileId, нужно для реализации авторизации
 * add(user) - добавляет объект User в базу данных, шифрует поле password посредством BCryptPasswordEncoder
 * update(id, user) - обновляет объект User в данных по id, если пароль был изменен - повторно шифрует поле
 * delete(id) - удаляет объект User из базы данных по id
 */

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAll() {
        log.debug("Call getAll() method in service {}", this.getClass());
        return userMapper.toDTOList(userRepository.findAll());
    }

    @Override
    public UserDTO getById(long id) {
        log.debug("Call getById() method in service {}", this.getClass());
        final Optional<User> userById = userRepository.findById(id);
        return userMapper.toDTO(userById.orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDTO getByProfileId(long id) {
        log.debug("Call getByProfileId() method in service {}", this.getClass());
        final Optional<User> userById = userRepository.getUserByProfileId(id);
        return userMapper.toDTO(userById.orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    @Override
    public void add(UserDTO newUser) {
        log.debug("Call add() method in service {}", this.getClass());
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        userRepository.saveAndFlush(userMapper.toUser(newUser));
    }

    @Transactional
    @Override
    public void update(long id, UserDTO userForUpdate) {
        log.debug("Call update() method in service {}", this.getClass());
        if (!getById(id).getPassword().equals(userForUpdate.getPassword())) {
            userForUpdate.setPassword(new BCryptPasswordEncoder().encode(userForUpdate.getPassword()));
        }
        userForUpdate.setId(id);
        userRepository.saveAndFlush(userMapper.toUser(userForUpdate));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.debug("Call delete() method in service {}", this.getClass());
        userRepository.deleteById(id);
    }
}
