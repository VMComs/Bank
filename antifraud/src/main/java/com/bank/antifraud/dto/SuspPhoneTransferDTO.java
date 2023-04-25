package com.bank.antifraud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuspPhoneTransferDTO {
    private Long id;

    private int phoneTransferId;

    private boolean isBlocked;

    private boolean isSuspicious;

    private String blockedReason;

    private String suspiciousReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
