package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.BookUserRepository;
import com.rikerik.BookWave.Model.BookUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

/**
 * This class represents a service for managing book users.
 */
@Service
public class BookUserService {

  @Autowired
  private BookUserRepository bookUserRepository;

  @Autowired
  private BookRepository bookRepository;

  /**
   * This method is scheduled to run every 24 hours and checks for overdue books.
   * If a book is overdue, it removes the user from the book, increases the book amount by 1,
   * and saves the updated book information.
   */
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
  public void checkAndRemoveOverdueBooks() {
    System.out.println("Executing scheduled task...");
    List<BookUser> overdueBookUsers = bookUserRepository.findOverdueBooks(LocalDateTime.now());

    for (BookUser bookUser : overdueBookUsers) {
      bookRepository.removeUserFromBook(bookUser.getUser().getUserId(), bookUser.getBook().getBookId());
      bookUser.getBook().setBookAmount(bookUser.getBook().getBookAmount() + 1);
      bookRepository.save(bookUser.getBook());
    }
  }
}