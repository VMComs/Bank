package com.bank.account.model.mapper;

import com.bank.account.model.dto.AuditDTO;
import com.bank.account.model.entity.Audit;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * В данном классе для сущности Audit переопределяются методы абстрактного класса BaseMapper
 * для конвертации DTO -> Entity  и Entity -> DTO
 */

@Component
public class AuditMapper extends BaseMapper <Audit, AuditDTO>{
    @Override
    public Audit convertToEntity(AuditDTO dto, Object... args) {
        Audit entity = new Audit();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }

    @Override
    public AuditDTO convertToDto(Audit entity, Object... args) {
        AuditDTO dto = new AuditDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }
}
