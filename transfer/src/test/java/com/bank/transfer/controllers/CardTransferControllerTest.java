package com.bank.transfer.controllers;

import com.bank.transfer.dtos.CardTransferDto;
import com.bank.transfer.services.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardTransferController.class)
@AutoConfigureMockMvc
class CardTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransferService<CardTransferDto> transferService;


    @Test
    void doCardTransfer() throws Exception {
        CardTransferDto transfer = CardTransferDto.builder()
                .accountDetailsId(1L)
                .cardNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        when(transferService.doTransfer(transfer)).thenReturn(transfer);

        mockMvc.perform(post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isAccepted());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-10, 0, -3})
    void doCardTransferWithInvalidAccountDetailsIdAndCardNumber(Long args) throws Exception {
        CardTransferDto invalidTransfer = CardTransferDto.builder()
                .accountDetailsId(args)
                .cardNumber(args)
                .amount(100.0)
                .purpose("test")
                .build();

        mockMvc.perform(post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTransfer)))
                .andExpect(status().isBadRequest());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {-100, 0})
    void doCardTransferWithInvalidAmount(Double args) throws Exception {
        CardTransferDto invalidTransfer = CardTransferDto.builder()
                .accountDetailsId(1L)
                .cardNumber(2L)
                .amount(args)
                .purpose("test")
                .build();

        mockMvc.perform(post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTransfer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCardTransfers() throws Exception {
        CardTransferDto transfer1 = CardTransferDto.builder()
                .accountDetailsId(1L)
                .cardNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        CardTransferDto transfer2 = CardTransferDto.builder()
                .accountDetailsId(2L)
                .cardNumber(8888L)
                .amount(300.0)
                .purpose("test2")
                .build();
        List<CardTransferDto> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);

        when(transferService.getAllTransfers()).thenReturn(transfers);

        mockMvc.perform(get("/card"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[0].cardNumber", is(12345)))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].purpose", is("test1")))
                .andExpect(jsonPath("$[1].accountDetailsId", is(2)))
                .andExpect(jsonPath("$[1].cardNumber", is(8888)))
                .andExpect(jsonPath("$[1].amount", is(300.0)))
                .andExpect(jsonPath("$[1].purpose", is("test2")));
    }

    @Test
    void getCardTransferById() throws Exception {
        CardTransferDto transfer = CardTransferDto.builder()
                .accountDetailsId(1L)
                .cardNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();


        when(transferService.getTransferById(1L)).thenReturn(transfer);

        mockMvc.perform(get("/card/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountDetailsId", is(1)))
                .andExpect(jsonPath("$.cardNumber", is(12345)))
                .andExpect(jsonPath("$.amount", is(100.0)))
                .andExpect(jsonPath("$.purpose", is("test1")));
    }

    @Test
    void getCardTransfersBySenderId() throws Exception {
        Long senderId = 1L;
        CardTransferDto transfer1 = CardTransferDto.builder()
                .accountDetailsId(senderId)
                .cardNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        CardTransferDto transfer2 = CardTransferDto.builder()
                .accountDetailsId(senderId)
                .cardNumber(8888L)
                .amount(300.0)
                .purpose("test2")
                .build();
        List<CardTransferDto> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);


        when(transferService.getTransfersBySenderId(senderId)).thenReturn(transfers);

        mockMvc.perform(get("/card?sender_id=" + senderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[0].cardNumber", is(12345)))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].purpose", is("test1")))
                .andExpect(jsonPath("$[1].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[1].cardNumber", is(8888)))
                .andExpect(jsonPath("$[1].amount", is(300.0)))
                .andExpect(jsonPath("$[1].purpose", is("test2")));
    }
}