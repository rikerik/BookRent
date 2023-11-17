package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
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

    List<Book> findByUsers(User user);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM book_user WHERE user_id = :userId AND book_id = :bookId", nativeQuery = true)
    void removeUserFromBook(@Param("userId") long userId, @Param("bookId") long bookId);



}
