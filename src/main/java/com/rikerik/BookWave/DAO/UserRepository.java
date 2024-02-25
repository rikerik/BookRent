package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Data access object for users
/**
 * This interface represents a repository for managing User entities.
 * It extends the JpaRepository interface, providing CRUD operations for User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the user with the specified username, or null if not found
     */
    User findByUsername(String username);
}
