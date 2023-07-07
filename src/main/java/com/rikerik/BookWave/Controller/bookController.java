package com.rikerik.BookWave.Controller;


import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.Model.Book;
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

    @GetMapping("/library")
    public String library(Model model) {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            String imageBase64 = Base64.getEncoder().encodeToString(book.getImageByte());
            book.setImageBase64(imageBase64);
        }
        model.addAttribute("books", books);
        return "library";
    }

}
