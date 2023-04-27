package com.bank.transfer.controller;

import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.entity.AuditOperationType;
import com.bank.transfer.entity.CardTransfer;
import com.bank.transfer.entity.PhoneTransfer;
import com.bank.transfer.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuditController.class)
@AutoConfigureMockMvc
class AuditControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuditService auditService;
    private List<AuditEntity> audits() {
        AuditEntity audit1 = AuditEntity.builder()
                .id(1L)
                .entityType(AccountTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 100")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        AuditEntity audit2 = AuditEntity.builder()
                .id(2L)
                .entityType(CardTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 140")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        AuditEntity audit3 = AuditEntity.builder()
                .id(3L)
                .entityType(PhoneTransfer.class.getSimpleName())
                .operationType(AuditOperationType.FAILED_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 42")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        List<AuditEntity> audits = new ArrayList<>();
        audits.add(audit1);
        audits.add(audit2);
        audits.add(audit3);
        return audits;
    }

    @Test
    void getAudits() throws Exception {
        when(auditService.getAllAudits()).thenReturn(audits());

        mockMvc.perform(get("/audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(audits())))
                .andExpect(status().isOk());
    }

    @Test
    void getAuditById() throws Exception {
        AuditEntity audit = AuditEntity.builder()
                .id(1L)
                .entityType(AccountTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 100")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        when(auditService.getAuditById(1L)).thenReturn(audit);

        mockMvc.perform(get("/audit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entityType", is("AccountTransfer")))
                .andExpect(jsonPath("$.operationType", is("SUCCESS_TRANSFER")))
                .andExpect(jsonPath("$.createdBy", is("Account with id 100")))
                .andExpect(jsonPath("$.entityJson",
                        is("здесь должен быть json, но мне лень его писать")));
    }

    @Test
    void getFailedTransfers() throws Exception {
        AuditEntity audit3 = AuditEntity.builder()
                .id(3L)
                .entityType(PhoneTransfer.class.getSimpleName())
                .operationType(AuditOperationType.FAILED_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 42")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        List<AuditEntity> list = new ArrayList<>();
        list.add(audit3);

        when(auditService.getAuditsByType(AuditOperationType.FAILED_TRANSFER)).thenReturn(list);

        mockMvc.perform(get("/audit/failed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].operationType", is("FAILED_TRANSFER")));
    }

    @Test
    void getSuccessTransfers() throws Exception {
        AuditEntity audit1 = AuditEntity.builder()
                .id(1L)
                .entityType(AccountTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 100")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        AuditEntity audit2 = AuditEntity.builder()
                .id(2L)
                .entityType(CardTransfer.class.getSimpleName())
                .operationType(AuditOperationType.SUCCESS_TRANSFER)
                .createdAt(new Date())
                .createdBy("Account with id 140")
                .entityJson("здесь должен быть json, но мне лень его писать")
                .build();
        List<AuditEntity> list = new ArrayList<>();
        list.add(audit1);
        list.add(audit2);

        when(auditService.getAuditsByType(AuditOperationType.SUCCESS_TRANSFER)).thenReturn(list);

        mockMvc.perform(get("/audit/success"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].operationType", is("SUCCESS_TRANSFER")))
                .andExpect(jsonPath("$[1].operationType", is("SUCCESS_TRANSFER")));

    }
}