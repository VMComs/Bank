package com.bank.transfer.service;

import com.bank.transfer.entity.AuditOperationType;
import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransfer;
import com.bank.transfer.exception.AccountNotFoundException;
import com.bank.transfer.exception.TransferFailedException;
import com.bank.transfer.exception.TransferNotFoundException;
import com.bank.transfer.pojo.Account;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.util.AccountSearcherUtil;
import com.bank.transfer.util.AccountTransferMapper;
import com.bank.transfer.util.TransferUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный слой реализующий бизнес-логику работы банковских переводов по номеру счета
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountTransferService implements TransferService<AccountTransferDto> {
    private final AccountTransferRepository repository;
    private final AccountTransferMapper mapper = Mappers.getMapper(AccountTransferMapper.class);
    private final TransferUtil transferUtil;
    private final AccountSearcherUtil searcherUtil;
    private final AuditService auditService;
    /**
     * Метод, выполняющий транзакцию на банковский счет:
     *
     * @param transferDto - запрос банковской транзакции, включает в себя тип перевода,
     *                    отправителя, получателя, сумму и цель перевода
     * @return AccountTransfer в случае успешной транзакции
     * @throws TransferFailedException если по какой-либо причине банковский перевод не был выполнен
     */
    @Transactional
    @Override
    public AccountTransferDto doTransfer(AccountTransferDto transferDto) throws TransferFailedException {
        try {
            log.info("Получаем и проверяем отправителя");
            final Account sender = transferUtil.getAndCheckSender(transferDto.getAccountDetailsId(),
                    transferDto.getAmount());

            log.info("Запрашиваем получателя перевода");
            final Account recipient = searcherUtil.getAccountByAccountNumber(transferDto.getAccountNumber());

            log.info("Переводим деньги на счет получателя");
            transferUtil.moneyTransfer(sender, recipient, transferDto.getAmount());

            log.info("Отправляем обновленные данные в сервис account и проверяем успешно ли они сохранились");
            transferUtil.uploadAccounts(sender, recipient);

            log.info("Записываем данные об успешной транзакции");
            final AccountTransfer transfer = mapper.convertToEntity(transferDto);

            log.info("Фиксируем выполненную транзакцию в сервисе аудита");
            auditService.toAudit(transfer, AuditOperationType.SUCCESS_TRANSFER);

            return mapper.convertToDto(repository.save(transfer));

        } catch (Exception ex) {
            log.error("Банковский перевод не был выполнен");
            log.info("Производим rollback...");
            log.info("Фиксируем неуспешную транзакцию в сервисе аудита");
            auditService.toAudit(mapper.convertToEntity(transferDto), AuditOperationType.FAILED_TRANSFER);
            throw new TransferFailedException(ex.getMessage());
        }
    }

    /**
     * Метод, возвращающий список всех банковских переводов по номеру счета
     *
     * @return список банковских переводов по номеру счета
     */
    @Override
    public List<AccountTransferDto> getAllTransfers() {
        return repository.findAll().stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод, возвращающий экземпляр банковского перевода по номеру счета по id данного перевода
     *
     * @param id - id банковской транзакции
     * @return экземпляр банковского перевода по номеру счета
     * @throws TransferNotFoundException - если по указанному id транзакция не была найдена
     */
    @Override
    public AccountTransferDto getTransferById(Long id) throws TransferNotFoundException {
        return repository.findById(id).stream()
                .map(mapper::convertToDto)
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Банковская транзакция по номеру счета с id {} не найдена", id);
                    return new TransferNotFoundException(String.format("Transfer by id %d not found", id));
                });
    }

    /**
     * Метод, возвращающий список переводов по номеру счета, совершенных отправителем
     *
     * @param id - id отправителя
     * @return список банковских переводов по номеру счета
     * @throws AccountNotFoundException - если по указанному id отправитель не был найден
     */
    @Override
    public List<AccountTransferDto> getTransfersBySenderId(Long id) throws AccountNotFoundException {
        if (!repository.existsByAccountDetailsId(id)) {
            log.warn("Отправитель банковских транзакций по номеру счета с id {} не найден", id);
            throw new AccountNotFoundException(String.format("Sender with id %d not found", id));
        }
        return repository.findAllByAccountDetailsId(id).stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }
}
