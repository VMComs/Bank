package com.bank.authorization.service;

import com.bank.authorization.dto.AuditDTO;
import com.bank.authorization.entity.Audit;
import com.bank.authorization.exception.AuditNotFoundException;
import com.bank.authorization.mapper.AuditMapper;
import com.bank.authorization.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
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
public class AuditServiceImplTest {

    @Mock
    AuditRepository repository;
    @Mock
    AuditMapper mapper;
    @InjectMocks
    AuditServiceImpl service;

    private final Audit entity = Audit.builder()
            .id(1L)
            .createdAt(new Timestamp(12345))
            .createdBy("Vlad")
            .entityJson("EntityJson")
            .entityType("Entity")
            .modifiedAt(new Timestamp(12345))
            .modifiedBy("Vlad")
            .operationType("Operation")
            .newEntityJson("NewJson")
            .build();

    private final AuditDTO entityDTO = AuditDTO.builder()
            .id(1L)
            .createdAt(new Timestamp(12345))
            .createdBy("Vlad")
            .entityJson("EntityJson")
            .entityType("Entity")
            .modifiedAt(new Timestamp(12345))
            .modifiedBy("Vlad")
            .operationType("Operation")
            .newEntityJson("NewJson")
            .build();

    private final List<Audit> entityList = List.of(entity);
    private final List<AuditDTO> entityDTOList = List.of(entityDTO);

    @Test
    void shouldReturnListOfAuditsDto() {
        doReturn(entityList).when(repository).findAll();
        doReturn(entityDTOList).when(mapper).toDTOList(entityList);
        List<AuditDTO> entityListInner = service.getAll();

        verify(repository, times(1)).findAll();
        assertThat(entityListInner).isNotEmpty();
        assertThat(entityListInner).isEqualTo(entityDTOList);
    }

    @Test
    void shouldReturnAuditById() {
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(entityDTO);
        AuditDTO actualResult = service.getById(entity.getId());

        verify(repository, times(1)).findById(entity.getId());
        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(entityDTO);
    }

    @Test
    void shouldThrowExceptionOnNullGetAuditById() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(anyLong())).isInstanceOf(AuditNotFoundException.class);
    }

    @Test
    void shouldAddAudit() {
        when(mapper.toAudit(entityDTO)).thenReturn(entity);
        when(repository.saveAndFlush(entity)).thenReturn(entity);

        service.add(entityDTO);

        verify(repository, times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldUpdateAudit() {
        when(mapper.toAudit(entityDTO)).thenReturn(entity);
        when(repository.saveAndFlush(entity)).thenReturn(entity);

        service.update(entityDTO, entityDTO.getId());

        verify(repository, times(1)).saveAndFlush(entity);
    }

    @Test
    void shouldDeleteAudit() {
        service.delete(anyLong());

        verify(repository, times(1)).deleteById(anyLong());
    }
}
