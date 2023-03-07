package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50)
    @Size(max = 50, message = "Name must be less than 50 characters")
    @NotNull(message = "Name must not be null")
    private String name;

    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
}