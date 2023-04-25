package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import lombok.*;

import javax.validation.constraints.*;

/**
 * BankDetailsDto - объект передачи данных сущности BankDetails (банковские реквизиты).
 * <b>bik</b> - БИК (банковский идентификационный счет). Обязательное поле
 * <b>inn</b> - ИНН (идентификационный номер налогоплательщика). Обязательное поле
 * <b>kpp</b> - КПП (Код причины постановки на учет). Обязательное поле
 * <b>corAccount</b> - корреспондентский счет. Обязательное поле
 * <b>city</b> - город регистрации юридического адреса банка. Обязательное поле
 * <b>jointStockCompany</b> - акционерное общество. Обязательное поле
 * <b>name</b> - Имя банка. Обязательное поле
 *
 * @see BankDetails
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BankDetailsDto {

    @NotNull(message = "Id should not be null")
    @Min(value = 1, message = "Id should be greater than 0")
    private Long id;

    @NonNull
    @NotNull(message = "BIK should not be null")
    @Min(value = 100_000_000L, message = "BIK should be 9 characters long")
    @Max(value = 999_999_999L, message = "BIK should be 9 characters long")
    private Long bik;

    @NonNull
    @NotNull(message = "INN should not be null")
    @Min(value = 1_000_000_000L, message = "INN must contain between 10 and 13 characters")
    @Max(value = 999_999_999_999L, message = "INN must contain between 10 and 13 characters")
    private Long inn;

    @NonNull
    @NotNull(message = "KPP should not be null")
    @Min(value = 1_000_000_000L, message = "KPP must contain between 10 and 12 characters")
    @Max(value = 999_999_999_999L, message = "KPP must contain between 10 and 12 characters")
    private Long kpp;

    @NonNull
    @NotNull(message = "Correspondent account should not be null")
    @Min(value = 10_000_000_000_000_000L, message = "Correspondent account should be 17 characters long")
    @Max(value = 99_999_999_999_999_999L, message = "Correspondent account should be 17 characters long")
    private Long corAccount;

    @NonNull
    @NotEmpty(message = "City should not be empty")
    @Size(min = 2, max = 180, message = "City must contain between 2 and 180 characters")
    private String city;

    @NonNull
    @NotEmpty(message = "Joint stock company should not be empty")
    @Size(min = 2, max = 15, message = "Joint stock company name must contain between 2 and 15 characters")
    private String jointStockCompany;

    @NonNull
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 80, message = "Name must contain between 2 and 80 characters")
    private String name;
}
