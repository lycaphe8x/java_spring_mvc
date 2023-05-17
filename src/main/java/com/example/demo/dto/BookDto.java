package com.example.demo.dto;

public record BookDto(
        Long id,
        String title,
        String author,
        Double price,
        String categoryName)
{ }