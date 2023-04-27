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
import java.util.Optional;

import static com.library.demo.util.RestTemplateUtils.buildUrlForTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${microservice.reader.ulr}")
    private String userServiceUrl;
    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public List<BookDTO> getAllAccessibleBooks() {
        RestTemplate template = new RestTemplate();

        String url = userServiceUrl + "find";

        ResponseEntity<BooksListResponse> response = template.getForEntity(url, BooksListResponse.class);

        return response.getBody().getBooks();
    }

    public List<BookDTO> getAllByName(String name) {
        RestTemplate template = new RestTemplate();

        String url = userServiceUrl + "search/{name}";


        URI uri = buildUrlForTemplate(new HashMap<>() {{
            put("name", name);
        }}, url);

        ResponseEntity<BooksListResponse> response = template.getForEntity(uri, BooksListResponse.class);

        return response.getBody().getBooks();
    }

    public Optional<BookDTO> getById(Long id) {
        RestTemplate template = new RestTemplate();

        String url = userServiceUrl + "take_book/{id}";


        URI uri = buildUrlForTemplate(new HashMap<>() {{
            put("id", id.toString());
        }}, url);

        ResponseEntity<BookDTO> response = template.getForEntity(uri, BookDTO.class);

        return Optional.of(response.getBody());
    }

    public List<BookDTO> getBooksByUserId(Long userId) {
        RestTemplate template = new RestTemplate();

        String url = userServiceUrl + "profile/{id}";

        URI uri = buildUrlForTemplate(new HashMap<>() {{
            put("id", userId.toString());
        }}, url);

        ResponseEntity<BooksListResponse> response = template.getForEntity(uri, BooksListResponse.class);

        return response.getBody().getBooks();
    }

    public void updateBook(BookDTO book) {




        mapper.bookToBookDTO(bookRepository.save(mapper.bookDTOToBook(book)));
    }
}
