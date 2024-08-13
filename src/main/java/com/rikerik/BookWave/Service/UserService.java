package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void removeUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            log.info("User removed successfully: " + user.getUsername());
        } else {
            log.info("User with ID '" + userId + "' not found.");
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Boolean existsUserById(Long id) {
        return userRepository.existsById(id);
    }
}
