package dev.wre.api.services;

import dev.wre.api.daos.UsersRepository;
import dev.wre.api.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepo;

    public Users addNewUser(Users user) {
        return usersRepo.save(user);
    }

    public List<Users> findAll() {
        return usersRepo.findAll();
    }

    public Users getById(int id) {
        return usersRepo.getById(id);
    }

    public Users getUserByUsername(String username) {
        return usersRepo.findByUsername(username);
    }

    public Users updateUser(Users user) {
        return usersRepo.save(user);
    }

}
