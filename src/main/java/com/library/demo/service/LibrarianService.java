package com.library.demo.service;

import com.library.demo.entity.Book;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.mapper.BookMapper;
import com.library.demo.repo.BookRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import static com.library.demo.util.RestTemplateUtils.buildUrlForTemplate;

@Service
@RequiredArgsConstructor
public class LibrarianService {

    @Value("${microservice.librarian.ulr}")
    private String librarianMicroserviceUrl;

    private final BookMapper mapper;

    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public BookDTO findById(Long id) {

        RestTemplate template = new RestTemplate();

        String url = librarianMicroserviceUrl + "edit/{id}";


        URI uri = buildUrlForTemplate(new HashMap<>() {{
            put("id", id.toString());
        }}, url);

        ResponseEntity<BookDTO> response = template.getForEntity(uri, BookDTO.class);

        return response.getBody();
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

}
