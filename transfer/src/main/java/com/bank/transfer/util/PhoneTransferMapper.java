package com.bank.transfer.util;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransfer;
import org.mapstruct.Mapper;

/**
 * Интерфейс для маппинга и конвертации dto в entity
 */
@Mapper
public interface PhoneTransferMapper {
    /**
     * Метод конвертации Dto банковской транзакции по номеру телефона в entity
     * @param transferDto dto банковской транзакции по номеру телефона
     * @return entity
     */
    PhoneTransfer convertToEntity(PhoneTransferDto transferDto);
    /**
     * Метод конвертации entity банковской транзакции по номеру телефона в dto
     * @param transfer entity банковской транзакции по номеру телефона
     * @return dto
     */
    PhoneTransferDto convertToDto(PhoneTransfer transfer);
}
