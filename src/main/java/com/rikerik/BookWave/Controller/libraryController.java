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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Controller for book operations
@SpringBootApplication
@Controller
public class libraryController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookWave.Controller.Controller.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public libraryController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    //lists all books in the library
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

    //lists all books of the current user in the user's library
    @GetMapping("/UserLibrary")
    public String userLibrary(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtaining the currently authenticated user
        User user = userRepository.findByUsername(authentication.getName());


        List<Book> userBooks = bookRepository.findByUsers(user);
        if (userBooks.isEmpty()) {
            logger.info("No books are found for the user!");
        } else {
            for (Book book : userBooks) {
                String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
                book.setImageBase64(imageBase64);
            }
            logger.info("User's books are listed!");


            model.addAttribute("books", userBooks);
        }
        return "UserLibrary";
    }
    //rents book from the library and adds to the library

    @PostMapping("/rentBook")
    public String rentBook(@RequestParam("bookId") Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (book.getBookAmount() > 0) {
                try {
                    // Decrease the book amount
                    book.setBookAmount(book.getBookAmount() - 1);

                    // Add the user to the book
                    book.getUsers().add(user);

                    // Save the changes
                    bookRepository.save(book);

                    logger.info(book.getTitle() + " is rented");
                } catch (Exception e) {
                    logger.error("Error renting the book with ID " + bookId, e);
                    // Handle the exception (e.g., show an error message to the user)
                    return "redirect:/library?error=rental_failed";
                }
            } else {
                logger.warn("No more available copies of " + book.getTitle() + " in the library");
                // Handle the case where the book is not available
                return "redirect:/library?error=no_available_copies";
            }
        } else {
            logger.error("Book with ID " + bookId + " not found");
            // Handle the case where the book is not found
            return "redirect:/library?error=book_not_found";
        }

        return "redirect:/library";
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestParam("bookId") Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            // Távolítsuk el az aktuális felhasználót a könyvhöz tartozó user_book táblából
          bookRepository.removeUserFromBook(currentUser.getUserId(), bookId);

            // Növeljük a könyv mennyiségét
            book.setBookAmount(book.getBookAmount() + 1);

            bookRepository.save(book);
            logger.info(book.getTitle() + " is returned by " + currentUser.getUsername());
        }

        return "redirect:/UserLibrary";
    }


}
