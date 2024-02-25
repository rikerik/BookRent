package com.rikerik.BookWave.Controller;


import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    private final BookService bookService;

    @Autowired


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
                               @RequestParam("description") MultipartFile descriptionFile, //Multipart file to work with uploaded txt and image
                               @RequestParam("image") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes) {
        bookService.registerBook(authorName, title, genre, labels, amount, descriptionFile, imageFile, redirectAttributes);
        return "BookAddingPage";
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
     * @param attribute the attribute to search for (default value is "genre")
     * @param searchValue the value to search for (optional)
     * @param model the model object to add the search results to
     * @return the name of the view to render the search results
     */
    @GetMapping("/bookSearch")
    public String bookSearch(@RequestParam(value = "attribute", defaultValue = "genre") String attribute,
                             @RequestParam(value = "searchValue", required = false) String searchValue,
                             Model model) {
        // Call bookSearch method from BookService
        Map<String, Object> searchResult = bookService.bookSearch(attribute, searchValue);

        // Get the message and books list from the searchResult map
        String message = (String) searchResult.get("message");
        List<Book> books = (List<Book>) searchResult.get("books");

        // Add message and books list to the model
        model.addAttribute("message", message);
        model.addAttribute("books", books);
        model.addAttribute("selectedAttribute", attribute);
        model.addAttribute("searchValue", searchValue);

        return "bookSearch";
    }

    /**
        * Returns the name of the view template for the book adding page.
        *
        * @return the name of the view template for the book adding page
        */
    @GetMapping("/BookAddingPage")
    public String register() {
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

    /**
        * Removes a book by its ID.
        *
        * @param id    the ID of the book to be removed
        * @param model the model object used for rendering the view
        * @return the name of the view to be rendered after removing the book
        */
    @PostMapping("/bookRemove")
    public String removeBookById(@RequestParam("id") Long id, Model model) {
        try {
            bookService.removeBookById(id);
            model.addAttribute("message", "Book removed successfully with ID: " + id);
        } catch (Exception e) {
            model.addAttribute("message", "Error removing book: " + e.getMessage());
        }
        return "bookRemove";
    }

    /**
     * Retrieves the name of the view template for removing a book.
     *
     * @return the name of the view template for removing a book
     */
    @GetMapping("/bookRemove")
    public String bookRemove() {
        return "bookRemove";
    }



//TODO
    //admin felület
    //kibérelt könyv esetén keresésnél nem jelez semmit csak egy üres oldalt jelenít meg


}
