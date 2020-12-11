package com.tgcoding.mealscalendar.service;

import com.tgcoding.mealscalendar.model.User;
import com.tgcoding.mealscalendar.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
