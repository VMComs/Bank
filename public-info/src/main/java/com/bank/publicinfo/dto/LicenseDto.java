package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.License;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * LicenseDto - объект передачи данных сущности License (лизенция банка).
 * <b>photo</b> - фотография лицензии. Обязательное поле.
 * <b>bankDetailsId</b> - ссылка на реквизиты банка. Внешний ключ,
 * ссылается на таблицу bank_details. Обязательное поле
 *
 * @see License
 * @see BankDetails
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class LicenseDto {

    @NotNull(message = "Id should not be null")
    @Min(value = 1, message = "Id should be greater than 0")
    private Long id;

    @NonNull
    @NotEmpty(message = "License photo should not be empty")
    private Byte[] photo;

    @NonNull
    @NotNull(message = "Bank details should not be null")
    private BankDetails bankDetails;
}
