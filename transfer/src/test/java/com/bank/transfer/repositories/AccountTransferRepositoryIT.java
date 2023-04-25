package com.bank.transfer.repositories;

import com.bank.transfer.entities.AccountTransfer;
import com.bank.transfer.support.IntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class AccountTransferRepositoryIT extends IntegrationTestBase {
    @Autowired
    private AccountTransferRepository repository;
    private void insertTransfers() {
        AccountTransfer transfer1 = AccountTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .accountNumber(123456789L)
                .build();
        AccountTransfer transfer2 = AccountTransfer.builder()
                .id(2L)
                .accountDetailsId(1L)
                .amount(350.0)
                .purpose("test2")
                .accountNumber(123456789L)
                .build();
        AccountTransfer transfer3 = AccountTransfer.builder()
                .id(3L)
                .accountDetailsId(2L)
                .amount(100.0)
                .purpose("test3")
                .accountNumber(11111L)
                .build();
        repository.save(transfer1);
        repository.save(transfer2);
        repository.save(transfer3);
        repository.flush();
    }
    @Test
    void save() {
        AccountTransfer expectedResult = AccountTransfer.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .accountNumber(123456789L)
                .build();

        AccountTransfer actualResult = repository.save(expectedResult);

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void findById() {
        insertTransfers();
        AccountTransfer expectedResult = AccountTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .accountNumber(123456789L)
                .build();

        Optional<AccountTransfer> actualResult = repository.findById(1L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(expectedResult.getId());
        assertThat(actualResult.get()).isEqualTo(expectedResult);
    }

    @Test
    void findAllByAccountDetailsId() {
        insertTransfers();

        List<AccountTransfer> actualResult = repository.findAllByAccountDetailsId(1L);

        assertThat(actualResult.size()).isEqualTo(2);
    }

    @Test
    void existsByAccountDetailsId() {
        insertTransfers();

        boolean actualResult1 = repository.existsByAccountDetailsId(1L);
        boolean actualResult2 = repository.existsByAccountDetailsId(10L);

        assertThat(actualResult1).isTrue();
        assertThat(actualResult2).isFalse();
    }
}