package com.rikerik.BookWave.Model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity // to map object to db
@Table(name = "Users") //name the table
@Data //generates all boilerplate code, like getters and setters
@NoArgsConstructor
@AllArgsConstructor
@Builder //generating a builder pattern for the class
public class User{

    @Id
    @SequenceGenerator(name = "ID_SEQ", // name of seq. gen.
            sequenceName = "ID_SEQ", //db seq. name for generating pk values
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, //generating by sequence
            generator = "ID_SEQ" //the name of the generator
    )
    @Column(name = "id")
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

}
