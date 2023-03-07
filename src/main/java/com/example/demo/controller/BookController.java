package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.services.BookStoreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/delete", params = "id")
    public String deleteBook(@RequestParam("id") Long id) {
        bookStoreService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,  BindingResult bindingResult, Model model) {
        System.out.println(book.getCategory().getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "book/add";
        }
        book.setId(null);
        bookStoreService.saveBook(book);
        return "redirect:/books";
    }
}


