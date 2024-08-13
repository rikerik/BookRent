package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.BookUserRepository;
import com.rikerik.BookWave.Model.BookUser;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class represents a service for managing book users.
 */
@Slf4j
@Service
public class BookUserService {

  private BookUserRepository bookUserRepository;
  private BookRepository bookRepository;

  public BookUserService(BookUserRepository bookUserRepository, BookRepository bookRepository) {
    this.bookUserRepository = bookUserRepository;
    this.bookRepository = bookRepository;
  }

  /**
   * This method is scheduled to run every 24 hours and checks for overdue books.
   * If a book is overdue, it removes the user from the book, increases the book
   * amount by 1,
   * and saves the updated book information.
   */
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
  public void checkAndRemoveOverdueBooks() {
    log.info("Executing scheduled task...");
    List<BookUser> overdueBookUsers = bookUserRepository.findOverdueBooks(LocalDateTime.now());

    for (BookUser bookUser : overdueBookUsers) {
      bookRepository.removeUserFromBook(bookUser.getUser().getUserId(), bookUser.getBook().getBookId());
      bookUser.getBook().setBookAmount(bookUser.getBook().getBookAmount() + 1);
      bookRepository.save(bookUser.getBook());
    }
  }
}