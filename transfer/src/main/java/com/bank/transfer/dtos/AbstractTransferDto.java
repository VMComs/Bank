package com.bank.transfer.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Родительский класс DTO всех видов транзакций
 * Классы наследники: AccountTransferDto, CardTransferDto, PhoneTransferDto
 * Поля:
 * amount - сумма перевода
 * purpose - цель перевода
 * accountDetailsId - id отправителя !!!Нужно избавиться от этого поля, может быть через запрос к auth - сервису
 */
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class AbstractTransferDto {
    @NotNull(message = "Amount shouldn't be null")
    @Positive(message = "Amount shouldn't be negative")
    protected Double amount;
    protected String purpose;
    @NotNull(message = "Account id shouldn't be null")
    @Positive(message = "Account id shouldn't be negative")
    protected Long accountDetailsId;
}
