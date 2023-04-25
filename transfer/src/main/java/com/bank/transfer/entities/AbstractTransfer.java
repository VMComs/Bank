package com.bank.transfer.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Родительский класс всех видов транзакций
 * Классы наследники: AccountTransfer, CardTransfer, PhoneTransfer
 * Поля:
 * id - технический идентификатор транзакции
 * amount - сумма перевода
 * purpose - цель перевода
 * accountDetailsId - технический идентификатор банковского счета отправителя
 */
@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class AbstractTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    protected Long id;
    @Column(name = "amount")
    @NotNull(message = "Amount shouldn't be null")
    @Positive(message = "Amount shouldn't be negative")
    protected Double amount;
    @Column(name = "purpose")
    protected String purpose;
    @Column(name = "account_details_id")
    @NotNull(message = "Account id shouldn't be null")
    @Positive(message = "Account id shouldn't be negative")
    protected Long accountDetailsId;
}
