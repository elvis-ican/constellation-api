package dev.wre.api.daos;
import dev.wre.api.models.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {

    List<Bookmark> findByTag(String tag);

}
