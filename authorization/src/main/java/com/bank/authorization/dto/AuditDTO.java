package com.bank.authorization.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Трансферный слой dto для сущности audit
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditDTO {
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

    @NotNull
    @NotEmpty(message = "Time of creation should not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp modifiedAt;

    private String newEntityJson;

    @NotNull
    @NotEmpty(message = "Entity should not be empty")
    private String entityJson;


}
