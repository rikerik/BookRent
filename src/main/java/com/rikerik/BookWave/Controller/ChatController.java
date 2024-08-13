package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.ChatMessage;
import com.rikerik.BookWave.Model.User;
import com.rikerik.BookWave.Service.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChatController {

    private final UserRepository userRepository;
    private CustomUserDetailsService userService;

    /**
     * Constructs a new ChatController with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used
     */
    public ChatController(UserRepository userRepository, CustomUserDetailsService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Registers a user for the "scifi" chat topic.
     *
     * @param chatMessage    the ChatMessage payload
     * @param headerAccessor the SimpMessageHeaderAccessor
     * @return the registered ChatMessage
     */
    @MessageMapping("/scifi/register")
    @SendTo("/topic/scifi")
    public ChatMessage registerForScifi(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    /**
     * Sends a message to the "scifi" chat topic.
     *
     * @param chatMessage the ChatMessage payload
     * @return the sent ChatMessage
     */
    @MessageMapping("/scifi/send")
    @SendTo("/topic/scifi")
    public ChatMessage sendScifiMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    /**
     * Leaves the "scifi" chat topic.
     *
     * @param chatMessage the ChatMessage payload
     * @return the ChatMessage indicating leaving the chat
     */
    @MessageMapping("/scifi/leave")
    @SendTo("/topic/scifi")
    public ChatMessage leaveScifiChat(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    /**
     * Registers a user for the "fantasy" chat topic.
     *
     * @param chatMessage    the ChatMessage payload
     * @param headerAccessor the SimpMessageHeaderAccessor
     * @return the registered ChatMessage
     */
    @MessageMapping("/fantasy/register")
    @SendTo("/topic/fantasy")
    public ChatMessage registerForFantasy(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    /**
     * Sends a message to the "fantasy" chat topic.
     *
     * @param chatMessage the ChatMessage payload
     * @return the sent ChatMessage
     */
    @MessageMapping("/fantasy/send")
    @SendTo("/topic/fantasy")
    public ChatMessage sendFantasyMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    /**
     * Leaves the "fantasy" chat topic.
     *
     * @param chatMessage the ChatMessage payload
     * @return the ChatMessage indicating leaving the chat
     */
    @MessageMapping("/fantasy/leave")
    @SendTo("/topic/fantasy")
    public ChatMessage leaveFantasyChat(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    /**
     * Handles the GET request for the "/chatScifi" endpoint.
     *
     * @param model the Model object
     * @return the view name for the "scifi" chat page or a redirect to the library
     *         page
     */
    @GetMapping("/chatScifi")
    public String scifiIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        List<Book> userBooks = userService.getUserBooks(user);

        if (!userService.hasEnoughScifiBooks(userBooks)) {
            // Redirect or handle the case where the user doesn't have enough scifi books
            return "redirect:/bookSearch";
        }

        String username = auth.getName();
        model.addAttribute("username", username);
        model.addAttribute("topic", "scifi");
        return "chatScifi";
    }

    /**
     * Handles the GET request for the "/chatFantasy" endpoint.
     *
     * @param model the Model object
     * @return the view name for the "fantasy" chat page or a redirect to the
     *         library page
     */
    @GetMapping("/chatFantasy")
    public String fantasyIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        List<Book> userBooks = userService.getUserBooks(user);

        if (!userService.hasEnoughFantasyBooks(userBooks)) {
            // Redirect or handle the case where the user doesn't have enough fantasy books
            return "redirect:/bookSearch";
        }

        String username = auth.getName();
        model.addAttribute("username", username);
        model.addAttribute("topic", "fantasy");

        return "chatFantasy";
    }
}
