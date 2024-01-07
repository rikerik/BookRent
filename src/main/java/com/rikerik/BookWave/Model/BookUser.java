package com.rikerik.BookWave.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BookUserId.class) // Specify the composite key class
public class BookUser  {

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "due_date")
    private LocalDateTime dueDateTime;

}