package com.bank.transfer.entities;

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
 * Класс переводов на банковскую карту
 * Поля:
 * cardNumber - номер карты получателя
 */
@Entity
@Table(name = "card_transfer")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
public class CardTransfer extends AbstractTransfer {
    @Column(name = "card_number")
    @NotNull(message = "Card number shouldn't be null")
    @Positive(message = "Card number shouldn't be negative")
    private Long cardNumber;
}
