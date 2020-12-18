package com.tgcoding.mealscalendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgcoding.mealscalendar.model.User;
import com.tgcoding.mealscalendar.service.UserService;
import com.tgcoding.mealscalendar.setup.security.WithMockCustomUser;
import com.tgcoding.mealscalendar.setup.security.WithMockCustomUserSecurityContextFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static final Long USER_ID = WithMockCustomUserSecurityContextFactory.USER_ID;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver()).build();

        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockCustomUser
    public void meCallsService() throws Exception {
        User user = new User();

        String userResponseJson = objectMapper.writeValueAsString(user);

        when(userService.findById(anyLong()))
                .thenReturn(user);

        mockMvc.perform(get("/user/me"))
                .andExpect(status().isOk())
                .andExpect(content().json(userResponseJson));

        verify(userService, times(1)).findById(USER_ID);
    }


}
