package com.rikerik.BookWave.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

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

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = true)
    private Roles Role;

    @JsonIgnore // Exclude books from hashCode and equals
    @ManyToMany(mappedBy = "users")
    private Set<Book> books = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, Role);
    }

}
