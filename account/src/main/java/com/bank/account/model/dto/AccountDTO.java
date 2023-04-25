package com.bank.account.model.dto;

import com.bank.account.model.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Интеграционный класс для сущности account и rest-сервисов
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO extends Account {
    @NotNull(message = "Id may not be null")
    @Min(value = 1, message = "Id should not be 0")
    private Long id;

    @NotNull(message = "Account number may not be null")
    @Min(value = 1, message = "Account number should not be 0")
    private Long accountNumber;

    @Digits(integer=20, fraction=2)
    private double money;

    @NotEmpty(message = "Negative balance should not be empty")
    private boolean negativeBalance;

    @NotNull(message = "Passport Id number should be greater than 0")
    private Long passportId;

    @NotNull(message = "BankDetails Id number should be greater than 0")
    private Long bankDetailsId;

    @NotNull(message = "Profile Id number may not be null")
    private Long profileId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Double.compare(that.money, money) == 0 && negativeBalance == that.negativeBalance && Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(passportId, that.passportId) && Objects.equals(bankDetailsId, that.bankDetailsId) && Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, money, negativeBalance, passportId, bankDetailsId, profileId);
    }
}
