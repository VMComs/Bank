package com.bank.authorization.service;

import com.bank.authorization.controller.UserRestController;
import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.pojo.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private UserRestController mockUserRestController;

    private UserDetailsServiceImpl userDetailsServiceImplUnderTest;

    final Profile profile = Profile.builder()
            .id(0L)
            .email("email")
            .snils(0L)
            .phoneNumber(0L)
            .actualRegistrationId(0L)
            .nameOnCard("name")
            .passportId(0L)
            .inn(0L)
            .build();

    final UserDTO userDTO = new UserDTO(0L,
            Set.of(new Role(0L, RoleEnum.ROLE_ADMIN)), 0L, "password");

    @BeforeEach
    void setUp() {
        userDetailsServiceImplUnderTest = new UserDetailsServiceImpl(mockUserService, mockUserRestController);
    }

    @Test
    void testLoadUserByUsername() {
        when(mockUserRestController.getProfileByUsername(profile.getEmail())).thenReturn(profile);
        when(mockUserService.getByProfileId(0L)).thenReturn(userDTO);

        final UserDetails result = userDetailsServiceImplUnderTest.loadUserByUsername(profile.getEmail());

        verify(mockUserService, times(1)).getByProfileId(0L);
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("email");
    }

    @Test
    void testLoadUserByUsernameUserRestControllerReturnsNull() {
        when(mockUserRestController.getProfileByUsername("username")).thenReturn(null);

        assertThatThrownBy(() -> userDetailsServiceImplUnderTest.loadUserByUsername("username"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
