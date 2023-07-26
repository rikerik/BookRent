package com.rikerik.BookWave.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable {
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
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false, length = 750)
    private String description;
    @Column(name = "author", nullable = false)
    private String authorName;
    @Column(name = "rented", nullable = false)
    private boolean isRented;
    @Column(name = "imageByte", columnDefinition = "bytea")
    private byte[] imageByte;
    @Column(name = "imageBase64")
    private String imageBase64;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
