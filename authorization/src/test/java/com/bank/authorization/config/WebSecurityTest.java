package com.bank.authorization.config;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.feign.ProfileFeignClient;
import com.bank.authorization.pojos.Profile;
import com.bank.authorization.service.UserDetailsServiceImpl;
import com.bank.authorization.service.UserService;
import com.bank.authorization.util.ErrBindingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import java.util.Set;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Disabled("Только с подключенной базой данных")
public class WebSecurityTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @Autowired
    private ProfileFeignClient mockProfileFeignClient;
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

    final private ObjectMapper objectMapper = new ObjectMapper();

    @RegisterExtension
    static WireMockExtension profileFeignMock = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8089))
            .build();

    @Test
    void deniedLogin() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @Disabled("Не рабочий")
    void correctLogin() throws Exception {
        final Profile profile = Profile.builder()
                .id(0L)
                .email("Admin@mail.ru")
                .snils(0L)
                .phoneNumber(0L)
                .actualRegistrationId(0L)
                .nameOnCard("name")
                .passportId(0L)
                .inn(0L).build();
        final UserDTO userDTO = UserDTO.builder()
                .id(3L)
                .role(Set.of(new Role(1L, RoleEnum.ROLE_ADMIN)))
                .password("Admin1@")
                .profileId(3L)
                .build();
        profileFeignMock.stubFor(com.github.tomakehurst.wiremock.client.WireMock
                .get("/api/profile?username=" + "Admin%40mail.ru")
                .willReturn(okJson(objectMapper.writeValueAsString(profile))));
        when(mockUserService.getByProfileId(profile.getId())).thenReturn(userDTO);

        mockMvc.perform(formLogin()
                        .user("Admin@mail.ru")
                        .password("Admin1@"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/auth"));
    }
}
