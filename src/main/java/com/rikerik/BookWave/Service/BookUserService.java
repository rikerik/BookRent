package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.BookUserRepository;
import com.rikerik.BookWave.Model.BookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookUserService {

    @Autowired
    private BookUserRepository bookUserRepository;


    @Autowired
    private BookRepository bookRepository;

    @Scheduled(fixedDelay = 1000) // Run every 1000 milliseconds (1 second) TODO dont check it every 1 second
        public void checkAndRemoveOverdueBooks() {
        System.out.println("Executing scheduled task...");
        List<BookUser> overdueBookUsers = bookUserRepository.findOverdueBooks(LocalDateTime.now());

        for (BookUser bookUser : overdueBookUsers) {
          bookRepository.removeUserFromBook(bookUser.getUser().getUserId(), bookUser.getBook().getBookId());
          bookUser.getBook().setBookAmount(bookUser.getBook().getBookAmount() +1);
          bookRepository.save(bookUser.getBook());

        }
    }
}