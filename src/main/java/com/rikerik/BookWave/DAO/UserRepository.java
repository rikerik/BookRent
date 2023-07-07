package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Data access object for users
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
