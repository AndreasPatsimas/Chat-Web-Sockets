package org.patsimas.chat.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.domain.User;
import org.patsimas.chat.exceptions.ResourceNotFoundException;
import org.patsimas.chat.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtils {

    private final UserRepository userRepository;

    public User fetch(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            throw new ResourceNotFoundException("There is no user with email: " + email);

        return user.get();
    }

    public User fetch(long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new ResourceNotFoundException("There is no user with id: " + userId);

        return user.get();
    }
}
