package com.tgcoding.mealscalendar.repository;

import com.tgcoding.mealscalendar.model.MealHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealHistoryRepository extends CrudRepository<MealHistory, Long> {
    List<MealHistory> findAllByMealDateBetweenOrderByMealTime(LocalDate start, LocalDate end);
}
