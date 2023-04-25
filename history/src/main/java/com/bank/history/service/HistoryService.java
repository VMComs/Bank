package com.bank.history.service;

import com.bank.history.entity.dto.HistoryDTO;

import java.util.List;

/**
 * интерфейс для работы сервиса
 */

public interface HistoryService {
/**
* найти конкретный history
*/
    HistoryDTO findById(Long id);

/**
* выводит всех history
*/
    List<HistoryDTO> getAllHistory();

/**
* сохраняет новую history
*/
    HistoryDTO save(HistoryDTO historyDTO);
/**
* обновляет/редактирует history
*/
    HistoryDTO update(Long id, HistoryDTO historyDTO);
}
