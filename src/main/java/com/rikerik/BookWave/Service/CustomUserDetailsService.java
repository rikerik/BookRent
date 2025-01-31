package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.BookRepository;
import com.rikerik.BookWave.DAO.UserRepository;

import com.rikerik.BookWave.Model.Book;
import com.rikerik.BookWave.Model.CustomUserDetails;
import com.rikerik.BookWave.Model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements the UserDetailsService interface and provides custom
 * functionality for loading user details and checking if a user has enough
 * fantasy or scifi books.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;
    private BookRepository bookRepository;

    public CustomUserDetailsService(UserRepository userRepo, BookRepository bookRepository) {
        this.userRepo = userRepo;
        this.bookRepository = bookRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return new CustomUserDetails(user); // if the users exists it will return the user with the appropriate
                                                // fields
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }

    public List<Book> getUserBooks(User user) {
        return bookRepository.findByUsers(user);
    }

    public boolean hasEnoughFantasyBooks(List<Book> userBooks) {
        // Filter fantasy books
        List<Book> fantasyBooks = userBooks.stream()
                .filter(book -> book.getGenre().contains("Fantasy"))
                .toList();

        // Check if the user has at least 5 fantasy books
        return !fantasyBooks.isEmpty();
    }

    public boolean hasEnoughScifiBooks(List<Book> userBooks) {
        // Filter scifi books
        List<Book> scifiBooks = userBooks.stream()
                .filter(book -> book.getGenre().contains("Scifi"))
                .toList();

        // Check if the user has at least 5 fantasy books
        return !scifiBooks.isEmpty();
    }
}
