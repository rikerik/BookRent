package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
}
