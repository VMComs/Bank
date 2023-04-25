package com.bank.transfer.utils;

import com.bank.transfer.dtos.AccountTransferDto;
import com.bank.transfer.entities.AccountTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для маппинга и конвертации dto в entity
 */
@Mapper
public interface AccountTransferMapper {
    AccountTransferMapper INSTANCE = Mappers.getMapper(AccountTransferMapper.class);
    /**
     * Метод конвертации Dto банковской транзакции по номеру счета в entity
     * @param transferDto dto банковской транзакции по номеру счета
     * @return entity
     */
    AccountTransfer convertToEntity(AccountTransferDto transferDto);
    /**
     * Метод конвертации entity банковской транзакции по номеру счета в dto
     * @param transfer entity банковской транзакции по номеру счета
     * @return dto
     */
    AccountTransferDto convertToDto(AccountTransfer transfer);
}
