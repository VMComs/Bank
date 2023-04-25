package com.bank.account.model.mapper;

import com.bank.account.model.dto.AccountDTO;
import com.bank.account.model.entity.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * В данном классе для сущности Account переопределяются методы абстрактного класса BaseMapper
 * для конвертации DTO -> Entity  и Entity -> DTO
 */

@Component
public class AccountMapper extends BaseMapper <Account, AccountDTO>{
    @Override
    public Account convertToEntity(AccountDTO dto, Object... args) {
        Account entity = new Account();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public AccountDTO convertToDto(Account entity, Object... args) {
        AccountDTO dto = new AccountDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
