package com.example.opentalk.controller;

import com.example.opentalk.Security.JwtToken;
import com.example.opentalk.dto.OpentalkDTo;
import com.example.opentalk.mapper.OpentalkMapper;
import com.example.opentalk.model.Status;
import com.example.opentalk.repository.OpentalkRepository;
import com.example.opentalk.service.AuthService;
import com.example.opentalk.service.OpentalkService;
import com.example.opentalk.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static java.util.Arrays.asList;

@WebMvcTest(controllers = OpentalkController.class)
public class OpentalkControllerTest {
    @MockBean
    private OpentalkService opentalkService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtToken jwtToken;

    @Test
    public void checkResponse() throws Exception {
        OpentalkDTo opentalkDTo = new OpentalkDTo(1L, "god of war", "KhanhTran", "2012-04-23", "hello", "hà nội");
        OpentalkDTo opentalkDTo2 = new OpentalkDTo(2L, "god of war 2", "KhanhTran2", "2012-04-23", "hello", "hà nội");

        Mockito.when(opentalkService.getAll()).thenReturn(asList(opentalkDTo, opentalkDTo2));

        mockMvc.perform(get("/opentalk/all"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("god of war")))
                .andExpect(jsonPath("$[0].employeeName", Matchers.is("KhanhTran")))
                .andExpect(jsonPath("$[1].employeeName", Matchers.is("KhanhTran2")))
                .andExpect(jsonPath("$[1].name", Matchers.is("god of war 2")));
    }
}
