package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.UserRepository;

import com.rikerik.BookWave.Model.CustomUserDetails;
import com.rikerik.BookWave.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.
        UserDetailsService;
import org.springframework.security.core.userdetails.
        UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return new CustomUserDetails(user); //if the users exists it will return the user with the appropriate fields
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }
}
