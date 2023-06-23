package com.rikerik.BookRent;

import com.rikerik.BookRent.DAO.UserRepository;
import com.rikerik.BookRent.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
@RequestMapping("logging")
public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            return "succes";
        } else {
            return "error";
        }
    }
}
