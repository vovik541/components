package com.library.demo.repo;

import com.library.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteUserById(Long id);
    Optional<User> findUserById(Long id);
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
    User findByLogin(String login);
}
