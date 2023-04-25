package com.bank.authorization.service;

import com.bank.authorization.entity.Role;
import java.util.List;

/**
 * Сервисный интерфейс бизнес-логики сущности Role
 * Реализация в классе RoleServiceImpl
 */

public interface RoleService {
    List<Role> getAll();
    void add(Role role);
    Role getById(Long id);
    void delete(Long id);
    void update(long id, Role roleForUpdate);
}
