package com.tgcoding.mealscalendar.service;

import com.tgcoding.mealscalendar.exception.ResourceNotFoundException;
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

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}
