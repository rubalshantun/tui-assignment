package com.tui.assignment;

import com.tui.assignment.controller.APIEndPoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TUIAssignmentIntegrationTests {

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void initTests() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void getSuccessfulEndPointResponse() throws Exception {
        mvc.perform(get("/api/v1/user-github-repos-info?username=rubal")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserNotFoundResponse() throws Exception {
        mvc.perform(get("/api/v1/user-github-repos-info?username=rubalt")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getResponse() throws Exception {
        mvc.perform(get("/api/v1/user-github-repos-info?username=rubal")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    void contextLoads() {
    }

}
