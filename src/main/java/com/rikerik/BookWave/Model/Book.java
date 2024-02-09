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

    // Könyv azonosítója
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

    // Könyv mennyisége
    @Column(name = "bookAmount")
    private long bookAmount;

    // Könyv címe
    @Column(name = "title", nullable = false)
    private String title;

    // Könyv leírása
    @Column(name = "description", nullable = false, length = 750)
    private String description;

    // Könyv szerzője
    @Column(name = "author", nullable = false)
    private String authorName;

    // Könyv műfaja
    @Column(name = "genre", nullable = true)
    private String genre;

    // Könyv címkéi
    @Column(name = "label", nullable = true)
    private String labels;

    // Könyv képének byte tömbje
    @Column(name = "imageByte", columnDefinition = "bytea")
    private byte[] imageByte;

    // Könyv képének Base64 kódolt stringje
    @Column(name = "imageBase64")
    private String imageBase64;

    // Felhasználó azonosítója, aki hozzárendelte a könyvet
    @Column(name = "user_id")
    private long userId;

    // Felhasználók halmaza, akik rendelkeznek a könyvvel
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_user",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getUserId() == user.getUserId();
    }

    @Override
    public int hashCode() {
        return (int) (getUserId() ^ (getUserId() >>> 32));
    }
}

