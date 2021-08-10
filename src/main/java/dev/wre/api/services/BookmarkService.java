package dev.wre.api.services;

import dev.wre.api.daos.BookmarkRepository;
import dev.wre.api.daos.UsersRepository;
import dev.wre.api.models.Bookmark;
import dev.wre.api.models.Users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UsersRepository usersRepository;
    
    public List<Bookmark> findAll() {
        return bookmarkRepository.findAll();
    }

    public List<Bookmark> findAllPublic(){
        return bookmarkRepository.findAll().stream().filter(p -> p.isSharing()).collect(Collectors.toList());
    }

    public List<Bookmark> findAllByUserId(int id) {
        Users user = usersRepository.getById(id);
        return user.getBookmarks();
    }

    public Bookmark getById(int id) {
        return bookmarkRepository.getById(id);
    }

    public Bookmark save(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public Bookmark update(int id, Bookmark bookmark) {
        return bookmarkRepository
            .findById(id)
            .map(e -> {
                e.setTitle(bookmark.getTitle());
                e.setUrl(bookmark.getUrl());
                e.setTag(bookmark.getTag());
                e.setRating(bookmark.getRating());
                e.setSharing(bookmark.isSharing());
                return bookmarkRepository.save(e);
            })
            .orElseGet(() -> {
                bookmark.setId(id);
                return bookmarkRepository.save(bookmark);
            });
    }

    public void delete(int id) {
        bookmarkRepository.delete(getById(id));
    }

}