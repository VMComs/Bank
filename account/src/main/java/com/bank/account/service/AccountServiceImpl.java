package com.bank.account.service;

import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.model.dto.AccountDTO;
import com.bank.account.model.entity.Account;
import com.bank.account.model.mapper.AccountMapper;
import com.bank.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Класс имлементирующий интерфейс, содержащий методы CRUD-операций для сущности Account.
 */

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }


    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getByID(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()
                -> new AccountNotFoundException("Account with id " + id  + "not found"));
        return accountMapper.convertToDto(account);
    }

    @Override
    @Transactional
    public void save(AccountDTO  accountDTO) {
        /* добавить метод валидации полей, которые должны совпадать с данными из БД других микросервисов,
           полученных через FeignClient
         */
        Account account = accountMapper.convertToEntity(accountDTO);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void update(AccountDTO accountDTO, Long id) {
        /* добавить метод валидации полей, которые должны совпадать с данными из БД других микросервисов,
           полученных через FeignClient
         */
        Account account = accountMapper.convertToEntity(accountDTO);
        account.setId(id);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        accountRepository.delete(getByID(id));
    }

}
