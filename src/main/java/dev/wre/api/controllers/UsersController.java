package dev.wre.api.controllers;

import dev.wre.api.models.Users;
import dev.wre.api.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<Users>> findAll() {
        return new ResponseEntity<>(usersService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get-one")
    public ResponseEntity<Users> getById(@RequestParam(value="id") int id) {
        return new ResponseEntity<>(
                usersService.getById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/{username}")
    public Users getUserByUserName(@PathVariable("username") String username) {
        return usersService.getUserByUsername(username);
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<Users> getUserByUserName2(@PathVariable("username") String username) {
//        return new ResponseEntity<>(usersService.getUserByUsername(username),
//                HttpStatus.OK);
//    }

    @PutMapping(value = "/update", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> updateUser(@RequestParam("username") String username,
                                             @RequestParam("newPassword") String newPassword,
                                             @RequestParam("oldPassword") String oldPassword,
                                             @RequestParam("email") String email) {

        Users user = usersService.getUserByUsername(username);
        if (user != null && oldPassword.equals(user.getPassword())) {
            if (newPassword.length() != 0) user.setPassword(newPassword);
            if (email.length() != 0) user.setEmail(email);
            usersService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/delete", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> deleteUser(@RequestParam("username") String username,
                                             @RequestParam("password") String password) {
        Users user = usersService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setActive(false);
            usersService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}