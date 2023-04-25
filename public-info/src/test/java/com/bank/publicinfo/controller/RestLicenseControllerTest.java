package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.service.LicenseService;
import com.bank.publicinfo.service.EntityDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestLicenseController.class)
class RestLicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService mockService;

    @MockBean
    private EntityDtoMapper<License, LicenseDto> mockMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final BankDetails BANK_DETAILS = new BankDetails(144_525_974L, 1_000_000_000L, 1_000_000_000L,
            11_810_600_000_000_957L, "city", "jointStock", "name");

    private final License ENTITY = new License(new Byte[]{(byte) 0b0}, BANK_DETAILS);

    private final LicenseDto DTO = new LicenseDto(new Byte[]{(byte) 0b0}, BANK_DETAILS);

    private final LicenseDto INVALID_DTO = new LicenseDto(new Byte[]{}, BANK_DETAILS);

    {
        ENTITY.setId(1L);
        DTO.setId(1L);
    }

    @Test
    @DisplayName("Возвращение списка DTO")
    void testGetList() throws Exception {

        final List<License> entityList = List.of(ENTITY);
        final List<LicenseDto> dtoList = List.of(DTO);

        when(mockService.findAll()).thenReturn(entityList);
        when(mockMapper.toDtoList(entityList)).thenReturn(dtoList);

        final MockHttpServletResponse response = mockMvc
                .perform(get("/license/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(dtoList));
    }

    @Test
    @DisplayName("Возвращение пустого списка DTO, если список Entity пуст")
    void testGetEmptyList() throws Exception {

        when(mockService.findAll()).thenReturn(Collections.emptyList());
        when(mockMapper.toDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc
                .perform(get("/license/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    @DisplayName("Возвращение списка DTO, а не Entity")
    void testEntityDtoMapperReturnsNoItems() throws Exception {

        final List<License> entityList = List.of(ENTITY);

        when(mockService.findAll()).thenReturn(entityList);
        when(mockMapper.toDtoList(entityList)).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc
                .perform(get("/license/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    @DisplayName("Возвращение DTO по ID")
    void testGetById() throws Exception {

        when(mockService.findById(1L)).thenReturn(ENTITY);
        when(mockMapper.toDto(ENTITY)).thenReturn(DTO);

        final MockHttpServletResponse response = mockMvc
                .perform(get("/license/id={id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(DTO));
    }

    @Test
    @DisplayName("Сохранение сущности")
    void testCreate() throws Exception {

        when(mockMapper.toEntity(DTO)).thenReturn(ENTITY);

        final MockHttpServletResponse response = mockMvc.perform(post("/admin/license/new")
                        .content(objectMapper.writeValueAsString(DTO)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(DTO));
        verify(mockService).save(ENTITY);
    }

    @Test
    @DisplayName("Выброс NotExecutedException при ошибке валидации (сохранение сущности)")
    void testCreateThrowsNotExecutedException() throws Exception {

        when(mockMapper.toEntity(INVALID_DTO)).thenThrow(new NotExecutedException());

        final MockHttpServletResponse response = mockMvc.perform(post("/admin/license/new")
                        .content(objectMapper.writeValueAsString(INVALID_DTO)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Обновление сущности")
    void testUpdate() throws Exception {

        when(mockMapper.toEntity(DTO)).thenReturn(ENTITY);

        final MockHttpServletResponse response = mockMvc.perform(patch("/admin/license/edit")
                        .content(objectMapper.writeValueAsString(DTO)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(DTO));
        verify(mockService).save(ENTITY);
    }

    @Test
    @DisplayName("Выброс NotExecutedException при ошибке валидации (обновление сущности)")
    void testUpdateThrowsNotExecutedException() throws Exception {

        when(mockMapper.toEntity(INVALID_DTO)).thenThrow(new NotExecutedException());

        final MockHttpServletResponse response = mockMvc.perform(patch("/admin/license/edit")
                        .content(objectMapper.writeValueAsString(INVALID_DTO)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Удаление сущности по ID")
    void testDeleteById() throws Exception {

        final MockHttpServletResponse response = mockMvc.perform(delete("/admin/license/id={id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockService).delete(1L);
    }
}
