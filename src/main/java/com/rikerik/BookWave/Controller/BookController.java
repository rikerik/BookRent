package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.Model.Book;

import com.rikerik.BookWave.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * This class is the controller for handling book-related operations.
 */
@SpringBootApplication
@Controller
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {

        this.bookService = bookService;
    }

    /**
     * Registers a book with the given details.
     *
     * @param authorName         the name of the author
     * @param title              the title of the book
     * @param genre              the genre of the book
     * @param labels             the labels associated with the book
     * @param amount             the amount of books available
     * @param descriptionFile    the file containing the book's description
     * @param imageFile          the file containing the book's image
     * @param redirectAttributes the redirect attributes for the response
     * @return the view name for the book adding page
     */
    @PostMapping("/registerBook")
    public String registerBook(@RequestParam("authorName") String authorName,
            @RequestParam("title") String title,
            @RequestParam("genre") String genre,
            @RequestParam("labels") String labels,
            @RequestParam("amount") Long amount,
            @RequestParam("description") MultipartFile descriptionFile, // Multipart file to work with uploaded txt and
                                                                        // image
            @RequestParam("image") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        bookService.registerBook(authorName, title, genre, labels, amount, descriptionFile, imageFile,
                redirectAttributes);
        return "redirect:/BookAddingPage";

    }

    /**
     * Retrieves a list of books and adds them to the model.
     * 
     * @param model the model to add the books to
     * @return the view name for displaying all books
     */
    @GetMapping("/listBooks")
    public String Books(Model model) {
        // Fetch all books and add them to the model
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "allBooks";
    }

    /**
     * Performs a book search based on the specified attribute and search value.
     * 
     * @param attribute   the attribute to search for (default value is "genre")
     * @param searchValue the value to search for (optional)
     * @param model       the model object to add the search results to
     * @return the name of the view to render the search results
     */
    @GetMapping("/bookSearch")
    public String searchBooks(
            @RequestParam(required = false) String attribute,
            @RequestParam(required = false) String searchValue,
            Model model) {
        if (attribute == null || searchValue == null || searchValue.isEmpty()) {
            attribute = "title"; // Default attribute
            searchValue = ""; // Empty search value to get all books
        }
        Map<String, Object> result = bookService.bookSearch(attribute, searchValue);
        model.addAttribute("books", result.get("books"));
        model.addAttribute("message", result.get("message"));
        return "bookSearch"; // Main page with search form and initial books
    }

    @GetMapping("/searchResults")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchBooksFragment(
            @RequestParam String attribute,
            @RequestParam String searchValue) {
        Map<String, Object> result = bookService.bookSearch(attribute, searchValue);
        return ResponseEntity.ok(result); // Return JSON response
    }

    /**
     * Returns the name of the view template for the book adding page.
     *
     * @return the name of the view template for the book adding page
     */
    @GetMapping("/BookAddingPage")
    public String register(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        logger.debug("Number of books: {}", books.size());
        return "BookAddingPage";
    }

    /**
     * Returns the name of the view template for displaying all books.
     *
     * @return the name of the view template
     */
    @GetMapping("/allBooks")
    public String allBooks() {
        return "allBooks";
    }

    @PostMapping("/bookRemover")
    public String removeBook(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "Cannot remove admin user with ID 2.");
        if (bookService.existsBookById(id)) {
            try {
                bookService.removeBookById(id);
                redirectAttributes.addFlashAttribute("message", "Book removed successfully with ID: " + id);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Error removing book: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Book with ID " + id + " does not exist.");
        }

        // Redirect to getUsers to refresh the user list after removal
        return "redirect:/BookAddingPage";
    }

    /**
     * Retrieves the name of the view template for removing a book.
     *
     * @return the name of the view template for removing a book
     */

}