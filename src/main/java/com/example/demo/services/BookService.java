package com.example.demo.services;

import com.example.demo.entity.Book;
import com.example.demo.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private IBookRepository IBookRepository;


    public List<Object[]> getAllBooks() {
        List<Object[]> booksData = new ArrayList<>();
        List<Book> books = IBookRepository.findAll();
        for (Book book : books) {
            Object[] bookData = new Object[5];
            bookData[0] = book.getId();
            bookData[1] = book.getTitle();
            bookData[2] = book.getAuthor();
            bookData[3] = book.getPrice();
            bookData[4] = book.getCategory().getId();
            booksData.add(bookData);
        }
        return booksData;
    }



}
