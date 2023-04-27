package com.bank.authorization.controller;

import com.bank.authorization.config.SuccessUserHandler;
import com.bank.authorization.config.WebSecurityConfig;
import com.bank.authorization.dto.AuditDTO;
import com.bank.authorization.service.AuditService;
import com.bank.authorization.service.UserDetailsServiceImpl;
import com.bank.authorization.util.ErrBindingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static java.sql.Timestamp.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuditRestController.class)
class AuditRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @MockBean
    private SuccessUserHandler successUserHandler;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private AuditService mockAuditService;
    @MockBean
    private ErrBindingResult mockErrBindingResult;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuditDTO entityDto = new AuditDTO(0L, "entityType", "operationType",
            "createdBy", "modifiedBy",
            valueOf(LocalDateTime.of(2000, 1, 1, 12, 0, 0, 0)),
            valueOf(LocalDateTime.of(2000, 1, 1, 12, 0, 0, 0)),
            "newEntityJson", "entityJson");

    final List<AuditDTO> entityDtoList = List.of(entityDto);

    @Test
    void testGetAll() throws Exception {
        when(mockAuditService.getAll()).thenReturn(entityDtoList);

        final MockHttpServletResponse response = mockMvc.perform(get("/audit")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(entityDtoList));
    }

    @Test
    void testGetAllAuditServiceReturnsNoItems() throws Exception {
        when(mockAuditService.getAll()).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc.perform(get("/audit")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetById() throws Exception {
        when(mockAuditService.getById(0L)).thenReturn(entityDto);

        final MockHttpServletResponse response = mockMvc.perform(get("/audit/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(entityDto));
    }

    @Test
    void testAdd() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(post("/audit")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(entityDto)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Audit has been added");
        verify(mockAuditService).add(entityDto);
    }

    @Test
    void testAddThrowsAuditNotCreatedException() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(post("/audit")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content("errorContent").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testUpdate() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(put("/audit/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(entityDto)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Audit with id-0 has been updated");
        verify(mockAuditService).update(entityDto, 0L);
    }

    @Test
    void testUpdateThrowsAuditNotCreatedException() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(put("/audit/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testDelete() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(delete("/audit/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Audit with id-0 has been deleted");
        verify(mockAuditService).delete(0L);
    }
}
