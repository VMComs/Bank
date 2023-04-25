package com.bank.publicinfo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Сущность Certificate - сертификат банка.
 * <b>photo</b> - фотография сертификата. Обязательное поле.
 * <b>bankDetailsId</b> - ссылка на реквизиты банка. Внешний ключ,
 * ссылается на таблицу bank_details. Обязательное поле
 *
 * @see BankDetails
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "certificate", schema = "public_bank_information")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Column(name = "photo")
    private Byte[] photo;

    @NonNull
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private BankDetails bankDetails;
}
