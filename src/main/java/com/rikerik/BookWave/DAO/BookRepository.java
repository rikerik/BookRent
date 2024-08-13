package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Data access object for books
/**
 * This interface represents a repository for managing books.
 * It extends the JpaRepository interface, providing CRUD operations for the
 * Book entity.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds a book by its title.
     *
     * @param title the title of the book
     * @return the book with the specified title, or null if not found
     */
    Book findBookByTitle(String title);

    /**
     * Finds a book by its genre.
     *
     * @param genre the genre of the book
     * @return the book with the specified genre, or null if not found
     */
    Book findByGenre(String genre);

    /**
     * Finds a book by its author's name.
     *
     * @param author the name of the author
     * @return the book with the specified author's name, or null if not found
     */
    Book findByAuthorName(String author);

    /**
     * Finds a book by its title.
     *
     * @param title the title of the book
     * @return the book with the specified title, or null if not found
     */
    Book findByTitle(String title);

    /**
     * Finds books whose author's name contains the specified search value
     * (case-insensitive).
     *
     * @param searchValue the search value to match against author's names
     * @return a list of books whose author's name contains the search value
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.authorName) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Book> findByAuthorNameILike(@Param("searchValue") String searchValue);

    /**
     * Finds books whose title contains the specified search value
     * (case-insensitive).
     *
     * @param searchValue the search value to match against titles
     * @return a list of books whose title contains the search value
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Book> findByTitleILike(@Param("searchValue") String searchValue);

    /**
     * Finds books whose genre contains the specified search value
     * (case-insensitive).
     *
     * @param searchValue the search value to match against genres
     * @return a list of books whose genre contains the search value
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Book> findByGenreILike(@Param("searchValue") String searchValue);

    /**
     * Finds books associated with the specified user.
     *
     * @param user the user associated with the books
     * @return a list of books associated with the user
     */
    List<Book> findByUsers(User user);

    /**
     * Removes the association between a user and a book.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM book_user WHERE user_id = :userId AND book_id = :bookId", nativeQuery = true)
    void removeUserFromBook(@Param("userId") long userId, @Param("bookId") long bookId);
}