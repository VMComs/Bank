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
 * Класс DTO переводов на номер телефона
 * Поля:
 * phoneNumber - номер телефона получателя
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class PhoneTransferDto extends AbstractTransferDto {
    @NotNull(message = "Phone number shouldn't be null")
    @Positive(message = "Account number shouldn't be negative")
    private Long phoneNumber;
}
