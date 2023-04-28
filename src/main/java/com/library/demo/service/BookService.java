package com.library.demo.service;

import com.google.gson.Gson;
import com.library.demo.entity.Book;
import com.library.demo.entity.dto.BookDTO;
import com.library.demo.mapper.BookMapper;
import com.library.demo.repo.BookRepository;
import com.library.demo.request.GetBookRequest;
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

        String url = userServiceUrl + "findBook";

        ResponseEntity<BooksListResponse> response = template.getForEntity(url, BooksListResponse.class);

        return response.getBody().getBooks();
    }

    public List<BookDTO> getAllByName(String name) {
        RestTemplate template = new RestTemplate();

        String url = userServiceUrl + "searchBook/{name}";


        URI uri = buildUrlForTemplate(new HashMap<>() {{
            put("name", name);
        }}, url);

        ResponseEntity<BooksListResponse> response = template.getForEntity(uri, BooksListResponse.class);

        return response.getBody().getBooks();
    }

    public Optional<BookDTO> getById(Long id, String userName) {
        String url = userServiceUrl + "takeBook";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate template = new RestTemplate();

        HttpEntity<String> request =
                new HttpEntity<String>(new Gson().toJson(new GetBookRequest(id, userName)), headers);

        ResponseEntity<BookDTO> responseEntityStr = template.
                postForEntity(url, request, BookDTO.class);

        return Optional.of(responseEntityStr.getBody());
    }
    public Optional<BookDTO> getById(Long id) {
        return Optional.of(mapper.bookToBookDTO(bookRepository.getById(id)));
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

        //todo


        mapper.bookToBookDTO(bookRepository.save(mapper.bookDTOToBook(book)));
    }
}
