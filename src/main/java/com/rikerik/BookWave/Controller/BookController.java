package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@Controller
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookWave.Controller.Controller.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    private JdbcOperations jdbcTemplate;


    public BookController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional //FOR TESTING REGISTER METHOD WITH PRE-FILLED DB
    public void resetSequence() {
        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(book_id) FROM books", Long.class);
        if (maxId != null) {
            jdbcTemplate.execute("ALTER SEQUENCE BOOK_ID_SEQ RESTART WITH " + (maxId + 1)); //alter the sequence so it wont start with one
        }
    }

    @PostMapping("/registerBook")
    public String registerBook(@RequestParam("authorName") String authorName,
                               @RequestParam("title") String title,
                               @RequestParam("description") MultipartFile descriptionFile, //Multipart file to work with uploaded txt and image
                               @RequestParam("image") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes) {
        if (bookRepository.findBookByTitle(title) != null) {
            redirectAttributes.addAttribute("error", "exists");
        } else {
            User admin = userRepository.findByUsername("Admin"); // to find the admin
            try {
                String descriptionText = new String(descriptionFile.getBytes(), StandardCharsets.UTF_8); //Construct a String from the bytes of the uploaded txt

                resetSequence(); // Reset the sequence before adding a new book

                bookRepository.save(Book.builder() // save the book
                        .authorName(authorName)
                        .title(title)
                        .description(descriptionText)
                        .imageByte(imageFile.getBytes())
                        .isRented(false)
                        .user(admin)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "BookAddingPage";
    }

    @GetMapping("/BookAddingPage")
    public String register() {
        return "BookAddingPage";
    }


    @GetMapping("/allBooks")
    public String allBooks() {
        return "allBooks";
    }
}
