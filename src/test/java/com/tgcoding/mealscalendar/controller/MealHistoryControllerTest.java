package com.tgcoding.mealscalendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgcoding.mealscalendar.model.MealHistory;
import com.tgcoding.mealscalendar.service.MealHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class MealHistoryControllerTest {

    @Mock
    MealHistoryService mealHistoryService;

    @InjectMocks
    MealHistoryController mealHistoryController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mealHistoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllCallsService() throws Exception {
        Iterable<MealHistory> historyList = new ArrayList<>();

        String foodResponseJson = objectMapper.writeValueAsString(historyList);

        when(mealHistoryService.getAll())
                .thenReturn(historyList);

        mockMvc.perform(get("/mealhistory/"))
                .andExpect(status().isOk())
                .andExpect(content().json(foodResponseJson));

        verify(mealHistoryService, times(1)).getAll();
    }
}
