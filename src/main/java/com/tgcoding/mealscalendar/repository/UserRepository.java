package com.tgcoding.mealscalendar.repository;

import com.tgcoding.mealscalendar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //
}
