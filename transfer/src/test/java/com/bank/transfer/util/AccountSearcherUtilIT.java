package com.bank.transfer.util;

import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.ServiceNotAvailableException;
import com.bank.transfer.pojo.Account;
import com.bank.transfer.pojo.Profile;
import com.bank.transfer.support.IntegrationTestBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountSearcherUtilIT extends IntegrationTestBase {
    @RegisterExtension
    static WireMockExtension ACCOUNT_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8085))
            .build();
    @RegisterExtension
    static WireMockExtension PROFILE_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8089))
            .build();
    @Autowired
    private AccountSearcherUtil searcherUtil;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAccountById() throws JsonProcessingException {
        Long accountId = 1L;

        Account expectedResult = Account.builder()
                .id(accountId)
                .passportId(1L)
                .accountNumber(123456789L)
                .bankDetailsId(1L)
                .money(100.0)
                .negativeBalance(false)
                .profileId(100L)
                .build();

        ACCOUNT_SERVICE.stubFor(get("/api/account/" + accountId)
                .willReturn(okJson(mapper.writeValueAsString(expectedResult))));

        Account actualResult = searcherUtil.getAccountById(1L);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void catchAccountNotFoundById() {
        Long accountId = 1L;

        ACCOUNT_SERVICE.stubFor(get("/api/account/" + accountId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        assertThrows(AccountNotFoundException.class,
                () -> searcherUtil.getAccountById(accountId));
    }

    @Test
    void getAccountByAccountNumber() throws JsonProcessingException {
        Long accountNumber = 12345L;

        Account expectedResult = Account.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(accountNumber)
                .bankDetailsId(1L)
                .money(100.0)
                .negativeBalance(false)
                .profileId(100L)
                .build();
        ACCOUNT_SERVICE.stubFor(get("/api/account?account_number=" + accountNumber)
                .willReturn(okJson(mapper.writeValueAsString(expectedResult))));

        Account actualResult = searcherUtil.getAccountByAccountNumber(accountNumber);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void catchAccountNotFoundByAccountNumber() {
        Long accountNumber = 12345L;

        ACCOUNT_SERVICE.stubFor(get("/api/account?account_number=" + accountNumber)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));


        assertThrows(AccountNotFoundException.class,
                () -> searcherUtil.getAccountByAccountNumber(accountNumber));
    }

    @Test
    void getAccountByCardNumber() {

    }

    @Test
    void getAccountByPhoneNumber() throws JsonProcessingException {
        Long phoneNumber = 8_800_555_35_35L;
        Long profileId = 100L;

        Profile profile = Profile.builder()
                .id(profileId)
                .phoneNumber(phoneNumber)
                .build();
        PROFILE_SERVICE.stubFor(get("/api/profile?phone_number=" + phoneNumber)
                .willReturn(okJson(mapper.writeValueAsString(profile))));

        Account expectedResult = Account.builder()
                .id(1L)
                .passportId(1L)
                .accountNumber(123456789L)
                .bankDetailsId(1L)
                .money(100.0)
                .negativeBalance(false)
                .profileId(profileId)
                .build();
        ACCOUNT_SERVICE.stubFor(get("/api/account?profile_id=" + profileId)
                .willReturn(okJson(mapper.writeValueAsString(expectedResult))));

        Account actualResult = searcherUtil.getAccountByPhoneNumber(phoneNumber);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
    @Test
    void catchAccountNotFoundByPhoneNumber() {
        Long phoneNumber = 8_800_555_35_35L;

        PROFILE_SERVICE.stubFor(get("/api/profile?phone_number=" + phoneNumber)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        assertThrows(AccountNotFoundException.class,
                () -> searcherUtil.getAccountByPhoneNumber(phoneNumber));
    }
    @Test
    void catchProfileInternalServerException() {
        Long phoneNumber = 8_800_555_35_35L;

        PROFILE_SERVICE.stubFor(get("/api/profile?phone_number=" + phoneNumber)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));
        assertThrows(ServiceNotAvailableException.class,
                () -> searcherUtil.getAccountByPhoneNumber(phoneNumber));
    }
    @Test
    void catchAccountInternalServerException() {
        Long accountId = 1L;

        ACCOUNT_SERVICE.stubFor(get("/api/account/" + accountId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        assertThrows(ServiceNotAvailableException.class,
                () -> searcherUtil.getAccountById(accountId));
    }
}