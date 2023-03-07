package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.services.BookStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookStoreService bookStoreService;

    public BookController(BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @GetMapping
    public String showAllBooks(Model model) {
        List<Object[]> books = bookStoreService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", bookStoreService.getAllCategories());
        return "book/add";
    }

    @GetMapping("/edit")
    public String editBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", bookStoreService.getAllCategories());
        return "book/edit";
    }
}


