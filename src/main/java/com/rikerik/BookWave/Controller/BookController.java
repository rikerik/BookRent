package com.rikerik.BookWave.Controller;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.Model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@SpringBootApplication
@Controller
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(com.rikerik.BookWave.Controller.Controller.class);

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @PostMapping("/registerBook")
    public String registerBook(@RequestParam("authorName") String authorName,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("image") String image,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (bookRepository.findBookByTitle(title) != null) {
            redirectAttributes.addAttribute("error", "exists");
            return "BookAddingPage";
        } else {
            File file = new File(description);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String text;
            while ((text = br.readLine()) != null)
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File(image));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "jpg", baos);

                    bookRepository.save(Book.builder()
                            .authorName(authorName)
                            .title(title)
                            .description(text)
                            .imageByte(baos.toByteArray())
                            .build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        return "BookAddingPage";
    }

    @GetMapping("/BookAddingPage") //to show the register form with get
    public String register() {
        return "BookAddingPage";
    }
}
