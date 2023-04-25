package com.bank.authorization.repository;

import com.bank.authorization.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA-репозиторий для сущности Role
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>  {
}
