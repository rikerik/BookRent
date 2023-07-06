package com.rikerik.BookWave.Controller;


import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Service.BookResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootApplication
@Controller
public class bookController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookWave.Controller.Controller.class);

    private final BookRepository bookRepository;

    public bookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //TEST

    @GetMapping("/GetAllBooks")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            logger.info("No books found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<BookResponseDTO> bookResponses = new ArrayList<>();
            for (Book book : books) {
                BookResponseDTO response = new BookResponseDTO(
                        book.getBookId(),
                        book.getTitle(),
                        book.getDescription(),
                        book.getAuthorName(),
                        book.isRented(),
                        Base64.getEncoder().encodeToString(book.getImageByte())
                );
                bookResponses.add(response);
            }

            logger.info("All books are listed");
            return new ResponseEntity<>(bookResponses, HttpStatus.OK);
        }
    }



    @GetMapping("/library")
    public String library(Model model) {
        List<Book> books = bookRepository.findAll();
            String imageBase64 = Base64.getEncoder().encodeToString(books.get(0).getImageByte());
            books.get(0).setImageBase64(imageBase64);
        model.addAttribute("books", books);
        return "library";
    }

}
