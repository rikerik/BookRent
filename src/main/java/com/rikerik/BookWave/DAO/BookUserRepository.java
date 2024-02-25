package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This interface represents a repository for managing BookUser entities.
 * It extends the JpaRepository interface, providing CRUD operations for BookUser entities.
 */
public interface BookUserRepository extends JpaRepository<BookUser, Long> {

    /**
     * Retrieves a list of overdue books based on the given current date and time.
     *
     * @param currentDateTime The current date and time.
     * @return A list of BookUser entities representing the overdue books.
     */
    @Query("SELECT bu FROM BookUser bu WHERE bu.dueDateTime < :currentDateTime")
    List<BookUser> findOverdueBooks(@Param("currentDateTime") LocalDateTime currentDateTime);
}
