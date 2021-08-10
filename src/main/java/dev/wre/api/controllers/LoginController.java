package dev.wre.api.controllers;

import dev.wre.api.models.Users;
import dev.wre.api.services.UsersService;
import dev.wre.api.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UsersService usersService;

    @PostMapping(value="/login", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> login(@RequestParam("username") String username,
                                        @RequestParam("password") String password){

        Users loginUser = usersService.getUserByUsername(username);

        if(loginUser != null && loginUser.isActive() && loginUser.getPassword().equals(password)){
            SecretKey secretKey = null;
            String token = null;
            try {
                secretKey = TokenUtil.getKeyFromPassword(username);
                token = TokenUtil.convertSecretKeyToString(secretKey);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }

            return ResponseEntity.ok().header("Authorization", token)
                    .header("Username", username)
                    .header("UserId", String.valueOf(loginUser.getId()))
                    .header("Access-Control-Expose-Headers", "Content-Type, Allow, Authorization, Username, UserId").build();
        } else {  // if not, return 401
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value="/register", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<Users> addNewUser(@RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            @RequestParam("email") String email){

        if (username.length()==0 || password.length()==0 || email.length()==0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Users registerUser = usersService.getUserByUsername(username);

            if (registerUser == null) {
                Users newUser = new Users(username, password, email);
                newUser.setActive(true);
                return new ResponseEntity<>(usersService.addNewUser(newUser), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

}
