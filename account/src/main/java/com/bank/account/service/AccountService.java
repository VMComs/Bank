package com.bank.account.service;

import com.bank.account.model.dto.AccountDTO;
import com.bank.account.model.entity.Account;

import java.util.List;

/**
 * Интерфейс, содержащий методы CRUD-операций для сущности Audit.
 */
public interface AccountService {
    List<Account> getAll();
    Account getByID(Long id);
    void save (AccountDTO accountDTO);
    void update (AccountDTO accountDTO, Long id);
    void delete (Long id);
}
