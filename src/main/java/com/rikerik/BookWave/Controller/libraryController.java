package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.BookUserRepository;
import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.BookUser;
import com.rikerik.BookWave.Model.User;
import com.rikerik.BookWave.Service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//Controller for book operations
@SpringBootApplication
@Controller
public class libraryController {


    @Autowired
    private CustomUserDetailsService userService;
    private static final Logger logger = LoggerFactory.getLogger(libraryController.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookUserRepository bookUserRepository;


    @Autowired
    public libraryController(BookRepository bookRepository, UserRepository userRepository, BookUserRepository bookUserRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookUserRepository = bookUserRepository;
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
            Map<String, Long> labelFrequencies = userBooks.stream()
                    .flatMap(book -> Arrays.stream(book.getLabels().split(",")))
                    .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

            System.out.println("Label Frequencies: " + labelFrequencies);

            // Sort the labels by frequency in descending order
            List<Map.Entry<String, Long>> sortedLabels = labelFrequencies.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .collect(Collectors.toList());

            // Select the top 3 labels
            List<String> topLabels = sortedLabels.stream()
                    .limit(3)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            logger.info("Top Labels: " + topLabels);
            // Find books that match the top labels
            Book recommendedBook = findRecommendedBook(topLabels, allBooks, userBooks);

            model.addAttribute("recommendedBook",recommendedBook);
            model.addAttribute("books", userBooks);
            // Check if the user has at least 5 fantasy books
            boolean hasEnoughFantasyBooks = userService.hasEnoughFantasyBooks(userBooks);
            model.addAttribute("hasEnoughFantasyBooks", hasEnoughFantasyBooks);
            model.addAttribute("topLabels", topLabels);

            logger.info(recommendedBook.getTitle());
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

                    // Create a BookUser entity and set the user, book, and due date
                    BookUser bookUser = new BookUser();
                    bookUser.setUser(user);
                    bookUser.setBook(book);
                    bookUser.setDueDateTime(LocalDateTime.now().plusDays(1)); //set this to an actual date

                    bookUserRepository.save(bookUser);

                    // Add the user to the book
                    book.getUsers().add(user);

                    // Save the changes
                    bookRepository.save(book);

                    logger.info(book.getTitle() + " is rented");
                } catch (Exception e) {
                  logger.warn(e.getMessage());
                }
            }
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

    @GetMapping("/recommend")
    public String recommendBooksForCurrentUser(Model model){
        model.addAttribute("recommendBooks");
        return "recommendBooks";
    }




    private Book findRecommendedBook(List<String> labels, List<Book> allBooks, List<Book> userBooks) {
        // Create a set of user book titles for faster lookup
        Set<String> userBookTitles = userBooks.stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        // Shuffle the list of all books
        List<Book> shuffledBooks = new ArrayList<>(allBooks);
        Collections.shuffle(shuffledBooks);

        // Find books that are not in the user's library and match all labels
        Optional<Book> recommendedBook = shuffledBooks.stream()
                .filter(book -> !userBookTitles.contains(book.getTitle())) // Filter out user books
                .filter(book -> bookContainsAllLabels(book, labels))
                .findFirst();

        if (!recommendedBook.isPresent()) {
            for (int i = labels.size(); i > 0; i--) {
                List<String> subsetLabels = labels.subList(0, i);
                recommendedBook = shuffledBooks.stream()
                        .filter(book -> !userBookTitles.contains(book.getTitle())) // Filter out user books
                        .filter(book -> bookContainsAllLabels(book, subsetLabels))
                        .findFirst();
                if (recommendedBook.isPresent()) {
                    break;
                }
            }
        }

        return recommendedBook.orElse(new Book("", Collections.emptyList()));
    }


    private boolean bookContainsAllLabels(Book book, List<String> labels) {
        List<String> bookLabels = Arrays.asList(book.getLabels().split(","));
        return bookLabels.containsAll(labels);
    }



    

}
