package com.bank.transfer.controllers;

import com.bank.transfer.dtos.AccountTransferDto;
import com.bank.transfer.exceptions.AccountNotFoundException;
import com.bank.transfer.services.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountTransferController.class)
@AutoConfigureMockMvc
class AccountTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransferService<AccountTransferDto> transferService;


    @Test
    void doAccountTransfer() throws Exception {
        AccountTransferDto transfer = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .accountNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        when(transferService.doTransfer(transfer)).thenReturn(transfer);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isAccepted());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-10, 0, -3})
    void doAccountTransferWithInvalidAccountDetailsIdAndAccountNumber(Long args) throws Exception {
        AccountTransferDto invalidTransfer = AccountTransferDto.builder()
                .accountDetailsId(args)
                .accountNumber(args)
                .amount(100.0)
                .purpose("test")
                .build();

        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTransfer)))
                .andExpect(status().isBadRequest());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {-100, 0})
    void doAccountTransferWithInvalidAmount(Double args) throws Exception {
        AccountTransferDto invalidTransfer = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .accountNumber(2L)
                .amount(args)
                .purpose("test")
                .build();

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTransfer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAccountTransfers() throws Exception {
        AccountTransferDto transfer1 = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .accountNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        AccountTransferDto transfer2 = AccountTransferDto.builder()
                .accountDetailsId(2L)
                .accountNumber(8888L)
                .amount(300.0)
                .purpose("test2")
                .build();
        List<AccountTransferDto> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);

        when(transferService.getAllTransfers()).thenReturn(transfers);

        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[0].accountNumber", is(12345)))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].purpose", is("test1")))
                .andExpect(jsonPath("$[1].accountDetailsId", is(2)))
                .andExpect(jsonPath("$[1].accountNumber", is(8888)))
                .andExpect(jsonPath("$[1].amount", is(300.0)))
                .andExpect(jsonPath("$[1].purpose", is("test2")));
    }

    @Test
    void getAccountTransferById() throws Exception {
        AccountTransferDto transfer = AccountTransferDto.builder()
                .accountDetailsId(1L)
                .accountNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();


        when(transferService.getTransferById(1L)).thenReturn(transfer);

        mockMvc.perform(get("/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountDetailsId", is(1)))
                .andExpect(jsonPath("$.accountNumber", is(12345)))
                .andExpect(jsonPath("$.amount", is(100.0)))
                .andExpect(jsonPath("$.purpose", is("test1")));
    }

    @Test
    void catchAccountNotFoundException() throws Exception {
        when(transferService.getTransferById(any())).thenThrow(AccountNotFoundException.class);

        mockMvc.perform(get("/account/" + anyLong()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAccountTransfersBySenderId() throws Exception {
        Long senderId = 1L;
        AccountTransferDto transfer1 = AccountTransferDto.builder()
                .accountDetailsId(senderId)
                .accountNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        AccountTransferDto transfer2 = AccountTransferDto.builder()
                .accountDetailsId(senderId)
                .accountNumber(8888L)
                .amount(300.0)
                .purpose("test2")
                .build();
        List<AccountTransferDto> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);


        when(transferService.getTransfersBySenderId(senderId)).thenReturn(transfers);

        mockMvc.perform(get("/account?sender_id=" + senderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[0].accountNumber", is(12345)))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].purpose", is("test1")))
                .andExpect(jsonPath("$[1].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[1].accountNumber", is(8888)))
                .andExpect(jsonPath("$[1].amount", is(300.0)))
                .andExpect(jsonPath("$[1].purpose", is("test2")));
    }
}