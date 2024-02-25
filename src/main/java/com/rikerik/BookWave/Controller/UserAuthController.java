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


/**
 * Controller class for handling user authentication related operations.
 */
@SpringBootApplication
@Controller
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
    private final UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor for UserAuthController.
     * @param userRepository the repository for user data access
     */
    @Autowired
    public UserAuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles the login request.
     * @param redirectAttributes the redirect attributes
     * @param username the username parameter
     * @param password the password parameter
     * @param model the model
     * @return the view name for successful login or error
     */
    @PostMapping("/login")
    public String login(RedirectAttributes redirectAttributes, @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        User user = userRepository.findByUsername(username); 
        if (user != null && user.getPassword().equals(password)) {     
            model.addAttribute("username", username); 
            logger.info("login succesful!");
            redirectAttributes.addAttribute("succes", "true");
            return "succes";
        } else {
            redirectAttributes.addAttribute("error", "exists");
            return "error";
        }
    }

    /**
     * Resets the sequence for testing the register method with pre-filled database.
     */
    @Transactional
    public void resetSequence() {
        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM users", Long.class);
        if (maxId != null) {
            jdbcTemplate.execute("ALTER SEQUENCE ID_SEQ RESTART WITH " + (maxId + 1));
        }
    }

    /**
     * Handles the register request.
     * @param username the username parameter
     * @param password the password parameter
     * @param email the email parameter
     * @param redirectAttributes the redirect attributes
     * @return the view name for successful registration or error
     */
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

        resetSequence();

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

    /**
     * Handles the logout request.
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @return the redirect view name
     */
    @PostMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }

    /**
     * Handles the index page request.
     * @return the view name for the index page
     */
    @GetMapping("/") 
    public String index() {
        return "index";
    }

    /**
     * Handles the login page request.
     * @return the view name for the login page
     */
    @GetMapping("/login") 
    public String login() {
        return "login";
    }

    /**
     * Handles the register page request.
     * @return the view name for the register page
     */
    @GetMapping("/register") 
    public String register() {
        return "register";
    }

    /**
     * Handles the login failure page request.
     * @return the view name for the login failure page
     */
    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }
}
