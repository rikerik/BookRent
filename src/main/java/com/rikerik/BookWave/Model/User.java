package com.rikerik.BookWave.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents a user in the system.
 */
@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // Felhasználó azonosítója
    @Id
    @SequenceGenerator(name = "ID_SEQ",
            sequenceName = "ID_SEQ",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ID_SEQ"
    )
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private long userId;

    // Felhasználónév
    @Column(name = "username", nullable = false)
    private String username;

    // Jelszó
    @Column(name = "password", nullable = false)
    private String password;

    // Email cím
    @Column(name = "email", nullable = false)
    private String email;

    // Felhasználó szerepe
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = true)
    private Roles Role;

    // Könyvek halmaza, amelyekhez a felhasználó hozzárendelt
    @JsonIgnore // Könyvek kizárása a hashCode és equals számításból
    @ManyToMany(mappedBy = "users")
    private Set<Book> books = new HashSet<>();

}
