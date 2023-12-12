package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootApplication
@Controller
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


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
                               @RequestParam("genre") String genre,
                               @RequestParam("labels") String labels,
                               @RequestParam("amount") Long amount,
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
                        .genre(genre)
                        .labels(labels)
                        .bookAmount(amount)
                        .description(descriptionText)
                        .imageByte(imageFile.getBytes())
                        .users(Collections.singleton(admin))
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "BookAddingPage";
    }

    @GetMapping("/listBooks")
    public String Books(Model model) { // add the books to the model so the View will be able to use it
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
        return "allBooks";
    }
    @GetMapping("/BookAddingPage")
    public String register() {
        return "BookAddingPage";
    }

    @GetMapping("/allBooks")
    public String allBooks() {
        return "allBooks";
    }


    //search for books, almost the same as list books

    // Helper method to convert a Book to a list

    @GetMapping("/bookSearch")
    public String bookSearch(@RequestParam(value = "attribute", defaultValue = "genre") String attribute,
                             @RequestParam(value = "searchValue", required = false) String searchValue,
                             Model model) {
        List<Book> books;

        if (searchValue != null && !searchValue.isEmpty()) {
            // Alakítsd az összes karaktert kisbetűvé a címeknél és íróknál
            String searchValueLowerCase = searchValue.toLowerCase();

            switch (attribute) {
                case "author" -> {
                    books = bookRepository.findByAuthorNameILike(searchValueLowerCase);
                }
                case "title" -> {
                    books = bookRepository.findByTitleILike(searchValueLowerCase);
                }
                default -> {
                    books = bookRepository.findByGenreILike(searchValueLowerCase);
                }
            };
        } else {
            books = bookRepository.findAll();
        }

        int numberOfBooks = books.size(); // Könyvek számának megszámolása

        // Logolás a konzolon
        System.out.println("Number of books found: " + numberOfBooks);

        if (numberOfBooks == 0) {
            model.addAttribute("message", "No books found for the specified " + attribute + ": " + searchValue);
        } else {
            for (Book book : books) {
                String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                book.setImageBase64(imageBase64);
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("selectedAttribute", attribute);
        model.addAttribute("searchValue", searchValue);


        return "bookSearch";
    }



//TODO
    //mettől meddig
    //admin felület
    //cimkék alapján alapján



}
