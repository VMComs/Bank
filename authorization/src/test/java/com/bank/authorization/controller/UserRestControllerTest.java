package com.bank.authorization.controller;

import com.bank.authorization.config.SuccessUserHandler;
import com.bank.authorization.config.WebSecurityConfig;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.entity.User;
import com.bank.authorization.feign.ProfileFeignClient;
import com.bank.authorization.pojo.Profile;
import com.bank.authorization.service.UserDetailsServiceImpl;
import com.bank.authorization.service.UserService;
import com.bank.authorization.util.ErrBindingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @MockBean
    private SuccessUserHandler successUserHandler;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private ErrBindingResult mockErrBindingResult;
    @MockBean
    private BindingResult bindingResult;
    @MockBean
    private ProfileFeignClient mockProfileFeignClient;
    @MockBean
    private Authentication authentication;

    @RegisterExtension
    static WireMockExtension profileFeignMock = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8089))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Profile profileEntity = Profile.builder()
            .id(0L)
            .email("Admin@mail.ru")
            .snils(0L)
            .phoneNumber(0L)
            .actualRegistrationId(0L)
            .nameOnCard("name")
            .passportId(0L)
            .inn(0L)
            .build();
    private final UserDTO entityDTO = UserDTO.builder()
            .id(3L)
            .role(Set.of(new Role(1L, RoleEnum.ROLE_ADMIN)))
            .password("Admin1@")
            .profileId(3L)
            .build();
    private final User entity = User.builder()
            .id(3L)
            .role(Set.of(new Role(1L, RoleEnum.ROLE_ADMIN)))
            .password("Admin1@")
            .profileId(3L)
            .build();

    final List<UserDTO> entityDtoList = List.of(entityDTO);

    @Test
    void testGetAll() throws Exception {
        when(mockUserService.getAll()).thenReturn(entityDtoList);

        final MockHttpServletResponse response = mockMvc.perform(
                        get("/users").with(user("Admin@mail.ru"))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(entityDtoList));
    }

    @Test
    void testGetAllUserServiceReturnsNoItems() throws Exception {
        when(mockUserService.getAll()).thenReturn(Collections.emptyList());

        final MockHttpServletResponse response = mockMvc.perform(get("/users")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetById() throws Exception {
        when(mockUserService.getById(entityDTO.getId())).thenReturn(entityDTO);

        final MockHttpServletResponse response = mockMvc.perform(get("/users/{id}", entityDTO.getId())
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(entityDTO));
    }

    @Test
    void testGetProfileByUsername() throws Exception {
        doReturn(ResponseEntity.ok(profileEntity)).when(mockProfileFeignClient).getProfileByUsername(profileEntity.getEmail());
        profileFeignMock.stubFor(com.github.tomakehurst.wiremock.client.WireMock
                .get(urlEqualTo("/api/profile?username=" + profileEntity.getEmail()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(objectMapper.writeValueAsString(profileEntity))
                        .withHeader("Content-Type", "application/json")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8089/api/profile?username=" + profileEntity.getEmail());
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(stringResponse).isEqualTo(objectMapper.writeValueAsString(profileEntity));
    }
    private String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }
    private String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }

    @Test
    void testGetAuthUser() throws Exception {
        when(mockProfileFeignClient.getProfileByUsername("Admin@mail.ru")).thenReturn(ResponseEntity.ok(profileEntity));

        final MockHttpServletResponse response = mockMvc.perform(get("/userView")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(profileEntity));
    }

    @Test
    void testAdd() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(post("/users")
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(entityDTO)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("User has been added");
        verify(mockUserService).add(entityDTO);
    }

    @Test
    void testAddThrowsUserNotCreatedException() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(bindingResult)).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(post("/users")
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

        final MockHttpServletResponse response = mockMvc.perform(put("/users/{id}", entityDTO.getId())
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(entityDTO)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("User with id-3 has been updated");
        verify(mockUserService).update(entityDTO.getId(), entityDTO);
    }

    @Test
    void testUpdateThrowsUserNotCreatedException() throws Exception {
        when(mockErrBindingResult.getErrorsFromBindingResult(any(BindingResult.class))).thenReturn("message");

        final MockHttpServletResponse response = mockMvc.perform(put("/users/{id}", entityDTO.getId())
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .content("errorContent").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testDelete() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(delete("/users/{id}", 3)
                        .with(user("Admin@mail.ru").password("Admin1@").roles("ADMIN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("User with id-3 has been deleted");
        verify(mockUserService).delete(entityDTO.getId());
    }
}
