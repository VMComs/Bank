package com.bank.transfer.util;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransfer;
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
