package com.tgcoding.mealscalendar.controller;

import com.tgcoding.mealscalendar.model.User;
import com.tgcoding.mealscalendar.security.CurrentUser;
import com.tgcoding.mealscalendar.security.UserPrincipal;
import com.tgcoding.mealscalendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId());
    }
}
