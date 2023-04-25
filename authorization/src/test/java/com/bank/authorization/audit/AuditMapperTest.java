package com.bank.authorization.audit;

import com.bank.authorization.dto.AuditDTO;
import com.bank.authorization.entity.Audit;
import com.bank.authorization.mapper.AuditMapper;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AuditMapperTest {

    AuditMapper auditMapper = AuditMapper.MAPPER;
    @Test
    void shouldMapToDto() {
        Audit audit = Audit.builder()
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

        AuditDTO actualResult = auditMapper.toDTO(audit);

        AuditDTO expectedResult = AuditDTO.builder()
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

        assertThat(actualResult).isInstanceOf(AuditDTO.class);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldMapToUser() {
        AuditDTO auditDTO = AuditDTO.builder()
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

        Audit actualResult = auditMapper.toAudit(auditDTO);

        Audit expectedResult = Audit.builder()
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

        assertThat(actualResult).isInstanceOf(Audit.class);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldMapToUserDtoList() {
        List<Audit> auditList = List.of(Audit.builder()
                .id(1L)
                .createdAt(new Timestamp(12345))
                .createdBy("Vlad")
                .entityJson("EntityJson")
                .entityType("Entity")
                .modifiedAt(new Timestamp(12345))
                .modifiedBy("Vlad")
                .operationType("Operation")
                .newEntityJson("NewJson")
                .build());

        List<AuditDTO> actualResult = auditMapper.toDTOList(auditList);

        List<AuditDTO> expectedResult = List.of(AuditDTO.builder()
                .id(1L)
                .createdAt(new Timestamp(12345))
                .createdBy("Vlad")
                .entityJson("EntityJson")
                .entityType("Entity")
                .modifiedAt(new Timestamp(12345))
                .modifiedBy("Vlad")
                .operationType("Operation")
                .newEntityJson("NewJson")
                .build());

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
