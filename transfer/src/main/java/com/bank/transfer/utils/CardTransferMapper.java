package com.bank.transfer.utils;

import com.bank.transfer.dtos.CardTransferDto;
import com.bank.transfer.entities.CardTransfer;
import org.mapstruct.Mapper;

/**
 * Интерфейс для маппинга и конвертации dto в entity
 */
@Mapper
public interface CardTransferMapper {
    /**
     * Метод конвертации Dto банковской транзакции по номеру карты в entity
     * @param transferDto dto банковской транзакции по номеру карты
     * @return entity
     */
    CardTransfer convertToEntity(CardTransferDto transferDto);
    /**
     * Метод конвертации entity банковской транзакции по номеру карты в dto
     * @param transfer entity банковской транзакции по номеру карты
     * @return dto
     */
    CardTransferDto convertToDto(CardTransfer transfer);
}
