package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;
import java.util.List;

//Controller for book operations
@SpringBootApplication
@Controller
public class bookController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookWave.Controller.Controller.class);

    private final BookRepository bookRepository;
    private final   UserRepository userRepository;

    public bookController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    //TEST

    @GetMapping("/library")
    public String library(Model model) { // add the books to the model so the View will be able to use it
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            logger.info("No books are found!");
        } else {
            for (Book book : books) {
                String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                book.setImageBase64(imageBase64); //iterating through the books and converting the bytes to images
            }
            logger.info("All books are listed!");
            model.addAttribute("books", books);
        }
        return "library";
    }

    @GetMapping("/UserLibrary")
    public String userLibrary(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtaining the currently authenticated user
        User user = userRepository.findByUsername(authentication.getName());
        long userId = user.getUserId();
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            logger.info("No books are found!");
        } else {
            for (Book book : books) {
                String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                book.setImageBase64(imageBase64); //iterating through the books and converting the bytes to images
            }
            logger.info("All books are listed!");
            model.addAttribute("userId", userId);
            model.addAttribute("books", books);
        }
        return "UserLibrary";
    }


}
