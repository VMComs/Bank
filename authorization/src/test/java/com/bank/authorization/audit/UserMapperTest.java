package com.bank.authorization.audit;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.entity.User;
import com.bank.authorization.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserMapperTest {

    UserMapper userMapper = UserMapper.MAPPER;


    @Test
    void shouldMapToDto() {
        User user = User.builder()
                .id(3L)
                .role(Set.of(new Role(1L, RoleEnum.ROLE_USER)))
                .password("User!2@#")
                .profileId(3L)
                .build();
        UserDTO actualResult = userMapper.toDTO(user);

        UserDTO expectedResult = UserDTO.builder()
                .id(3L)
                .role(Set.of(new Role(1L, RoleEnum.ROLE_USER)))
                .password("User!2@#")
                .profileId(3L)
                .build();

        assertThat(actualResult).isInstanceOf(UserDTO.class);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldMapToUser() {
        UserDTO userDto = UserDTO.builder()
                .id(4L)
                .role(Set.of(new Role(2L, RoleEnum.ROLE_ADMIN)))
                .password("User2@!")
                .profileId(4L)
                .build();
        User actualResult = userMapper.toUser(userDto);

        User expectedResult = User.builder()
                .id(4L)
                .role(Set.of(new Role(2L, RoleEnum.ROLE_ADMIN)))
                .password("User2@!")
                .profileId(4L)
                .build();

        assertThat(actualResult).isInstanceOf(User.class);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldMapToUserDtoList() {
        List<User> userList = List.of(User.builder()
                .id(4L)
                .role(Set.of(new Role(2L, RoleEnum.ROLE_ADMIN)))
                .password("User2@!")
                .profileId(4L)
                .build());
        List<UserDTO> actualResult = userMapper.toDTOList(userList);

        List<UserDTO> expectedResult = List.of(UserDTO.builder()
                .id(4L)
                .role(Set.of(new Role(1L, RoleEnum.ROLE_ADMIN)))
                .password("User2@!")
                .profileId(4L)
                .build());

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
