package com.library.demo.service;

import com.library.demo.model.Book;
import com.library.demo.model.User;
import com.library.demo.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibrarianService {
    private final BookRepository bookRepository;

    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.getById(id);
    }
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book){
        bookRepository.save(book);
    }

}
