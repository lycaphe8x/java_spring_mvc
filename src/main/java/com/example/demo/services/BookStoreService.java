package com.example.demo.services;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.repository.IBookRepository;
import com.example.demo.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookStoreService {
    private final IBookRepository IBookRepository;

    private final ICategoryRepository ICategoryRepository;

    public BookStoreService(IBookRepository ibookrepository, ICategoryRepository icategoryrepository) {
        this.IBookRepository = ibookrepository;
        this.ICategoryRepository = icategoryrepository;
    }

    public List<Object[]> getAllBooks() {
        List<Object[]> booksData = new ArrayList<>();
        List<Book> books = IBookRepository.findAll();
        for (Book book : books) {
            Object[] bookData = new Object[5];
            bookData[0] = book.getId();
            bookData[1] = book.getTitle();
            bookData[2] = book.getAuthor();
            bookData[3] = book.getPrice();
            bookData[4] = (book.getCategory() != null) ? book.getCategory().getName() : null;
            booksData.add(bookData);
        }
        return booksData;
    }

    public List<Category> getAllCategories() {
        return ICategoryRepository.findAll();
    }

    @Transactional
    public void deleteBook(Long id) {
        try {
            IBookRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveBook(Book book) {
        try {
            System.out.println(book.getCategory().getId());
            IBookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }
}
