package com.library.demo.service;

import com.library.demo.exception.ReaderWasNotFoundException;
import com.library.demo.model.User;
import com.library.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final UserRepository userRepository;

    public User addReader(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllReaders() {
        return userRepository.findAll();
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
