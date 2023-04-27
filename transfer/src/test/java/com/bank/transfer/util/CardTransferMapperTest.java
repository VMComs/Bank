package com.bank.transfer.util;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransfer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CardTransferMapperTest {
    private final CardTransferMapper mapper = Mappers.getMapper(CardTransferMapper.class);

    @Test
    void convertToEntity() {
        CardTransferDto transferDto = CardTransferDto.builder()
                .accountDetailsId(10L)
                .amount(330.0)
                .purpose("тест")
                .cardNumber(1234567L)
                .build();
        CardTransfer actualResult = mapper.convertToEntity(transferDto);

        CardTransfer expectedResult = CardTransfer.builder()
                .accountDetailsId(10L)
                .amount(330.0)
                .purpose("тест")
                .cardNumber(1234567L)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void convertToDto() {
        CardTransfer transfer = CardTransfer.builder()
                .accountDetailsId(10L)
                .amount(330.0)
                .purpose("тест")
                .cardNumber(1234567L)
                .build();



        CardTransferDto actualResult = mapper.convertToDto(transfer);

        CardTransferDto expectedResult = CardTransferDto.builder()
                .accountDetailsId(10L)
                .amount(330.0)
                .purpose("тест")
                .cardNumber(1234567L)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}