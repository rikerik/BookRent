package com.rikerik.BookRent.DAO;

import com.rikerik.BookRent.Model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
public interface UserRepository extends JpaRepository<User, Long> {
}
