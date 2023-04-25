package com.bank.transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Класс аудита банковских операций
 * Поля:
 * id - технический идентификатор
 * entityType - тип сущности
 * operationType - тип операции
 * createdBy - кто создал
 * modifiedBy - кто изменил
 * createdAt - время создания
 * modifiedAt - время изменения
 * newEntityJson - json при изменении
 * entityJson - json при создании
 */
@Entity
@Table(name = "audit")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "entity_type")
    private String entityType;
    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private AuditOperationType operationType;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "modified_at")
    private Date modifiedAt;
    @Column(name = "new_entity_json")
    private String newEntityJson;
    @Column(name = "entity_json")
    private String entityJson;
}
