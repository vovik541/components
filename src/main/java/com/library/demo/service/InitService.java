package com.library.demo.service;

import com.library.demo.config.Roles;
import com.library.demo.model.User;
import com.library.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        User user = userRepository.findByLogin("librarian");

        if (user == null){
            user = new User();
            user.setEmail("librarian@kpi.ua");
            user.setRole(Roles.LIBRARIAN.name());
            user.setPassword(encoder.encode("123456"));
            user.setFirstName("Natalia");
            user.setLastName("Last");
            user.setLogin("librarian");

            userRepository.save(user);
        }
    }
}
