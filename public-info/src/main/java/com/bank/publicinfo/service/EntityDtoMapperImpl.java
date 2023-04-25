package com.bank.publicinfo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс EntityDtoMapperImpl<E, D> - имплментация маппера для преобразования Entity из/в Dto
 *
 * @param <E> - Entity.class
 * @param <D> - EntityDto.class
 */

@Slf4j
@Service
@Scope(value = "prototype")
public class EntityDtoMapperImpl<E, D> implements EntityDtoMapper<E, D> {

    private String entityClassName;
    private String dtoClassName;

    @SuppressWarnings(value = "all")
    @Override
    public E toEntity(D dto) {

        log.debug("Вызов метода toEntity() |DTO = " + dto + "| в сервисе " + this.getClass() + ". Класс сущности: " + entityClassName);

        E entity;
        try {
            Class<?> entityClass = Class.forName(entityClassName);
            entity = (E) entityClass.getDeclaredConstructor().newInstance();
            if (dto != null) {
                BeanUtils.copyProperties(dto, entity);

                log.debug("Вызов метода BeanUtils.copyProperties(dto, entity) в сервисе " + this.getClass() + ". Entity: " + entity);
            }
        } catch (ClassNotFoundException | InvocationTargetException |
                 IllegalAccessException | NoSuchMethodException | InstantiationException e) {

            log.error(this.getClass() + ": Ошибка в работе метода toEntity() " + e);

            throw new RuntimeException(e);
        }
        return entity;
    }

    @SuppressWarnings(value = "all")
    @Override
    public D toDto(E entity) {

        log.debug("Вызов метода toDto() |Entity = " + entity + "| в сервисе " + this.getClass() + ". Класс DTO: " + dtoClassName);

        D dto;
        try {
            Class<?> dtoClass = Class.forName(dtoClassName);
            dto = (D) dtoClass.getDeclaredConstructor().newInstance();
            if (entity != null) {
                BeanUtils.copyProperties(entity, dto);

                log.debug("Вызов метода BeanUtils.copyProperties(entity, dto) в сервисе " + this.getClass() + ". DTO: " + dto);
            }
        } catch (ClassNotFoundException | InvocationTargetException |
                 IllegalAccessException | NoSuchMethodException | InstantiationException e) {

            log.error(this.getClass() + ": Ошибка в работе метода toDto() " + e);

            throw new RuntimeException(e);
        }
        return dto;
    }

    @Override
    public List<D> toDtoList(Collection<E> entityCollection) {

        log.debug("Вызов метода toDtoList() в сервисе " + this.getClass());

        return entityCollection.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    @Override
    public void setDtoClassName(String dtoClassName) {
        this.dtoClassName = dtoClassName;
    }
}
