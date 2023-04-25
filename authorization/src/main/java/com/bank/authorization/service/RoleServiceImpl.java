package com.bank.authorization.service;

import com.bank.authorization.entity.Role;
import com.bank.authorization.exception.RoleNotFoundException;
import com.bank.authorization.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Сервисный слой бизнес-логики сущности Role
 * Реализует методы интерфейса RoleService
 * Реализуется посредством JpaRepository класса RoleRepository
 * Методы:
 * getRoles() - возвращает список объектов Role
 * roleByID(id) - возвращает объект Role или бросает исключение RoleNotFoundException если в Optional - null
 * addRole(role) - добавляет объект Role в базу данных
 * updateRole(id, role) - обновляет объект Role в данных по id
 * deleteRole(id) - удаляет объект Role из базы данных по id
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) throws RoleNotFoundException {
        log.debug("Call getAll() method in service {}", this.getClass());
        final Optional<Role> role = roleRepository.findById(id);
        return role.orElseThrow(RoleNotFoundException::new);
    }

    @Transactional
    @Override
    public void add(Role role) {
        log.debug("Call add() method in service {}", this.getClass());
        roleRepository.saveAndFlush(role);
    }

    @Override
    public void update(long id, Role roleForUpdate) {
        log.debug("Call update() method in service {}", this.getClass());
        roleForUpdate.setId(id);
        roleRepository.saveAndFlush(roleForUpdate);
    }

    @Override
    public void delete(Long id) {
        log.debug("Call delete() method in service {}", this.getClass());
        roleRepository.deleteById(id);
    }
}
