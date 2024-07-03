package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

/**
 * The controller class for managing the library operations.
 */
@SpringBootApplication
@Controller
public class libraryController {

    @Autowired
    private final LibraryService libraryService;

    /**
     * Constructs a new libraryController with the given LibraryService.
     *
     * @param libraryService the LibraryService to be used
     */
    @Autowired
    public libraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Retrieves the library and adds it to the model.
     *
     * @param model the model to be used
     * @return the name of the view (HTML file) to be rendered
     */
    @GetMapping("/library")
    public String getLibrary(Model model) {
        List<Book> books = libraryService.getLibrary();
        model.addAttribute("books", books);
        return "library";
    }

    /**
     * Retrieves the user library and adds it to the model.
     *
     * @param model the model to be used
     * @return the name of the view (HTML file) to be rendered
     */
    @GetMapping("/UserLibrary")
    public String userLibrary(Model model) {
        Map<String, Object> modelAttributes = libraryService.getUserLibrary();
        model.addAllAttributes(modelAttributes);
        return "UserLibrary";
    }

    /**
     * Rents a book with the given bookId.
     *
     * @param bookId the ID of the book to be rented
     * @return the URL to redirect after renting the book
     */
    @PostMapping("/rentBook")
    public String rentBook(@RequestParam("bookId") Long bookId) {
        libraryService.rentBook(bookId);
        return "redirect:/bookSearch";
    }

    /**
     * Returns a book with the given bookId.
     *
     * @param bookId the ID of the book to be returned
     * @return the URL to redirect after returning the book
     */
    @PostMapping("/returnBook")
    public String returnBook(@RequestParam("bookId") Long bookId) {
        libraryService.returnBook(bookId);
        return "redirect:/UserLibrary";
    }

    /**
     * Retrieves recommended books for the current user and adds them to the model.
     *
     * @param model the model to be used
     * @return the name of the view (HTML file) to be rendered
     */
    @GetMapping("/recommend")
    public String recommendBooksForCurrentUser(Model model) {
        model.addAttribute("recommendBooks");
        return "recommendBooks";
    }
}