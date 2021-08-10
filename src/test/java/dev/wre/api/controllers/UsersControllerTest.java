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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class UsersControllerTest {

    private MockMvc mockMvc;

    @MockBean
    UsersService usersService;

    @Autowired
    UsersController usersController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    public void findAllShouldReturn200() throws Exception {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        Users user2 = new Users("user2", "pass", "u@wre.dev", true);
        Users user3 = new Users("user3", "pass", "u@wre.dev", true);
        List<Users> users = Arrays.asList(user1, user2, user3);
        doReturn(users).when(usersService).findAll();
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldReturn200() throws Exception {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        doReturn(user1).when(usersService).getById(1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/get-one")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByUserNameShouldReturn200() throws Exception {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        doReturn(user1).when(usersService).getUserByUsername("user1");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/user1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserShouldReturn200() throws Exception {
        Users u = new Users("harry", "pass", "harry@wre.dev", true);
        Users u2 = new Users("harry", "newPass", "har@wre.dev", true);
        doReturn(u).when(usersService).getUserByUsername("harry");
        doReturn(u2).when(usersService).updateUser(u);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("utf-8")
                .param("username", "harry")
                .param("newPassword", "newPass")
                .param("oldPassword", "pass")
                .param("email", "har@wre.dev"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserShouldReturn200() throws Exception {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        doReturn(user1).when(usersService).getUserByUsername("user1");
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("utf-8")
                .param("username", "user1")
                .param("password", "pass"))
                .andExpect(status().isOk());
    }
}
