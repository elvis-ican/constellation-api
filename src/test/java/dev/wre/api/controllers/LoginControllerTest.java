package dev.wre.api.controllers;

import dev.wre.api.models.Users;
import dev.wre.api.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class LoginControllerTest {

    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @Autowired
    LoginController loginController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void loginShouldReturnCorrectHeader() throws Exception {
        Users u = new Users("harry", "pass", "harry@wre.dev", true);
        doReturn(u).when(usersService).getUserByUsername("harry");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/login")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .characterEncoding("utf-8")
                    .param("username", "harry")
                    .param("password", "pass"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().string("username", "harry"));
    }

    @Test
    public void registerShouldReturnCorrectUser() throws Exception {
        Users u = new Users("harry", "pass", "harry@wre.dev", true);
        doReturn(u).when(usersService).getUserByUsername("harry");

        Users u2 = new Users("ron", "pass", "ron@wre.dev", true);
        doReturn(u2).when(usersService).addNewUser(u2);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("utf-8")
                .param("username", "ron")
                .param("password", "pass")
                .param("email", "ron@wre.dev"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("ron"));
    }

}
