package com.bank.antifraud.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность перевода по номеру телефона
 * id - id сущности
 * phoneTransferId - id операции
 * isBlocked - операция заблокирована или нет
 * isSuspicious - операция подозрительна или нет
 * blockedReason - причина блокировки. Не может быть Null
 * suspiciousReason - причина подозрительности
 */

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "suspicious_phone_transfers", uniqueConstraints = {@UniqueConstraint(columnNames = {"phone_transfer_id"})})
public class SuspPhoneTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "phone_transfer_id")
    private int phoneTransferId;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_suspicious")
    private boolean isSuspicious;

    @Column(name = "blocked_reason")
    @NonNull
    private String blockedReason;

    @Column(name = "suspicious_reason")
    private String suspiciousReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
