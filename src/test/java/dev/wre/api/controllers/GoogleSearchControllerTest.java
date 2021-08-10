package dev.wre.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GoogleSearchControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    GoogleSearchController googleSearchController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(googleSearchController).build();
    }

    @Test
    public void getGoogleSearchShouldReturn200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/googlesearch/dog"))
                .andExpect(status().isOk());
    }

}
