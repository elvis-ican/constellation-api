package dev.wre.api.daos;

import dev.wre.api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);

}
