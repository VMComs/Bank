package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspAccountTransferDTO;
import com.bank.antifraud.dto.SuspCardTransferDTO;
import com.bank.antifraud.dto.SuspPhoneTransferDTO;
import com.bank.antifraud.entity.SuspAccountTransfer;
import com.bank.antifraud.entity.SuspCardTransfer;
import com.bank.antifraud.entity.SuspPhoneTransfer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-25T12:11:56+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Microsoft)"
)
@Component
public class ConverterServiceImpl implements ConverterService {

    @Override
    public SuspCardTransfer convertToEntity(SuspCardTransferDTO transferDTO) {
        if ( transferDTO == null ) {
            return null;
        }

        SuspCardTransfer suspCardTransfer = new SuspCardTransfer();

        suspCardTransfer.setId( transferDTO.getId() );
        suspCardTransfer.setCardTransferId( transferDTO.getCardTransferId() );
        suspCardTransfer.setBlocked( transferDTO.isBlocked() );
        suspCardTransfer.setSuspicious( transferDTO.isSuspicious() );
        suspCardTransfer.setBlockedReason( transferDTO.getBlockedReason() );
        suspCardTransfer.setSuspiciousReason( transferDTO.getSuspiciousReason() );

        return suspCardTransfer;
    }

    @Override
    public SuspAccountTransfer convertToEntity(SuspAccountTransferDTO transferDTO) {
        if ( transferDTO == null ) {
            return null;
        }

        SuspAccountTransfer suspAccountTransfer = new SuspAccountTransfer();

        suspAccountTransfer.setId( transferDTO.getId() );
        suspAccountTransfer.setAccountTransferId( transferDTO.getAccountTransferId() );
        suspAccountTransfer.setBlocked( transferDTO.isBlocked() );
        suspAccountTransfer.setSuspicious( transferDTO.isSuspicious() );
        suspAccountTransfer.setBlockedReason( transferDTO.getBlockedReason() );
        suspAccountTransfer.setSuspiciousReason( transferDTO.getSuspiciousReason() );

        return suspAccountTransfer;
    }

    @Override
    public SuspPhoneTransfer convertToEntity(SuspPhoneTransferDTO transferDTO) {
        if ( transferDTO == null ) {
            return null;
        }

        SuspPhoneTransfer suspPhoneTransfer = new SuspPhoneTransfer();

        suspPhoneTransfer.setId( transferDTO.getId() );
        suspPhoneTransfer.setPhoneTransferId( transferDTO.getPhoneTransferId() );
        suspPhoneTransfer.setBlocked( transferDTO.isBlocked() );
        suspPhoneTransfer.setSuspicious( transferDTO.isSuspicious() );
        suspPhoneTransfer.setBlockedReason( transferDTO.getBlockedReason() );
        suspPhoneTransfer.setSuspiciousReason( transferDTO.getSuspiciousReason() );

        return suspPhoneTransfer;
    }

    @Override
    public SuspCardTransferDTO convertToDTO(SuspCardTransfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        SuspCardTransferDTO suspCardTransferDTO = new SuspCardTransferDTO();

        suspCardTransferDTO.setId( transfer.getId() );
        suspCardTransferDTO.setCardTransferId( transfer.getCardTransferId() );
        suspCardTransferDTO.setBlocked( transfer.isBlocked() );
        suspCardTransferDTO.setSuspicious( transfer.isSuspicious() );
        suspCardTransferDTO.setBlockedReason( transfer.getBlockedReason() );
        suspCardTransferDTO.setSuspiciousReason( transfer.getSuspiciousReason() );

        return suspCardTransferDTO;
    }

    @Override
    public SuspAccountTransferDTO convertToDTO(SuspAccountTransfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        SuspAccountTransferDTO suspAccountTransferDTO = new SuspAccountTransferDTO();

        suspAccountTransferDTO.setId( transfer.getId() );
        suspAccountTransferDTO.setAccountTransferId( transfer.getAccountTransferId() );
        suspAccountTransferDTO.setBlocked( transfer.isBlocked() );
        suspAccountTransferDTO.setSuspicious( transfer.isSuspicious() );
        suspAccountTransferDTO.setBlockedReason( transfer.getBlockedReason() );
        suspAccountTransferDTO.setSuspiciousReason( transfer.getSuspiciousReason() );

        return suspAccountTransferDTO;
    }

    @Override
    public SuspPhoneTransferDTO convertToDTO(SuspPhoneTransfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        SuspPhoneTransferDTO suspPhoneTransferDTO = new SuspPhoneTransferDTO();

        suspPhoneTransferDTO.setId( transfer.getId() );
        suspPhoneTransferDTO.setPhoneTransferId( transfer.getPhoneTransferId() );
        suspPhoneTransferDTO.setBlocked( transfer.isBlocked() );
        suspPhoneTransferDTO.setSuspicious( transfer.isSuspicious() );
        suspPhoneTransferDTO.setBlockedReason( transfer.getBlockedReason() );
        suspPhoneTransferDTO.setSuspiciousReason( transfer.getSuspiciousReason() );

        return suspPhoneTransferDTO;
    }
}
