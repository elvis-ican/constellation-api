package dev.wre.api.controllers;

import dev.wre.api.models.Bookmark;
import dev.wre.api.models.Users;
import dev.wre.api.services.BookmarkService;
import dev.wre.api.services.UsersService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<Bookmark>> findAll(
            @RequestHeader(value = "UserId", required = false) Integer id
    ) {
        if (id!=null) {
            return new ResponseEntity<>(
                    bookmarkService.findAllByUserId(id),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    bookmarkService.findAll(),
                    HttpStatus.OK
            );
        }
    }

    @GetMapping(value="/public")
    public ResponseEntity<List<Bookmark>> findAllPublic(){
        return new ResponseEntity<>(
                bookmarkService.findAllPublic(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> getById(@PathVariable("id") int id) {
        return new ResponseEntity<>(
                bookmarkService.getById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Bookmark> save(
            @RequestBody(required = true) Bookmark bookmark,
            @RequestHeader(value = "UserId", required = true) Integer id) {
        bookmarkService.save(bookmark);
        Users owner = usersService.getById(id);
        owner.getBookmarks().add(bookmark);
        usersService.updateUser(owner);

        return new ResponseEntity<>(bookmark, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> update(
            @PathVariable("id") int id,
            @RequestBody Bookmark bookmark) {
        bookmark.setId(id);
        return new ResponseEntity<>(bookmarkService.save(bookmark), HttpStatus.OK);
    }

    @DeleteMapping("/{bkId}")
    public ResponseEntity<String> delete(
            @PathVariable("bkId") int bkId,
            @RequestHeader("UserId") int userId) {

        Users user = usersService.getById(userId);
        List<Bookmark> bookmarks = user.getBookmarks();
        System.out.println(bookmarks.size());
        Bookmark bookmark = bookmarkService.getById(bkId);
        if (bookmarks.contains(bookmark)) {
            bookmarks.remove(bookmark);
            user.setBookmarks(bookmarks);
            usersService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}