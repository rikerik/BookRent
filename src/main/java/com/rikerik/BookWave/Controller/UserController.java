package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.Model.User;
import com.rikerik.BookWave.Service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class UserController {

    private UserService userService;

    /**
     * Constructs a new UserController with the given UserService.
     *
     * @param userService the UserService to be used
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userRemover")
    public String removeUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        if (id.equals(2L)) {
            redirectAttributes.addFlashAttribute("message", "Cannot remove admin user with ID 2.");
        } else {
            if (userService.existsUserById(id)) {
                try {
                    userService.removeUserById(id);
                    redirectAttributes.addFlashAttribute("message", "User removed successfully with ID: " + id);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("message", "Error removing user: " + e.getMessage());
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "User with ID " + id + " does not exist.");
            }
        }
        // Redirect to getUsers to refresh the user list after removal
        return "redirect:/userRemove";
    }

    @GetMapping("/userRemove")
    public String getUsers(Model model, RedirectAttributes redirectAttributes) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        log.debug("Number of users: {}", users.size());
        return "userRemove";
    }

}