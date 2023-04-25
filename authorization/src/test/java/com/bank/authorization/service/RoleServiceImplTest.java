package com.bank.authorization.service;

import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.RoleEnum;
import com.bank.authorization.exception.RoleNotFoundException;
import com.bank.authorization.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    RoleRepository repository;
    @InjectMocks
    RoleServiceImpl service;

    private final Role entity = Role.builder()
            .id(1L)
            .name(RoleEnum.ROLE_USER)
            .build();

    private final List<Role> entityList = List.of(entity);

    @Test
    void shouldReturnListOfRolesDto() {
        doReturn(entityList).when(repository).findAll();
        List<Role> entityListInner = service.getAll();

        verify(repository, times(1)).findAll();
        assertThat(entityListInner).isNotEmpty();
    }

    @Test
    void shouldReturnRoleById() {
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        Role actualResult = service.getById(entity.getId());

        verify(repository, times(1)).findById(entity.getId());
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(new Role(1L, RoleEnum.ROLE_USER));
    }

    @Test
    void shouldThrowExceptionOnNullGetRoleById() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(anyLong())).isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void shouldAddRole() {
        when(repository.saveAndFlush(entity)).thenReturn(entity);
        service.add(entity);

        verify(repository, times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldUpdateRole() {
        when(repository.saveAndFlush(entity)).thenReturn(entity);
        service.update(entity.getId(), entity);

        verify(repository, times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldDeleteRole() {
        service.delete(anyLong());

        verify(repository, times(1)).deleteById(anyLong());
    }
}
