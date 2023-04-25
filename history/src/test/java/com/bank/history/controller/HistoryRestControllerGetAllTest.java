package com.bank.history.controller;

import com.bank.history.entity.History;
import com.bank.history.entity.dto.HistoryDTO;
import com.bank.history.entity.dto.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import com.bank.history.service.HistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * тестирование контроллера по получению всех записей
 */

@WebMvcTest(HistoryRestController.class)
@AutoConfigureMockMvc
class HistoryRestControllerGetAllTest {
    @MockBean
    private HistoryRepository historyRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HistoryServiceImpl historyService;
    @MockBean
    private HistoryMapper historyMapper;
      @Test
    void getAllHistoryTest() throws Exception {
        historyRepository.deleteAll();
        History history1 = new History();
        History history2 = new History();
        HistoryDTO historyDTO1 = historyMapper.toDTO(history1);
        historyRepository.save(history1);
        HistoryDTO historyDTO2 = historyMapper.toDTO(history2);
        historyRepository.save(history2);
        when(historyService.getAllHistory()).thenReturn(Arrays.asList(historyDTO1, historyDTO2));

        this.mockMvc.perform(get("/histories"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(historyService, only()).getAllHistory();
    }
}