package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 50)
    @Max(value = 50, message = "Title must be less than 50 characters")
    @NotNull(message = "Title must not be null")
    private String title;
    @Column(name = "author", length = 50)
    @Max(value = 50, message = "Author must be less than 50 characters")
    private String author;

    @Column(name = "price")
    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",
            insertable = false,
            updatable = false,
            referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "FK_SACH_LOAISACH"))
    private Category category;
}
