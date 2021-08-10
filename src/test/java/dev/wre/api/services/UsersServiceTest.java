package dev.wre.api.services;

import dev.wre.api.daos.UsersRepository;
import dev.wre.api.models.Users;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersServiceTest {
    @InjectMocks
    @Spy
    @Autowired
    private UsersService usersService;

    @Mock
    UsersRepository usersRepo;

    @BeforeAll
    public void setupMocks() {
        initMocks(this);
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        Users user2 = new Users("user2", "pass", "u@wre.dev", true);
        Users user3 = new Users("user3", "pass", "u@wre.dev", true);
        List<Users> spyUsers = Arrays.asList(user1, user2, user3);

        doReturn(spyUsers).when(usersRepo).findAll();
        doReturn(user1).when(usersRepo).getById(any(Integer.class));
        doReturn(user1).when(usersRepo).findByUsername(any(String.class));
        doReturn(user1).when(usersRepo).save(any(Users.class));
    }

    @Test
    public void addNewUserTest() {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        Users spyUser = spy(user1);
        assertEquals(usersService.addNewUser(spyUser), user1);
    }

    @Test
    public void findAllTest() {
        assertEquals(3, usersService.findAll().size());
    }

    @Test
    public void getById() {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        assertEquals(usersService.getById(1), user1);
    }

    @Test
    public void addUserByUsernameTest() {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        assertEquals(usersService.getUserByUsername("user1"), user1);
    }

    @Test
    public void updateUserTest() {
        Users user1 = new Users("user1", "pass", "u@wre.dev", true);
        assertEquals(usersService.updateUser(user1), user1);
    }
}