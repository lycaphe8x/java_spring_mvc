package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50)
    @NotNull(message = "Username must not be null")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @Column(name = "password", length = 250)
    @NotNull(message = "Password must not be null")
    private String password;

    @Column(name = "isLocked"
            , columnDefinition = "bit default 0")
    private boolean isLocked;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Book> books = new ArrayList<>();
}
