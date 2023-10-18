package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

//Data access object for books
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
    Book findByGenre(String genre);
    Book findByAuthorName(String author);
    Book findByTitle (String title);
}
