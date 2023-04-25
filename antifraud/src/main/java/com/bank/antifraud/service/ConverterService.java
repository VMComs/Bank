package com.bank.antifraud.service;

import com.bank.antifraud.dto.SuspAccountTransferDTO;
import com.bank.antifraud.dto.SuspCardTransferDTO;
import com.bank.antifraud.dto.SuspPhoneTransferDTO;
import com.bank.antifraud.entity.SuspAccountTransfer;
import com.bank.antifraud.entity.SuspCardTransfer;
import com.bank.antifraud.entity.SuspPhoneTransfer;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Сервисный слой для конвертации сущностей
 */
@Mapper(componentModel = "spring")
public interface ConverterService {
    SuspCardTransfer convertToEntity(SuspCardTransferDTO transferDTO);

    SuspAccountTransfer convertToEntity(SuspAccountTransferDTO transferDTO);

    SuspPhoneTransfer convertToEntity(SuspPhoneTransferDTO transferDTO);

    SuspCardTransferDTO convertToDTO(SuspCardTransfer transfer);

    SuspAccountTransferDTO convertToDTO(SuspAccountTransfer transfer);

    SuspPhoneTransferDTO convertToDTO(SuspPhoneTransfer transfer);

    static <R, E> List<R> convertToList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }
}
