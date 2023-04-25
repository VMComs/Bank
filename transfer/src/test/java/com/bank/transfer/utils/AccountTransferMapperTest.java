package com.bank.transfer.utils;

import com.bank.transfer.dtos.AccountTransferDto;
import com.bank.transfer.entities.AccountTransfer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AccountTransferMapperTest {
    private final AccountTransferMapper mapper = Mappers.getMapper(AccountTransferMapper.class);

    @Test
    void convertToEntity() {
        AccountTransferDto transferDto = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("просто так")
                .accountNumber(1234L)
                .build();
        AccountTransfer actualResult = mapper.convertToEntity(transferDto);

        AccountTransfer expectedResult = AccountTransfer.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("просто так")
                .accountNumber(1234L)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void convertToDto() {
        AccountTransfer transfer = AccountTransfer.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("просто так")
                .accountNumber(1234L)
                .build();
        AccountTransferDto actualResult = mapper.convertToDto(transfer);

        AccountTransferDto expectedResult = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("просто так")
                .accountNumber(1234L)
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}