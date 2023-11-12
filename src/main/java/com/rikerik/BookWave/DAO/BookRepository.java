package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Data access object for books
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookByTitle(String title);
    Book findByGenre(String genre);
    Book findByAuthorName(String author);
    Book findByTitle (String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.authorName) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Book> findByAuthorNameILike(@Param("searchValue") String searchValue);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Book> findByTitleILike(@Param("searchValue") String searchValue);

    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Book> findByGenreILike(@Param("searchValue") String searchValue);
}
