package com.bank.publicinfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Интерфейс BasicRestController - набор базовых методов для RestController.
 *
 * @param <D> - EntityDto.class
 */

@SuppressWarnings("all")
public interface BasicRestController<D> {

    /**
     * Возвращает список всех сущностей из базы данных
     *
     * @return ResponseEntity<List < D>>
     */
    ResponseEntity<List<D>> getList();

    /**
     * Находит сущность в базе данных по ID и вовзращает объект передачи данных
     *
     * @param id - ID запрашиваемой сущности
     * @return ResponseEntity<D>
     */
    ResponseEntity<D> getById(Long id);

    /**
     * Создает новую сущность в базе данных и вовзращает объект передачи данных
     *
     * @param dto - объект передачи данных
     * @param bindingResult - объект проверки ошибок валидации
     * @return ResponseEntity<D>
     */
    ResponseEntity<D> create(D dto, BindingResult bindingResult);

    /**
     * Обновляет сущность в базе данных и вовзращает объект передачи данных
     *
     * @param dto- объект передачи данных
     * @param bindingResult - объект проверки ошибок валидации
     * @return ResponseEntity<AtmDto>
     */
    ResponseEntity<D> update(D dto, BindingResult bindingResult);

    /**
     * Удаляет сущность из базы данных по ID
     *
     * @param id - ID удаляемой сущности
     * @return ResponseEntity<Void>
     */
    ResponseEntity<Void> deleteById(Long id);
}
