package fit.hutech.spring.controllers;

import fit.hutech.spring.entities.Book;
import fit.hutech.spring.services.BookService;
import fit.hutech.spring.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private final CategoryService categoryService;

    @GetMapping
    public String showAllBooks(@NotNull Model model,
                               @RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "20") Integer pageSize,
                               @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("books", bookService.getAllBooks(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", bookService.getAllBooks(pageNo, pageSize, sortBy).size() / pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/list";
    }

    @GetMapping("/add")
    public String addBookForm(@NotNull Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(
            @Valid @ModelAttribute("book") Book book,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@NotNull Model model, @PathVariable long id) {
        var book = bookService.getBookById(id);
        model.addAttribute("book", book != null ? book : new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/edit";
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") Book book,
                           @NotNull BindingResult bindingResult,
                           @NotNull Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        if (bookService.getBookById(id) != null)
            bookService.deleteBookById(id);
        else
            throw new IllegalArgumentException("Book not found");
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBook(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("books", bookService.searchBook(keyword));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages",
                bookService
                        .getAllBooks(pageNo, pageSize, sortBy).size() / pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/list";
    }
}