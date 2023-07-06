package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import com.rikerik.BookWave.Service.BookResponseDTO;
import javassist.bytecode.ByteArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final UserRepository userRepository;

    @Autowired

    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }





    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            logger.info("No users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.info("All users are listed");
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK); //return all users
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Object> getUser(@RequestParam("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("User listed");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            logger.info("User not found");
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteById(id);
        logger.info("User deleted");
        return new ResponseEntity<>("User deleted!", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> register(@RequestParam("id") Long id,
                                           @RequestParam("username") String username, //parameters for register new User
                                           @RequestParam("password") String password,
                                           @RequestParam("email") String email) {
        userRepository.save(User.builder()
                .userId(id)//using lombok builder to make the User with the given parameters
                .username(username)
                .password(password)
                .email(email)
                .build());
        logger.info("User updated!");
        return new ResponseEntity<>("User updated!", HttpStatus.CREATED);
    }
}
