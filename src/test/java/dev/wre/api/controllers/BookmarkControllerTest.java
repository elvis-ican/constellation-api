package dev.wre.api.controllers;

import dev.wre.api.models.Bookmark;
import dev.wre.api.models.Users;
import dev.wre.api.services.BookmarkService;
import dev.wre.api.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookmarkControllerTest {

    private MockMvc mockMvc;

    @MockBean
    BookmarkService bookmarkService;

    @MockBean
    UsersService usersService;

    @Autowired
    BookmarkController bookmarkController;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController).build();
    }

    @Test
    public void findAllShouldReturns200() throws Exception {
        Bookmark bk1 = new Bookmark("Spring", "spring.io", "Java", 5, true);
        Bookmark bk2 = new Bookmark("Java", "java.org", "Java", 5, true);
        Bookmark bk3 = new Bookmark("CSS", "css.org", "CSS", 5, true);
        List<Bookmark> bookmarks = Arrays.asList(bk1, bk2, bk3);
        Users user = new Users("user", "pass", "u@wre.dev", true);
        user.setBookmarks(bookmarks);
        doReturn(user).when(usersService).getById(any(Integer.class));
        doReturn(bookmarks).when(bookmarkService).findAll();

        this.mockMvc.perform(get("/bookmarks"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllPublicShouldReturns200() throws Exception {
        Bookmark bk1 = new Bookmark("Spring", "spring.io", "Java", 5, true);
        Bookmark bk2 = new Bookmark("Java", "java.org", "Java", 5, true);
        Bookmark bk3 = new Bookmark("CSS", "css.org", "CSS", 5, true);
        List<Bookmark> bookmarks = Arrays.asList(bk1, bk2, bk3);
        doReturn(bookmarks).when(bookmarkService).findAll();

        this.mockMvc.perform(get("/bookmarks/public"))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldReturns200() throws Exception {
        Bookmark bk1 = new Bookmark("Spring", "spring.io", "Java", 5, true);
        doReturn(bk1).when(bookmarkService).getById(1);
        this.mockMvc.perform(get("/bookmarks/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturns200() throws Exception {
        Bookmark bk = new Bookmark("Spring", "spring.io", "Java", 5, true);
        doReturn(bk).when(bookmarkService).save(bk);
        this.mockMvc.perform(put("/bookmarks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{ \"title\":\"Spring\", \"url\":\"spring.io\", \"tag\": \"Java\"," +
                        "\"rating\": 5,\"sharing\": true}"))
            .andExpect(status().isOk());
    }

}
