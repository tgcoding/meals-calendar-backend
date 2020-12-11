package com.tgcoding.mealscalendar.repository;

import com.tgcoding.mealscalendar.model.MealHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealHistoryRepository extends CrudRepository<MealHistory, Long> {
    //
}
