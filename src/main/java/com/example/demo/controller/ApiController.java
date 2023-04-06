package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
public class ApiController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @Transactional
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id) != null)
            bookService.deleteBook(id);
    }

    @PutMapping()
    @Transactional
    public void updateBook(@RequestBody Book book) {
        if (bookService.getBookById(book.getId()) != null)
            bookService.updateBook(book);
    }
}
