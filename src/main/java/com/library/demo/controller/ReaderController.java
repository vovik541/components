package com.library.demo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.library.demo.config.security.CustomUserDetails;
import com.library.demo.entity.Book;
import com.library.demo.entity.User;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.repo.UserRepository;
import com.library.demo.response.BooksListResponse;
import com.library.demo.service.BookService;
import com.library.demo.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/v1/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;
    private final BookService bookService;

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public String readerMainPage(Model model) {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = principal.getUsername();
        User user = userRepository.findByLogin(userName);
        List<BookDTO> books = bookService.getBooksByUserId(user.getId());
        model.addAttribute("books", books);

        return "reader/main_page";
    }

    @GetMapping("/search")
    public String findBookPage(Model model, Book book) {
        List<BookDTO> books = bookService.getAllByName(book.getBookName());
        model.addAttribute("books", books);
        model.addAttribute("searchBook", new Book());

        return "reader/find_book";
    }

    @GetMapping("/find")
    public String findBookPage(Model model) {
        List<BookDTO> books = bookService.getAllAccessibleBooks();
        model.addAttribute("books", books);
        model.addAttribute("searchBook", new Book());

        return "reader/find_book";
    }

    @PostMapping("/take_book/{id}")
    public String updateBook(@PathVariable("id") Long id) {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = principal.getUsername();
        User user = userRepository.findByLogin(userName);
        BookDTO book = bookService.getById(id, userName).get();
        book.setUserId(user.getId());
        book.setBooked(true);
        bookService.updateBook(book);
        return "redirect:/v1/reader/profile";
    }

    @PostMapping("/give_back/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        BookDTO book = bookService.getById(id).get();
        book.setBooked(false);
        book.setUserId(null);
        bookService.updateBook(book);

        return "redirect:/v1/reader/profile";
    }


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllReaders() {
        List<User> users = readerService.findAllReaders();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<User> getReaderById(@PathVariable("id") Long id) {
        User user = readerService.findReaderById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addReader(@RequestBody User user) {
        User newUser = readerService.addReader(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateEmployee(@RequestBody User employee) {
        User updateEmployee = readerService.updateReader(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReader(@PathVariable("id") Long id) {
        readerService.deleteReader(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
