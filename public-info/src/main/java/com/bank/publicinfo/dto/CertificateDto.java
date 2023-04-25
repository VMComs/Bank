package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.entity.Certificate;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * CertificateDto - объект передачи данных сущности Certificate (сертификат банка).
 * <b>photo</b> - фотография сертификата. Обязательное поле.
 * <b>bankDetailsId</b> - ссылка на реквизиты банка. Внешний ключ,
 * ссылается на таблицу bank_details. Обязательное поле
 *
 * @see Certificate
 * @see BankDetails
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CertificateDto {

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
