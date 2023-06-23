package com.rikerik.BookRent;

import com.rikerik.BookRent.DAO.UserRepository;
import com.rikerik.BookRent.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final UserRepository userRepository;

    @Autowired

    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {

        SpringApplication.run(Controller.class, args);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("All users are listed");
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK); //return all users
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

    @PostMapping("/CreateUser")
    public User CreateUser(User user) {
        return user; //TODO
    }


}
