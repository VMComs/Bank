package com.bank.transfer.controller;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.service.TransferService;
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

@WebMvcTest(PhoneTransferController.class)
@AutoConfigureMockMvc
class PhoneTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransferService<PhoneTransferDto> transferService;

    @Test
    void doPhoneTransfer() throws Exception {
        PhoneTransferDto transfer = PhoneTransferDto.builder()
                .accountDetailsId(1L)
                .phoneNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        when(transferService.doTransfer(transfer)).thenReturn(transfer);

        mockMvc.perform(post("/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isAccepted());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-10, 0, -3})
    void doPhoneTransferWithInvalidAccountDetailsIdAndPhoneNumber(Long args) throws Exception {
        PhoneTransferDto invalidTransfer = PhoneTransferDto.builder()
                .accountDetailsId(args)
                .phoneNumber(args)
                .amount(100.0)
                .purpose("test")
                .build();

        mockMvc.perform(post("/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTransfer)))
                .andExpect(status().isBadRequest());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {-100, 0})
    void doPhoneTransferWithInvalidAmount(Double args) throws Exception {
        PhoneTransferDto invalidTransfer = PhoneTransferDto.builder()
                .accountDetailsId(1L)
                .phoneNumber(2L)
                .amount(args)
                .purpose("test")
                .build();

        mockMvc.perform(post("/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTransfer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPhoneTransfers() throws Exception {
        PhoneTransferDto transfer1 = PhoneTransferDto.builder()
                .accountDetailsId(1L)
                .phoneNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        PhoneTransferDto transfer2 = PhoneTransferDto.builder()
                .accountDetailsId(2L)
                .phoneNumber(8888L)
                .amount(300.0)
                .purpose("test2")
                .build();
        List<PhoneTransferDto> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);

        when(transferService.getAllTransfers()).thenReturn(transfers);

        mockMvc.perform(get("/phone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[0].phoneNumber", is(12345)))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].purpose", is("test1")))
                .andExpect(jsonPath("$[1].accountDetailsId", is(2)))
                .andExpect(jsonPath("$[1].phoneNumber", is(8888)))
                .andExpect(jsonPath("$[1].amount", is(300.0)))
                .andExpect(jsonPath("$[1].purpose", is("test2")));
    }

    @Test
    void getPhoneTransferById() throws Exception {
        PhoneTransferDto transfer = PhoneTransferDto.builder()
                .accountDetailsId(1L)
                .phoneNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();


        when(transferService.getTransferById(1L)).thenReturn(transfer);

        mockMvc.perform(get("/phone/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountDetailsId", is(1)))
                .andExpect(jsonPath("$.phoneNumber", is(12345)))
                .andExpect(jsonPath("$.amount", is(100.0)))
                .andExpect(jsonPath("$.purpose", is("test1")));
    }

    @Test
    void getCardTransfersBySenderId() throws Exception {
        Long senderId = 1L;

        PhoneTransferDto transfer1 = PhoneTransferDto.builder()
                .accountDetailsId(senderId)
                .phoneNumber(12345L)
                .amount(100.0)
                .purpose("test1")
                .build();
        PhoneTransferDto transfer2 = PhoneTransferDto.builder()
                .accountDetailsId(senderId)
                .phoneNumber(8888L)
                .amount(300.0)
                .purpose("test2")
                .build();
        List<PhoneTransferDto> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);


        when(transferService.getTransfersBySenderId(1L)).thenReturn(transfers);

        mockMvc.perform(get("/phone?sender_id=" + senderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[0].phoneNumber", is(12345)))
                .andExpect(jsonPath("$[0].amount", is(100.0)))
                .andExpect(jsonPath("$[0].purpose", is("test1")))
                .andExpect(jsonPath("$[1].accountDetailsId", is(1)))
                .andExpect(jsonPath("$[1].phoneNumber", is(8888)))
                .andExpect(jsonPath("$[1].amount", is(300.0)))
                .andExpect(jsonPath("$[1].purpose", is("test2")));
    }
}