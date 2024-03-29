package com.library.demo.controller;

import com.library.demo.entity.Book;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.service.LibrarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/api/v1/librarians")
@RequiredArgsConstructor
public class LibrarianController {

    private final LibrarianService librarianService;

    @GetMapping("/profile")
    public String readerMainPage(Model model) {
        List<BookDTO> books = librarianService.findAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("book", new Book());
        return "librarian/main_page";
    }

    @GetMapping("/booking")
    public String redirectToBooking(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/add_book";
    }

    @GetMapping("/editing")
    public String redirectEditing(Model model) {
        List<BookDTO> books = librarianService.findAllBooks();
        model.addAttribute("books", books);
        return "librarian/editing_books";
    }

    @PostMapping("/addBook")
    public String addBook(BookDTO book) {
        librarianService.addBook(book);
        return "redirect:/api/v1/librarians/profile";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        librarianService.deleteBookById(id);
        return "redirect:/api/v1/librarians/profile";
    }

    @GetMapping("/editBook/{id}")
    public String updateRedirectBook(Model model, @PathVariable(name = "id") Long id) {
        BookDTO book = librarianService.findById(id);
        model.addAttribute("book", book);
        return "librarian/edit_book";
    }

    @PostMapping("/updateBook/{id}")
    public String updateBook(BookDTO book) {
        librarianService.updateBook(book);
        return "redirect:/api/v1/librarians/profile";
    }

}
