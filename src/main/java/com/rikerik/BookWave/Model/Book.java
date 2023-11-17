package com.rikerik.BookWave.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "bookAmount")
    private long bookAmount;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 750)
    private String description;

    @Column(name = "author", nullable = false)
    private String authorName;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "label", nullable = true)
    private String labels;

    @Column(name = "imageByte", columnDefinition = "bytea")
    private byte[] imageByte;

    @Column(name = "imageBase64")
    private String imageBase64;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_user",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

}
