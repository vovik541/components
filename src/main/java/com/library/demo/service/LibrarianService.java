package com.library.demo.service;

import com.google.gson.Gson;
import com.library.demo.entity.Book;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.mapper.BookMapper;
import com.library.demo.repo.BookRepository;
import com.library.demo.response.BooksListResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public void addBook(BookDTO book) {
        String url = librarianMicroserviceUrl + "/addBook";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate template = new RestTemplate();

        HttpEntity<String> request =
                new HttpEntity<String>(new Gson().toJson(book), headers);

        ResponseEntity<BookDTO> responseEntityStr = template.
                postForEntity(url, request, BookDTO.class);
    }

    public List<BookDTO> findAllBooks() {
        RestTemplate template = new RestTemplate();

        String url = librarianMicroserviceUrl + "profile";

        ResponseEntity<BooksListResponse> response = template.getForEntity(url, BooksListResponse.class);

        return response.getBody().getBooks();
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
        RestTemplate template = new RestTemplate();

        String url = librarianMicroserviceUrl + "delete/" + id.toString();

        template.delete(url);
    }

    public void updateBook(BookDTO book) {
        String url = librarianMicroserviceUrl + "/update/" + book.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate template = new RestTemplate();

        HttpEntity<String> request =
                new HttpEntity<String>(new Gson().toJson(book), headers);

        ResponseEntity<BookDTO> responseEntityStr = template.
                postForEntity(url, request, BookDTO.class);
    }

}
