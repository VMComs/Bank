package com.bank.account.model.dto;

import com.bank.account.model.entity.Audit;
import lombok.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Интеграционный класс для сущности audit и rest-сервисов
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDTO extends Audit {
    @NotEmpty(message = "Id should not be empty")
    @Min(value = 1, message = "Id should not be 0")
    private Long id;

    @NotEmpty(message = "Entity type should not be empty")
    @Size(min = 2, max = 40, message = "Entity type's size should be between 2 and 40 characters")
    private String entityType;

    @NotEmpty(message = "Operation type should not be empty")
    @Size(min = 2, max = 255, message = "Operation type's size should be between 2 and 255 characters")
    private String operationType;

    @NotEmpty(message = "createdBy should not be empty")
    @Size(min = 2, max = 255, message = "createdBy' size should be between 2 and 255 characters")
    private String createdBy;

    private String modifiedBy;

    @NotEmpty(message = "Creation time should not be empty")
    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private String newEntityJson;

    @NotEmpty(message = "EntityJson should not be empty")
    private String entityJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditDTO auditDTO = (AuditDTO) o;
        return Objects.equals(id, auditDTO.id) && Objects.equals(entityType, auditDTO.entityType) && Objects.equals(operationType, auditDTO.operationType) && Objects.equals(createdBy, auditDTO.createdBy) && Objects.equals(modifiedBy, auditDTO.modifiedBy) && Objects.equals(createdAt, auditDTO.createdAt) && Objects.equals(modifiedAt, auditDTO.modifiedAt) && Objects.equals(newEntityJson, auditDTO.newEntityJson) && Objects.equals(entityJson, auditDTO.entityJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityType, operationType, createdBy, modifiedBy, createdAt, modifiedAt, newEntityJson, entityJson);
    }
}
