package com.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BasicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllCourse() throws Exception {
        this.mockMvc.perform(get("/api/v1/employee/export")).andExpect(status().isOk()).andReturn();
    }

}
