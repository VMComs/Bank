package com.bank.transfer.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Класс DTO переводов на банковский счет
 * Поля:
 * accountNumber - номер счета получателя
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class AccountTransferDto extends AbstractTransferDto {
    @NotNull(message = "Account number shouldn't be null")
    @Positive(message = "Account number shouldn't be negative")
    private Long accountNumber;
}
