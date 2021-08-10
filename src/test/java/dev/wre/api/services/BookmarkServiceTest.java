package dev.wre.api.services;

import dev.wre.api.daos.BookmarkRepository;
import dev.wre.api.daos.UsersRepository;
import dev.wre.api.models.Bookmark;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookmarkServiceTest {
    @InjectMocks
    @Spy
    @Autowired
    private BookmarkService bookmarkService;

    @Mock
    BookmarkRepository bkRepo;

    @Mock
    UsersRepository userRepo;

    @BeforeAll
    public void setupMocks() {
        initMocks(this);
        Bookmark bk1 = new Bookmark("Spring", "spring.io", "Java", 5, true);
        Bookmark bk2 = new Bookmark("Java", "java.org", "Java", 5, true);
        Bookmark bk3 = new Bookmark("CSS", "css.org", "CSS", 5, true);
        List<Bookmark> spyBookmarks = Arrays.asList(bk1, bk2, bk3);

        doReturn(spyBookmarks).when(bkRepo).findAll();
        doReturn(bk1).when(bkRepo).getById(any(Integer.class));
        doReturn(bk1).when(bkRepo).save(any(Bookmark.class));
        doNothing().when(bkRepo).delete(any(Bookmark.class));

        Users user = new Users("user", "pass", "u@wre.dev", true);
        user.setBookmarks(spyBookmarks);
        doReturn(user).when(userRepo).getById(any(Integer.class));
    }

    @Test
    public void findAllTest() {
        assertEquals(3, bookmarkService.findAll().size());
    }

    @Test
    public void findAllPublicTest() {
        assertEquals(3, bookmarkService.findAllPublic().size());
    }

    @Test
    public void findAllByUserIdTest() {
        assertEquals(bookmarkService.findAllByUserId(1).size(), 3);
    }

    @Test
    public void getByIdTest() {
        assertEquals(bookmarkService.getById(1).getTitle(), "Spring");
    }

    @Test
    public void saveTest() {
        Bookmark bk = new Bookmark("Spring", "spring.io", "Java", 5, true);
        Bookmark spyBookmark = spy(bk);
        assertEquals(bookmarkService.save(spyBookmark), bk);
    }

    @Test
    public void updateTest() {
        Bookmark bk = new Bookmark("Spring", "spring.io", "Java", 5, true);
        Bookmark spyBookmark = spy(bk);
        assertEquals(bookmarkService.update(1, spyBookmark), bk);
    }
}
