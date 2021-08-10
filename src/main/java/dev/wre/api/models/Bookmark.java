package dev.wre.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bk_id")
    private int id;

    private String title;

    private String url;

    private String tag;

    @Column(name = "rating", columnDefinition = "int default 0")
    private int rating;

    @Column(name = "sharing", columnDefinition = "boolean default false")
    private boolean sharing;

    @JsonIgnore
    @ManyToMany(mappedBy = "bookmarks")
    private List<Users> users;

    public Bookmark() {
    }

    public Bookmark(String title, String url, String tag) {
        this.title = title;
        this.url = url;
        this.tag = tag;
    }

    public Bookmark(String title, String url, String tag, int rating, boolean sharing) {
        this.title = title;
        this.url = url;
        this.tag = tag;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isSharing() {
        return sharing;
    }

    public void setSharing(boolean sharing) {
        this.sharing = sharing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Bookmark [id=" + id + ", rating=" + rating + ", sharing=" + sharing + ", tag=" + tag + ", title="
                + title + ", url=" + url + ", users=" + users + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + rating;
        result = prime * result + (sharing ? 1231 : 1237);
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((users == null) ? 0 : users.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bookmark other = (Bookmark) obj;
        if (id != other.id)
            return false;
        if (rating != other.rating)
            return false;
        if (sharing != other.sharing)
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (users == null) {
            if (other.users != null)
                return false;
        } else if (!users.equals(other.users))
            return false;
        return true;
    }

    

}
