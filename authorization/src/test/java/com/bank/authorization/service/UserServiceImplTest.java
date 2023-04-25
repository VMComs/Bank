package com.bank.authorization.service;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.entity.User;
import com.bank.authorization.exception.UserNotFoundException;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;
    @Mock
    UserMapper mapper;
    @InjectMocks
    UserServiceImpl service;

    private final User entity = User.builder()
            .id(3L)
            .role(Set.of(new Role(1L, RoleEnum.ROLE_USER)))
            .password("User!2@#")
            .profileId(3L)
            .build();

    private final UserDTO entityDTO = UserDTO.builder()
            .id(3L)
            .role(Set.of(new Role(1L, RoleEnum.ROLE_USER)))
            .password("User!2@#")
            .profileId(3L)
            .build();

    private final List<User> userList = List.of(entity);
    private final List<UserDTO> userDtoList = List.of(entityDTO);

    @Test
    void shouldReturnListOfUsersDto() {
        doReturn(userList).when(repository).findAll();
        doReturn(userDtoList).when(mapper).toDTOList(userList);
        List<UserDTO> userListInner = service.getAll();

        verify(repository, times(1)).findAll();
        assertThat(userListInner).isNotEmpty();
        assertThat(userListInner).isEqualTo(userDtoList);
    }

    @Test
    void shouldReturnUserById() {
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(entityDTO);
        UserDTO actualResult = service.getById(entity.getId());

        verify(repository, times(1)).findById(entity.getId());
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(entityDTO);
    }

    @Test
    void shouldThrowExceptionOnNullGetUserById() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(anyLong())).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldReturnUserByProfileId() {
        when(repository.getUserByProfileId(entity.getProfileId())).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(entityDTO);
        UserDTO actualResult = service.getByProfileId(entity.getProfileId());

        verify(repository, times(1)).getUserByProfileId(entity.getProfileId());
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(entityDTO);
    }

    @Test
    void shouldThrowExceptionOnNullGetByProfileId() {
        when(repository.getUserByProfileId(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByProfileId(anyLong())).isInstanceOf(UserNotFoundException.class);
    }


    @Test
    void shouldAddUser() {
        when(mapper.toUser(entityDTO)).thenReturn(entity);
        when(repository.saveAndFlush(entity)).thenReturn(entity);

        service.add(entityDTO);

        verify(repository, times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldUpdateUser() {
        when(repository.findById(entityDTO.getId())).thenReturn(Optional.of(entity));
        when(service.getById(entityDTO.getId())).thenReturn(entityDTO);
        when(mapper.toUser(entityDTO)).thenReturn(entity);
        when(repository.saveAndFlush(entity)).thenReturn(entity);

        service.update(entityDTO.getId(), entityDTO);

        verify(repository, times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldDeleteUser() {
        service.delete(anyLong());

        verify(repository, times(1)).deleteById(anyLong());
    }
}
