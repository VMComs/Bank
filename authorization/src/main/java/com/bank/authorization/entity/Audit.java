package com.bank.authorization.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Сущность Audit - аудит.
 * entityType - тип сущности. Обязательное поле
 * operationType - тип операции. Обязательное поле
 * createdBy - создатель сущности. Обязательное поле
 * modifiedBy - кто изменил сущность. Может быть пустым
 * createdAt - время создания сущности относительно временной зоны. Обязательное поле
 * modified_at - время изменения сущности относительно временной зоны. Может быть пустым
 * newEntityJson - JSON, заполняется при изменении сущности. Для хранения в БД преобразуется
 * к текстовому представлению. Может быть пустым
 * entityJson - JSON, заполняется при изменении и сохранении сущности. Для хранения в БД
 * преобразуется к текстовому представлению. Обязательное поле
 */

@Getter
@Setter
//@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "audit", schema = "auth")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 40)
    @Column(name = "entity_type")
    private String entityType;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "operation_type")
    private String operationType;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "created_by")
    private String createdBy;

    @Size(max = 255)
    @Column(name = "modified_by")
    private String modifiedBy;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "created_at")
    private Timestamp  createdAt;

    @Column(name = "modified_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp  modifiedAt;

    @Column(name = "new_entity_json")
    private String newEntityJson;

    @NotEmpty(message = "Entity should not be empty")
    @Column(name = "entity_json")
    private String entityJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Audit audit = (Audit) o;
        return Objects.equals(id, audit.id) &&
                Objects.equals(entityType, audit.entityType) &&
                Objects.equals(operationType, audit.operationType) &&
                Objects.equals(createdBy, audit.createdBy) &&
                Objects.equals(modifiedBy, audit.modifiedBy) &&
                Objects.equals(createdAt, audit.createdAt) &&
                Objects.equals(modifiedAt, audit.modifiedAt) &&
                Objects.equals(newEntityJson, audit.newEntityJson) &&
                Objects.equals(entityJson, audit.entityJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityType, operationType, createdBy,
                modifiedBy, createdAt, modifiedAt, newEntityJson, entityJson);
    }
}
