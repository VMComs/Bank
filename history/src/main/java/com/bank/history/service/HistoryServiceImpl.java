package com.bank.history.service;

import com.bank.history.entity.dto.HistoryMapper;
import com.bank.history.entity.History;
import com.bank.history.entity.dto.HistoryDTO;

import com.bank.history.exception.HistoryNotFoundException;
import com.bank.history.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * класс сервиса, реализующий методы сервиса с использованием исключения
 */
@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    /**
     * внедерение зависимости через конструктор
     */
    public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    /**
     * метод выводит список всех history из БД
     */

    @Override
    public List<HistoryDTO> getAllHistory() {
        log.debug("getAllHistory() method is calling from {}  ", this.getClass() );
        return historyRepository.findAll().stream().map(historyMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * метод делает сохранение в БД ентити из дто
     */

    @Override
    public HistoryDTO save(HistoryDTO historyDTO) {
        log.debug("method save() is calling from {} ", this.getClass());
        final History history = historyMapper.toEntity(historyDTO);
        historyRepository.save(history);
        return historyMapper.toDTO(history);
    }

    /**
     * найти запись history по id или выбросить исключение
     */
    @Override
    public HistoryDTO findById(Long id) {
        log.debug("method findById() by id={} is calling from  ", id, this.getClass());
        final History history = historyRepository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException("History with " + id + " id not found"));
        final HistoryDTO historyDTO = historyMapper.toDTO(history);
        return historyDTO;
    }

    /**
     * сделать изменения в history или выбросить исключение , если такой history не существует
     */

    @Override
    public HistoryDTO update(Long id, HistoryDTO historyDTO) {
        log.debug("method update() by id={} is calling from  ", id, this.getClass());
        historyRepository.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException("History with id=" + id + " not found"));
        final History historyEntity = historyMapper.toEntity(historyDTO);
        historyEntity.setId(id);
        final History history = historyRepository.save(historyEntity);
        return historyMapper.toDTO(history);
    }
}
