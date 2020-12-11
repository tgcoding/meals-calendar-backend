package com.tgcoding.mealscalendar.service;

import com.tgcoding.mealscalendar.exception.KnownErrorException;
import com.tgcoding.mealscalendar.model.MealHistory;
import com.tgcoding.mealscalendar.repository.MealHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MealHistoryService {
    private final MealHistoryRepository mealHistoryRepository;

    public MealHistoryService(MealHistoryRepository mealHistoryRepository) {
        this.mealHistoryRepository = mealHistoryRepository;
    }

    public MealHistory save(MealHistory mealHistory) throws KnownErrorException {
        if (mealHistory == null) {
            throw new KnownErrorException("MealHistory is null");
        }

        // Spring Data JPA will insert a new row if the ID is bad, we want it to fail instead
        if (mealHistory.getId()!=null && mealHistory.getId()>0) {
            Optional<MealHistory> remote = mealHistoryRepository.findById(mealHistory.getId());
            if (remote.isEmpty()) {
                throw new KnownErrorException("ID does not exist");
            }
        }

        MealHistory saved = mealHistoryRepository.save(mealHistory);

        return saved;
    }

    public Iterable<MealHistory> getAll() {
        Iterable<MealHistory> items = mealHistoryRepository.findAll();
        return items;
    }

    public MealHistory findById(Long id) {
        Optional<MealHistory> mealHistory = mealHistoryRepository.findById(id);
        return mealHistory.orElse(null);
    }
}
