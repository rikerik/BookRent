package com.rikerik.BookWave.Service;

import com.rikerik.BookWave.DAO.UserRepository;
import com.rikerik.BookWave.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void removeUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            logger.info("User removed successfully: " + user.getUsername());
        } else {
            logger.info("User with ID '" + userId + "' not found.");
        }
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Boolean existsUserById(Long id) {
        return  userRepository.existsById(id);
    }
}
