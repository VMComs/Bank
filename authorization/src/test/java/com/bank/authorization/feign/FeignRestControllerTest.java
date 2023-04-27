package com.bank.authorization.feign;

import com.bank.authorization.config.SuccessUserHandler;
import com.bank.authorization.config.WebSecurityConfig;
import com.bank.authorization.controller.UserRestController;
import com.bank.authorization.pojo.Profile;
import com.bank.authorization.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Objects;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("Только с подключенной базой данных")
public class FeignRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @MockBean
    private ProfileFeignClient mockProfileFeignClient;
    @MockBean
    private SuccessUserHandler successUserHandler;
    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    private UserRestController userRestController;

    final ObjectMapper objectMapper = new ObjectMapper();
    @RegisterExtension
    static WireMockExtension profileFeignMock = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8089))
            .build();

    @Test
    void testGetProfileByUsername() throws Exception {
        final Profile profile = Profile.builder()
                .id(0L)
                .email("Admin@mail.ru")
                .snils(0L)
                .phoneNumber(0L)
                .actualRegistrationId(0L)
                .nameOnCard("name")
                .passportId(0L)
                .inn(0L).build();
        profileFeignMock.stubFor(com.github.tomakehurst.wiremock.client.WireMock
                .get("/api/profile?username=" + "Admin%40mail.ru")
                .willReturn(okJson(objectMapper.writeValueAsString(profile))));

        when(mockProfileFeignClient.getProfileByUsername("Admin@mail.ru")).thenReturn(ResponseEntity.ok(profile));
        ResponseEntity<Profile> profileResponse = mockProfileFeignClient.getProfileByUsername("Admin@mail.ru");

        Assertions.assertThat(Objects.requireNonNull(profileResponse.getBody()).getEmail()).isEqualTo("Admin@mail.ru");
        }
}
