package com.bank.transfer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Класс переводов на номер телефона
 * Поля:
 * phoneNumber - номер телефона получателя
 */
@Entity
@Table(name = "phone_transfer")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class PhoneTransfer extends AbstractTransfer {
    @Column(name = "phone_number")
    @NotNull(message = "Phone number shouldn't be null")
    @Positive(message = "Account number shouldn't be negative")
    private Long phoneNumber;
}
