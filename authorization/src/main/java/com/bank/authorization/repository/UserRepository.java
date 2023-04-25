package com.bank.authorization.repository;

import com.bank.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA-репозиторий для сущности User
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByProfileId(long id);
}
