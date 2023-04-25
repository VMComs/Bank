package com.bank.transfer.utils;

import com.bank.transfer.dtos.PhoneTransferDto;
import com.bank.transfer.entities.PhoneTransfer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PhoneTransferMapperTest {
    private final PhoneTransferMapper mapper = Mappers.getMapper(PhoneTransferMapper.class);


    @Test
    void convertToEntity() {
        PhoneTransferDto transferDto = PhoneTransferDto.builder()
                .accountDetailsId(100L)
                .amount(600.0)
                .purpose("тест2")
                .phoneNumber(1234L)
                .build();

        PhoneTransfer actualResult = mapper.convertToEntity(transferDto);

        PhoneTransfer expectedResult = PhoneTransfer.builder()
                .accountDetailsId(100L)
                .amount(600.0)
                .purpose("тест2")
                .phoneNumber(1234L)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void convertToDto() {
        PhoneTransfer transfer = PhoneTransfer.builder()
                .accountDetailsId(100L)
                .amount(600.0)
                .purpose("тест2")
                .phoneNumber(1234L)
                .build();


        PhoneTransferDto actualResult = mapper.convertToDto(transfer);

        PhoneTransferDto expectedResult = PhoneTransferDto.builder()
                .accountDetailsId(100L)
                .amount(600.0)
                .purpose("тест2")
                .phoneNumber(1234L)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}