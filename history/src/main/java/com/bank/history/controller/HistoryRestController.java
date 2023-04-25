package com.bank.history.controller;

import com.bank.history.entity.dto.HistoryDTO;
import com.bank.history.service.HistoryService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * класс контроллера, принимает запросы и возвращает согласно запросу или данные, или делает обновление данных
 */
@Slf4j
@RestController
public class HistoryRestController {
    /**
     * работа класса с сервисным слоем
     */
    private final HistoryService historyService;

    /**
     * внедрение зависимости через конструктор
     */
    public HistoryRestController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * GET запрос, настроенный по URI, выводит все записи historyDTO
     */
    @Timed(value = "all")
    @GetMapping(path = "/histories")
    public ResponseEntity<List<HistoryDTO>> getAllHistory() {
        log.info("Method getAllHistory() is calling from RestController {}", this.getClass());
        return ResponseEntity.ok(historyService.getAllHistory());
    }

    /**
     * POST запрос с данными новой history добавит ее в БД (ДТО перейдет в entity)
     */
    @Timed(value = "new")
    @PostMapping(path = "/newHistory")
    public ResponseEntity<HistoryDTO> saveHistory(@RequestBody HistoryDTO historyDTO) {
        log.info("Method saveHistory() is calling from RestController {}", this.getClass());
        return ResponseEntity.ok(historyService.save(historyDTO));
    }

    /**
     * GET запрос для получения history по id
     */
    @Timed(value = "findID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<HistoryDTO> findHistoryById(@PathVariable("id") Long id) {
        log.info("Method findHistoryById() by id={} is calling from RestController {} ", id, this.getClass());
        return ResponseEntity.ok(historyService.findById(id));
    }

    /**
     * запрос для изменения существующей записи history
     */
    @Timed(value = "update")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<HistoryDTO> updateHistory(@PathVariable("id") Long id, @RequestBody HistoryDTO historyDTO) {
        log.info("Method updateHistory() by id={} is calling from RestController {} ", id, this.getClass());
        return ResponseEntity.ok(historyService.update(id, historyDTO));
    }
}
