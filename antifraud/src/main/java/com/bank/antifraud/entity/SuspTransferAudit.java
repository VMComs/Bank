package com.bank.antifraud.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Сущность Audit - аудит.
 * entityType - тип сущности
 * operationType - тип операции
 * createdBy - создатель сущности
 * modifiedBy - кто изменил сущность. Может быть пустым
 * createdAt - время создания сущности относительно временной зоны
 * modified_at - время изменения сущности относительно временной зоны. Может быть пустым
 * newEntityJson - JSON, заполняется при изменении сущности. Для хранения в БД преобразуется
 * к текстовому представлению. Может быть пустым
 * entityJson - JSON, заполняется при изменении и сохранении сущности. Для хранения в БД
 * преобразуется к текстовому представлению
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "audit")
public class SuspTransferAudit {
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
}








