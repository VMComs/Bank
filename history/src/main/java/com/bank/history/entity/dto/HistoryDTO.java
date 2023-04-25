package com.bank.history.entity.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Класс дто создан для того, чтобы сервису не брать каждый раз в работу модель, а общаться с этим классом
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HistoryDTO {
    Long id;
    Long transferAuditId;
    Long profileAuditId;
    Long accountAuditId;
    Long antiFraudAuditId;
    Long bankInfoAuditId;
    Long authorizationAuditId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HistoryDTO entity = (HistoryDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.transferAuditId, entity.transferAuditId) &&
                Objects.equals(this.profileAuditId, entity.profileAuditId) &&
                Objects.equals(this.accountAuditId, entity.accountAuditId) &&
                Objects.equals(this.antiFraudAuditId, entity.antiFraudAuditId) &&
                Objects.equals(this.bankInfoAuditId, entity.bankInfoAuditId) &&
                Objects.equals(this.authorizationAuditId, entity.authorizationAuditId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transferAuditId, profileAuditId,
                accountAuditId, antiFraudAuditId, bankInfoAuditId,
                authorizationAuditId);
    }
}
