package com.library.demo.repo;

import com.library.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByIsBooked(Boolean isBooked);
    List<Book> findAllByUserId(Long userId);
    List<Book> findAllByBookName(String bookName);

}
