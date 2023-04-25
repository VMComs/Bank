package com.bank.history.controller;

import com.bank.history.entity.History;
import com.bank.history.entity.dto.HistoryDTO;
import com.bank.history.entity.dto.HistoryMapper;

import com.bank.history.repository.HistoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * класс тестирования контроллеров
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HistoryRestControllerSecondTest {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private ObjectMapper objectMapper;      // обьект для перевода запроса в json

    /**
     * тест на получение по id.
     * может @throws Exception в случае неудачной проверки
     */
    @Test
    void findHistoryByIdControllerTest() throws Exception {
        History history = new History();
        historyRepository.save(history);
        HistoryDTO historyDTO = historyMapper.toDTO(history);
        Long id = historyDTO.getId();

        mockMvc.perform(get("/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id));
    }

    /**
     * тест на действия контроллера по сохранению
     * при неудаче - @throws Exception
     */
    @Test
    void saveHistoryController() throws Exception {
        History history = new History();
        history.setId(108L);
        history.setAccountAuditId(108L);
        HistoryDTO historyDTO = historyMapper.toDTO(history);

        mockMvc.perform(post("/newHistory").content(objectMapper.writeValueAsString(historyDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(108L))
                .andExpect(jsonPath("$.accountAuditId").value(108L));
    }

    /**
     * тест контроллера на редактирование
     * может @throws Exception
     */
    @Test
    void updateHistoryControllerTest() throws Exception {
        History history = new History();
        history.setBankInfoAuditId(400L);
        history.setAccountAuditId(400L);
        historyRepository.save(history);
        Long id = history.getId();
        History history1 = new History();
        history1.setBankInfoAuditId(50L);
        history1.setAccountAuditId(50L);
        HistoryDTO historyDTO = historyMapper.toDTO(history1);

        mockMvc.perform(put("/update/{id}", id).content(objectMapper.writeValueAsString(historyDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankInfoAuditId").value(50L))
                .andExpect(jsonPath("$.accountAuditId").value(50L));
    }

    /**
     * тест контроллера на получение всех сущностей
     * может @throws Exception
     */
    @Test
    void getAllControllerTest() throws Exception {
        HistoryDTO historyDTO = new HistoryDTO();
        List<HistoryDTO> list = List.of(historyDTO);
        mockMvc.perform(get("/histories").content(objectMapper.writeValueAsString(list)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
