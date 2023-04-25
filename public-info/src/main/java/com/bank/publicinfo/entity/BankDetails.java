package com.bank.publicinfo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Сущность BankDetails - банковские реквизиты.
 * <b>bik</b> - БИК (банковский идентификационный счет). Обязательное поле
 * <b>inn</b> - ИНН (идентификационный номер налогоплательщика). Обязательное поле
 * <b>kpp</b> - КПП (Код причины постановки на учет). Обязательное поле
 * <b>corAccount</b> - корреспондентский счет. Обязательное поле
 * <b>city</b> - город регистрации юридического адреса банка. Обязательное поле
 * <b>jointStockCompany</b> - акционерное общество. Обязательное поле
 * <b>name</b> - Имя банка. Обязательное поле
 *
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "bank_details", schema = "public_bank_information")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    @Column(name = "bik")
    private Long bik;

    @NonNull
    @NotNull
    @Column(name = "inn")
    private Long inn;

    @NonNull
    @NotNull
    @Column(name = "kpp")
    private Long kpp;

    @NonNull
    @NotNull
    @Column(name = "cor_account")
    private Long corAccount;

    @NonNull
    @NotEmpty
    @Size(max = 180)
    @Column(name = "city")
    private String city;

    @NonNull
    @NotEmpty
    @Size(max = 15)
    @Column(name = "joint_stock_company")
    private String jointStockCompany;

    @NonNull
    @NotEmpty
    @Size(max = 80)
    @Column(name = "name")
    private String name;
}
