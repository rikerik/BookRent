package com.rikerik.BookWave.DAO;

import com.rikerik.BookWave.Model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookUserRepository extends JpaRepository<BookUser, Long> {
    @Query("SELECT bu FROM BookUser bu WHERE bu.dueDateTime < :currentDateTime")
    List<BookUser> findOverdueBooks(@Param("currentDateTime") LocalDateTime currentDateTime);
}
