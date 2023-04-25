package com.bank.transfer.repositories;

import com.bank.transfer.entities.CardTransfer;
import com.bank.transfer.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CardTransferRepositoryIT extends IntegrationTestBase {
    @Autowired
    private CardTransferRepository repository;

    private void insertTransfers() {
        CardTransfer transfer1 = CardTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .cardNumber(123456789L)
                .build();
        CardTransfer transfer2 = CardTransfer.builder()
                .id(2L)
                .accountDetailsId(1L)
                .amount(350.0)
                .purpose("test2")
                .cardNumber(123456789L)
                .build();
        CardTransfer transfer3 = CardTransfer.builder()
                .id(3L)
                .accountDetailsId(2L)
                .amount(100.0)
                .purpose("test3")
                .cardNumber(11111L)
                .build();
        repository.save(transfer1);
        repository.save(transfer2);
        repository.save(transfer3);
        repository.flush();
    }

    @Test
    void save() {
        CardTransfer expectedResult = CardTransfer.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .cardNumber(123456789L)
                .build();

        CardTransfer actualResult = repository.save(expectedResult);


        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void findById() {
        insertTransfers();
        CardTransfer expectedResult = CardTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .cardNumber(123456789L)
                .build();

        Optional<CardTransfer> actualResult = repository.findById(1L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(expectedResult.getId());
        assertThat(actualResult.get()).isEqualTo(expectedResult);
    }

    @Test
    void findAllByAccountDetailsId() {
        insertTransfers();

        List<CardTransfer> actualResult = repository.findAllByAccountDetailsId(1L);

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