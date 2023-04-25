package com.bank.transfer.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO класс, необходимый для работы с сущностью из account сервиса
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private Long passportId;
    private Long accountNumber;
    private Long bankDetailsId;
    private Double money;
    private boolean negativeBalance;
    private Long profileId;
}
