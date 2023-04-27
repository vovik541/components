package com.library.demo.service;

import com.library.demo.exception.ReaderWasNotFoundException;
import com.library.demo.entity.User;
import com.library.demo.mapper.UserMapper;
import com.library.demo.repo.UserRepository;
import com.library.demo.response.BooksListResponse;
import com.library.demo.response.UsersListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

import static com.library.demo.util.RestTemplateUtils.buildUrlForTemplate;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final UserRepository userRepository;
    @Value("${microservice.reader.ulr}")
    private String userServiceUrl;

    private UserMapper mapper;

    public User addReader(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllReaders() {
        RestTemplate template = new RestTemplate();

        String url = userServiceUrl + "all";

        URI uri = buildUrlForTemplate(new HashMap<>(), url);

        ResponseEntity<UsersListResponse> response = template.getForEntity(uri, UsersListResponse.class);

        return mapper.userDTOsToUser(response.getBody().getUserDTOS()) ;
    }

    public User updateReader(User user) {
        return userRepository.save(user);
    }

    public void deleteReader(Long id) {
        userRepository.deleteUserById(id);
    }

    public User findReaderById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new ReaderWasNotFoundException("Reader with id: " + id + "wa not found"));
    }
}
