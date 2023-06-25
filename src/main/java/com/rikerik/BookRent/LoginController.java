package com.rikerik.BookRent;

import com.rikerik.BookRent.DAO.UserRepository;
import com.rikerik.BookRent.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
@RequestMapping("welcome")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookRent.Controller.class);
    private final UserRepository userRepository;


    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        return "index";
    }

    @RequestMapping("/loginTest")
    public String showLoginPage() {
        return "login";
    }

    //handling login request
    //The Model object is used to pass data from controller to the view, so I can add attributes
    //to the model which will be rendered on the html page by Thymeleaf
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = userRepository.findByUsername(username); // Retrieve the user from the repository by the given parameter
        if (user != null && user.getPassword().equals(password)) {     // Check if the user exists and the password matches
            model.addAttribute("username", username); // Add the username attribute to the model
            logger.info("login succesful!");
            return "succes";
        } else {
            logger.info("login was not succesful!");
            return "error";
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestParam("username") String username, //parameters for register new User
                                           @RequestParam("password") String password,
                                           @RequestParam("email") String email) {
        userRepository.save(User.builder()  //using lombok builder to make the User with the given parameters
                .username(username)
                .password(password)
                .email(email)
                .build());
        logger.info("User registered!");
        return new ResponseEntity<>("User registered!", HttpStatus.CREATED);
    }
}
