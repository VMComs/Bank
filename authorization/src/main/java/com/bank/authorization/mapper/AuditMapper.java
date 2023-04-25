package com.bank.authorization.mapper;

import com.bank.authorization.dto.AuditDTO;
import com.bank.authorization.entity.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Маппер Mapstruct для сущности Audit
 * toUser - преобразует AuditDTO в Audit
 * toUserDTO - преобразует Audit в AuditDTO
 * Бизнес-логика преобразования реализуется в RestController
 */

@Mapper(componentModel = "spring")
@Component
public interface AuditMapper {

    AuditMapper MAPPER = Mappers.getMapper(AuditMapper.class);

    Audit toAudit(AuditDTO AuditDTO);
    AuditDTO toDTO(Audit audit);
    List<AuditDTO> toDTOList(List<Audit> auditDTOList);
}
