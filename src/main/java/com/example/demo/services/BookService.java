package com.example.demo.services;

import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private List<Book> books;

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(Book book) {
        Optional<Book> bookOptional = getBookById(book.getId());
        if (bookOptional.isPresent()) {
            Book bookUpdate = bookOptional.get();
            bookUpdate.setTitle(book.getTitle());
            bookUpdate.setAuthor(book.getAuthor());
            bookUpdate.setPrice(book.getPrice());
            bookUpdate.setCategory(book.getCategory());
        }
    }

    public void deleteBookById(Long id) {
        getBookById(id).ifPresent(books::remove);
    }
}
