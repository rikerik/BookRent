package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Roles;
import com.rikerik.BookWave.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//Controller for login and registration
@SpringBootApplication
@Controller
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
    private final UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserAuthController(UserRepository userRepository) {
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

    @Transactional //FOR TESTING REGISTER METHOD WITH PRE-FILLED DB
    public void resetSequence() {
        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM users", Long.class);
        if (maxId != null) {
            jdbcTemplate.execute("ALTER SEQUENCE ID_SEQ RESTART WITH " + (maxId + 1)); //alter the sequence so it wont start with one
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("email") String email,
                           RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (userRepository.findByUsername(username) != null) {
            redirectAttributes.addAttribute("error", "exists");
            return "register";
        }

        resetSequence(); // Reset the sequence before saving the new user

        userRepository.save(User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .Role(Roles.USER)
                .build());

        logger.info("User registered!");
        redirectAttributes.addAttribute("success", "true");
        return "login";
    }

    @PostMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        // Retrieves the Authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Checks if there is an authenticated user
        if (authentication != null) {
            // Creates a new instance of SecurityContextLogoutHandler and calls its logout method
            // Clears the SecurityContext associated with the current user's session
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
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
