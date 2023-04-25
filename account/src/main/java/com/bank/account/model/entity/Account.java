package com.bank.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Сущность, реализующая функционал банковского аккаунта.
 *  <b>accountNumber</b> - Номер  аккаунта банковского счета. Обязательное поле.
 *  <b>money</b> - Баланс банкоского счета. Обязательное поле.
 *  <b>negativeBalance</b> - Наличие негативного баланса. Обязательное поле.
 *  <b>passportId</b> - Номер паспорта клиента. Должен валидировать через API с таблицей profile.passport
 *                      микросервиса profile. Обязательное поле.
 *  <b>bankDetailsId</b> - Идентификатор банка. Должен валидировать через API с таблицей public_bank_information.bank_details
 *  *                      микросервиса public-info. Обязательное поле.
 *  <b>profileId</b> - Идентификатор профиля клиента. Должен валидировать через API с таблицей profile.profile
 *  *                      микросервиса profile. Обязательное поле.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
// @EntityListeners возможно пригодится для аудита
@Table (name = "account_details", schema = "account",
        uniqueConstraints =
        {
            @UniqueConstraint(columnNames = "account_number"),
            @UniqueConstraint(columnNames = "bank_details_id")
        }
        )
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    @NotNull
    private Long accountNumber;

    @Column(name = "money")
    @Digits(integer=20, fraction=2)
    private double money;

    @Column (name = "negative_balance")
    @NotEmpty
    private boolean negativeBalance;

    @Column (name = "passport_id")
    @NotNull
    private Long passportId;

    @Column (name = "bank_details_id")
    @NotNull
    private Long bankDetailsId;

    @Column (name = "profile_id")
    @NotNull
    private Long profileId;
}
