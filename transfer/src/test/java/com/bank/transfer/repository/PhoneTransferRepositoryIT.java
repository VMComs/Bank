package com.bank.transfer.repository;

import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class PhoneTransferRepositoryIT extends IntegrationTestBase {

    @Autowired
    private PhoneTransferRepository repository;

    private void insertTransfers() {
        PhoneTransfer transfer1 = PhoneTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .phoneNumber(123456789L)
                .build();
        PhoneTransfer transfer2 = PhoneTransfer.builder()
                .id(2L)
                .accountDetailsId(1L)
                .amount(350.0)
                .purpose("test2")
                .phoneNumber(123456789L)
                .build();
        PhoneTransfer transfer3 = PhoneTransfer.builder()
                .id(3L)
                .accountDetailsId(2L)
                .amount(100.0)
                .purpose("test3")
                .phoneNumber(11111L)
                .build();
        repository.save(transfer1);
        repository.save(transfer2);
        repository.save(transfer3);
        repository.flush();
    }

    @Test
    void save() {
        PhoneTransfer expectedResult = PhoneTransfer.builder()
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .phoneNumber(123456789L)
                .build();

        PhoneTransfer actualResult = repository.save(expectedResult);


        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getId()).isNotNull();
    }

    @Test
    void findById() {
        insertTransfers();
        PhoneTransfer expectedResult = PhoneTransfer.builder()
                .id(1L)
                .accountDetailsId(1L)
                .amount(300.0)
                .purpose("test1")
                .phoneNumber(123456789L)
                .build();

        Optional<PhoneTransfer> actualResult = repository.findById(1L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(expectedResult.getId());
        assertThat(actualResult.get()).isEqualTo(expectedResult);
    }

    @Test
    void findAllByAccountDetailsId() {
        insertTransfers();

        List<PhoneTransfer> actualResult = repository.findAllByAccountDetailsId(1L);

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