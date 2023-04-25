package com.bank.history.entity;

import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * модель, которая описана по таблице для преобразования структуры обьекта и данных, чтобы сохранять и передавать
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "history", schema = "history")
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transfer_audit_id")
    private Long transferAuditId;
    @Column(name = "profile_audit_id")
    private Long profileAuditId;
    @Column(name = "account_audit_id")
    private Long accountAuditId;
    @Column(name = "anti_fraud_audit_id")
    private Long antiFraudAuditId;
    @Column(name = "public_bank_info_audit_id")
    private Long bankInfoAuditId;
    @Column(name = "authorization_audit_id")
    private Long authorizationAuditId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final History history = (History) o;
        return id != null && Objects.equals(id, history.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

