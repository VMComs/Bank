package com.bank.account.model.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Сущность Audit - аудит.
 * <b>entityType</b> - тип сущности. Обязательное поле
 * <b>operationType</b> - тип операции. Обязательное поле
 * <b>createdBy</b> - кто создал сущность. Обязательное поле
 * <b>modifiedBy</b> - кто изменил сущность. Может быть пустым
 * <b>createdAt</b> - время создания сущности. Обязательное поле
 * <b>modified_at</b> - время изменения сущности. Может быть пустым
 * <b>newEntityJson</b> - JSON, заполняется при изменении сущности. Для хранения в БД преобразуется
 * в текст. Может быть пустым
 * <b>entityJson</b> - JSON, заполняется при изменении и сохранении сущности. Для хранения в БД
 * преобразуется в текст. Обязательное поле
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit", schema = "account")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 40)
    @Column(name="entity_type")
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
    @Column(name = "created_at")
    private Timestamp  createdAt;

    @Column(name = "modified_at")
    private Timestamp  modifiedAt;

    @Column(name = "new_entity_json")
    private String newEntityJson;

    @NotEmpty
    @Column(name = "entity_json")
    private String entityJson;
}
