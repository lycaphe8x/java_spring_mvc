package fit.hutech.spring.controllers;

import fit.hutech.spring.entities.Book;
import fit.hutech.spring.services.BookService;
import fit.hutech.spring.services.CartService;
import fit.hutech.spring.services.CategoryService;
import fit.hutech.spring.viewmodels.BookGetVm;
import fit.hutech.spring.viewmodels.BookPostVm;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ApiController {
    private final BookService bookService;

    private final CategoryService categoryService;

    private final CartService cartService;

    @GetMapping("/books")
    public ResponseEntity<List<BookGetVm>> getAllBooks(Integer pageNo, Integer pageSize, String sortBy) {
        return ResponseEntity.ok(bookService.getAllBooks(
                pageNo == null ? 0 : pageNo,
                pageSize == null ? 20 : pageSize,
                sortBy == null ? "id" : sortBy)
                .stream()
                .map(BookGetVm::from)
                .toList());
    }

    @GetMapping("/books/id/{id}")
    public ResponseEntity<BookGetVm> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id)
                .map(BookGetVm::from)
                .orElse(null));
    }

    @PostMapping("/books")
    public ResponseEntity<Void> createBook(@RequestBody @NotNull BookPostVm bookPostVm) {
        bookService.addBook(Book.builder()
                .title(bookPostVm.title())
                .author(bookPostVm.author())
                .price(bookPostVm.price())
                .category(categoryService.getCategoryById(bookPostVm.categoryId()).orElse(null))
                .build());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/books")
    public ResponseEntity<Void> updateBook(@RequestBody @NotNull BookPostVm bookPostVm) {
        bookService.updateBook(Book.builder()
                .title(bookPostVm.title())
                .author(bookPostVm.author())
                .price(bookPostVm.price())
                .category(categoryService.getCategoryById(bookPostVm.categoryId()).orElse(null))
                .build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<BookGetVm>> searchBooks(String keyword) {
        return ResponseEntity.ok(bookService.searchBook(keyword)
                .stream()
                .map(BookGetVm::from)
                .toList());
    }
}
