package com.bank.publicinfo.service;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс EntityDtoMapper<E, D> - маппер для преобразования Entity из/в Dto
 *
 * @param <E> - Entity.class
 * @param <D> - EntityDto.class
 */

public interface EntityDtoMapper<E, D> {

    /**
     * Преобразует DTO в Entity указанного класса
     *
     * @param dto             - объект передачи данных
     * @return E - объект Entity указанного класса
     */
    E toEntity(D dto);

    /**
     * Преобразует Entity в DTO указанного класса
     *
     * @param entity       - объект сущности
     * @return D - объект передачи данных указанного класса
     */
    D toDto(E entity);

    /**
     * Преобразует Collection Entity в List DTO указанного класса
     *
     * @param entityCollection - коллекция объектов сущности
     * @return List<D> - List объектов передачи данных указанного класса
     */
    List<D> toDtoList(Collection<E> entityCollection);

    /**
    * @param entityClassName - имя класса сущности
    */
    void setEntityClassName(String entityClassName);

    /**
     * @param dtoClassName - имя класса объекта передачи данных
     */
    void setDtoClassName(String dtoClassName);
}
