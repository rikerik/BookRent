package com.rikerik.BookRent;

import com.rikerik.BookRent.DAO.UserRepository;
import com.rikerik.BookRent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("bookrent")

public class BookRentApplication {

    private final UserRepository userRepository;

    public BookRentApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(BookRentApplication.class, args);

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK); //return all users
    }

    @PostMapping("/CreateUser")
    public User CreateUser(User user) {
        return user; //TODO
    }


}
