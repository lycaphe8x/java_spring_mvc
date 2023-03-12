package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.services.BookService;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/edit";
    }

    @GetMapping(value = "/delete", params = "id")
    public String deleteBook(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        String viewName = "book/add";
        if (book.getId() != null)
            viewName = "book/edit";

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", (book.getId() != null) ? "Edit Book" : "Add Book");
            return viewName;
        }

        bookService.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Book saved successfully");
        return "redirect:/books";
    }
}


