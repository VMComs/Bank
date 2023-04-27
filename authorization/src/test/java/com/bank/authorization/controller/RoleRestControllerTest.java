package com.bank.authorization.controller;

import com.bank.authorization.config.SuccessUserHandler;
import com.bank.authorization.config.WebSecurityConfig;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.service.RoleService;
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
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleRestController.class)
class RoleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @MockBean
    private SuccessUserHandler successUserHandler;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private RoleService mockRoleService;
    @MockBean
    private ErrBindingResult mockErrBindingResult;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Role entity = new Role(0L, RoleEnum.ROLE_ADMIN);
    private final List<Role> entityList = List.of(entity);

    @Test
    void testGetAll() throws Exception {
        when(mockRoleService.getAll()).thenReturn(entityList);

        final MockHttpServletResponse response = mockMvc.perform(get("/roles")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(entityList));
    }

    @Test
    void testGetAllRoleServiceReturnsNoItems() throws Exception {
        when(mockRoleService.getAll()).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc.perform(get("/roles")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetRoleById() throws Exception {
        when(mockRoleService.getById(entity.getId())).thenReturn(entity);

        final MockHttpServletResponse response = mockMvc.perform(get("/roles/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(entity));
    }

    @Test
    void testAdd() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(post("/roles")
                        .content(objectMapper.writeValueAsString(entity)).contentType(MediaType.APPLICATION_JSON)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Role ROLE_ADMIN has been added");
        verify(mockRoleService).add(entity);
    }

    @Test
    void testAddThrowsRoleNotCreatedException() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(post("/roles")
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

        final MockHttpServletResponse response = mockMvc.perform(put("/roles/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(entity)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Role with id-0 has been updated");
        verify(mockRoleService).update(entity.getId(), entity);
    }

    @Test
    void testUpdateThrowsRoleNotCreatedException() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(put("/roles/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content("errorContent").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testDelete() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(delete("/roles/{id}", 0)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Role with id-0 has been deleted");
        verify(mockRoleService).delete(entity.getId());
    }
}
