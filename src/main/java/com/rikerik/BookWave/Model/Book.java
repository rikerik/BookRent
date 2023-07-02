package com.rikerik.BookWave.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @SequenceGenerator(name = "BOOK_ID_SEQ",
            sequenceName = "BOOK_ID_SEQ",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BOOK_ID_SEQ"
    )
    @Column(name = "bookId", nullable = false, columnDefinition = "serial")
    private long bookId;
    @Column(name ="title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "author",nullable = false)
    private String authorName;
    @Column(name = "available",nullable = false)
    private boolean isRented;
    @Lob
    @Column (name = "image")
    private byte[] image;
}
