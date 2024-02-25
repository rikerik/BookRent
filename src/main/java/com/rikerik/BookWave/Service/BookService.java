package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    private JdbcOperations jdbcTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void resetSequence() {
        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(book_id) FROM books", Long.class);
        if (maxId != null) {
            jdbcTemplate.execute("ALTER SEQUENCE BOOK_ID_SEQ RESTART WITH " + (maxId + 1));
        }
    }

    public void registerBook(String authorName, String title, String genre, String labels, Long amount,
                             MultipartFile descriptionFile, MultipartFile imageFile, RedirectAttributes redirectAttributes) {
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
                        .userId(admin.getUserId())
                        .imageByte(imageFile.getBytes())
                        .build());
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

        }
    }

    public List<Book> getAllBooks() {
 // add the books to the model so the View will be able to use it
            List<Book> books = bookRepository.findAll();
            if (books.isEmpty()) {
                logger.info("No books are found!");
            } else {
                for (Book book : books) {
                    String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                    book.setImageBase64(imageBase64); //iterating through the books and converting the bytes to images
                }
                logger.info("All books are listed!");
            }
            return books;
    }

    public Map<String, Object> bookSearch(String attribute, String searchValue) {
        List<Book> books;
        String message = "";

        if (searchValue != null && !searchValue.isEmpty()) {
            // Convert all characters to lowercase for consistency
            String searchValueLowerCase = searchValue.toLowerCase();

            switch (attribute) {
                case "author":
                    books = bookRepository.findByAuthorNameILike(searchValueLowerCase);
                    break;
                case "title":
                    books = bookRepository.findByTitleILike(searchValueLowerCase);
                    break;
                default:
                    books = bookRepository.findByGenreILike(searchValueLowerCase);
                    break;
            }
        } else {
            books = bookRepository.findAll();
        }

        int numberOfBooks = books.size(); // Count the number of books

        // Log the number of books found
        logger.info("Number of books found: " + numberOfBooks);

        if (numberOfBooks == 0) {
            message = "No books found for the specified " + attribute + ": " + searchValue;
        } else {
            // Encode images to Base64
            for (Book book : books) {
                String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                book.setImageBase64(imageBase64);
            }
        }

        // Create a Map to hold the message and books list
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", message);
        resultMap.put("books", books);
        return resultMap;
    }
    public void removeBookById(Long bookId) {
        // Find the book by ID
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            // Remove the book
            bookRepository.delete(book);
            logger.info("Book removed successfully: " + book.getTitle());
        } else {
            logger.info("Book with ID '" + bookId + "' not found.");
        }
    }

}

