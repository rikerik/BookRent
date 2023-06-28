package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootApplication
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookWave.Controller.Controller.class);
    private final UserRepository userRepository;


    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //handling login request
    //The Model object is used to pass data from controller to the view, so I can add attributes
    //to the model which will be rendered on the html page by Thymeleaf
    @PostMapping("/login")
    public String login(RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = userRepository.findByUsername(username); // Retrieve the user from the repository by the given parameter
        if (user != null && user.getPassword().equals(password)) {     // Check if the user exists and the password matches
            model.addAttribute("username", username); // Add the username attribute to the model
            logger.info("login succesful!");
            redirectAttributes.addAttribute("succes", "true");
            return "succes";
        } else {
            redirectAttributes.addAttribute("error", "exists");
            return "error";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username, //parameters for register new User
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (userRepository.findByUsername(username) != null) {
            redirectAttributes.addAttribute("error", "exists");
            return "register";
        }
        userRepository.save(User.builder()  //using lombok builder to make the User with the given parameters
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .build());
        logger.info("User registered!");
        redirectAttributes.addAttribute("succes", "true");
        return "login";
    }

    //Views
    @GetMapping("/") //to show the login form with get
    public String index() {
        return "index";
    }

    @GetMapping("/login") //to show the login form with get
    public String login() {
        return "login";
    }

    @GetMapping("/register") //to show the register form with get
    public String register() {
        return "register";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }
}
