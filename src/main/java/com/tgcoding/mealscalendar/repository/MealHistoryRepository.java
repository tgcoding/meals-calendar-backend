package com.tgcoding.mealscalendar.repository;

import com.tgcoding.mealscalendar.model.MealHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealHistoryRepository extends CrudRepository<MealHistory, Long> {
    List<MealHistory> findAllByUser_IdAndMealDateBetweenOrderByMealTime(Long userId, LocalDate start, LocalDate end);
    List<MealHistory> findAllByUser_Id(Long userId);
}
