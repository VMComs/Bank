package com.bank.transfer.service;

import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.entity.AuditOperationType;
import com.bank.transfer.entity.AbstractTransfer;
import com.bank.transfer.exception.AuditNotFoundException;
import com.bank.transfer.exception.TransferFailedException;
import com.bank.transfer.repository.AuditRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuditServiceImp implements AuditService {
    private final AuditRepository repository;
    private final ObjectMapper mapper;

    /**
     * Метод, выполняющий аудит банковской транзакции
     * @param auditedObject объект банковской транзакции, подлежащий аудиту
     * @param type - результат выполнения банковского перевода
     * @throws TransferFailedException - если запись аудита не была выполнена
     */
    @Override
    public void toAudit(AbstractTransfer auditedObject, AuditOperationType type) throws TransferFailedException {
        try {
            final AuditEntity audit = AuditEntity.builder()
                    .entityType(auditedObject.getClass().getSimpleName())
                    .operationType(type)
                    .createdBy("Account with id " + auditedObject.getAccountDetailsId())
                    .createdAt(new Date())
                    .entityJson(mapper.writeValueAsString(auditedObject))
                    .build();
            repository.save(audit);
        } catch (JsonProcessingException exception) {
            log.warn("Не удалось создать JSON из аудируемого объекта");
            throw new TransferFailedException(exception.getMessage());
        }
    }
    /**
     * Метод, возвращающий список записей аудита по id
     * @param id технический идентификатор записи аудита
     * @return запись аудита
     */
    @Override
    public AuditEntity getAuditById(Long id) throws AuditNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Запись аудита по id {} не найдена", id);
                    return new AuditNotFoundException(String.format("Audit with id %d not found", id));
                });
    }
    /**
     * Метод, возвращающий список всех записей аудита
     * @return список записей аудита
     */
    @Override
    public List<AuditEntity> getAllAudits() {
        return repository.findAll();
    }

    /**
     * Метод, возвращающий список записей аудита по типу результата транзакций(успешная, неуспешная)
     * @param operationType тип результата выполнения транзакции
     * @return список записей аудита
     */
    @Override
    public List<AuditEntity> getAuditsByType(AuditOperationType operationType) {
        return repository.findAllByOperationType(operationType);
    }


}
