package dev.wre.api.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Component
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "user_bookmark",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bk_id")
    )
    private List<Bookmark> bookmarks;

    public Users() {
    }

    public Users(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Users(String username, String password, String email, boolean isActive){
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users user = (Users) o;
        return id == user.id && username.equals(user.username) && password.equals(user.password) && email.equals(user.email) && Objects.equals(bookmarks, user.bookmarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, bookmarks);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", bookmarks=" + bookmarks +
                '}';
    }
}
