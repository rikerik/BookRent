package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.BookUserRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.BookUser;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;


import static com.rikerik.BookWave.Service.RecommendationService.calculateTopLabels;
import static com.rikerik.BookWave.Service.RecommendationService.findRecommendedBooks;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookUserRepository bookUserRepository;
    private final CustomUserDetailsService userService;

    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    @Autowired
    public LibraryService(BookRepository bookRepository, UserRepository userRepository, BookUserRepository bookUserRepository, CustomUserDetailsService userService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookUserRepository = bookUserRepository;
        this.userService = userService;
    }

    public List<Book> getLibrary() { // add the books to the model so the View will be able to use it
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

    public Map<String, Object> getUserLibrary() {
        Map<String, Object> modelAttributes = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());

        List<Book> allBooks = bookRepository.findAll();
        List<Book> userBooks = bookRepository.findByUsers(user);

        if (userBooks.isEmpty()) {
            logger.info("No books are found for the user!");
        } else {
            for (Book book : userBooks) {
                String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                book.setImageBase64(imageBase64);

                // Fetch and set the labels for the book
                List<String> labels = Arrays.asList(book.getLabels().split(","));
                System.out.println("Labels for Book " + book.getBookId() + ": " + labels);
            }
            logger.info("User's books are listed!");

            // Calculate label frequencies for all books
           List<String> topLabels = calculateTopLabels(userBooks);

            logger.info("Top Labels: " + topLabels);
            // Find books that match the top labels
            List<Book> recommendedBooks = findRecommendedBooks(topLabels, allBooks, userBooks);

            modelAttributes.put("recommendedBooks", recommendedBooks);
            modelAttributes.put("books", userBooks);
            // Check if the user has at least 5 fantasy books
            boolean hasEnoughFantasyBooks = userService.hasEnoughFantasyBooks(userBooks);
            modelAttributes.put("hasEnoughFantasyBooks", hasEnoughFantasyBooks);
            modelAttributes.put("topLabels", topLabels);

            for (Book book : recommendedBooks) {
                logger.info("Recommended book title: {}", book.getTitle());
            }
        }

        return modelAttributes;
    }

    public void rentBook(Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getBookAmount() > 0) {
                try {
                    // Check if the user already has the book in their library
                    boolean alreadyRented = book.getUsers().contains(user);

                    if (!alreadyRented) {
                        // Decrease the book amount
                        book.setBookAmount(book.getBookAmount() - 1);

                        // Create a BookUser entity and set the user, book, and due date
                        BookUser bookUser = new BookUser();
                        bookUser.setUser(user);
                        bookUser.setBook(book);
                        bookUser.setDueDateTime(LocalDateTime.now().plusDays(1)); // Set this to an actual date

                        bookUserRepository.save(bookUser);

                        // Add the user to the book
                        book.getUsers().add(user);

                        // Save the changes
                        bookRepository.save(book);

                        logger.info(book.getTitle() + " is rented");
                    } else {
                        logger.info(book.getTitle() + " is already in the user's library");
                    }
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }

    public void returnBook(Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            // Remove the current user from the user_book table associated with the book
            bookRepository.removeUserFromBook(currentUser.getUserId(), bookId);

            // Increase the book quantity
            book.setBookAmount(book.getBookAmount() + 1);

            bookRepository.save(book);
            logger.info(book.getTitle() + " is returned by " + currentUser.getUsername());
        }
    }


}
