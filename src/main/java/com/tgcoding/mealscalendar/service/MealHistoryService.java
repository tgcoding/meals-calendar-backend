package com.tgcoding.mealscalendar.service;

import com.tgcoding.mealscalendar.exception.KnownErrorException;
import com.tgcoding.mealscalendar.model.MealHistory;
import com.tgcoding.mealscalendar.model.User;
import com.tgcoding.mealscalendar.repository.MealHistoryRepository;
import com.tgcoding.mealscalendar.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

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

    public Iterable<MealHistory> getAll(User user) {

        Iterable<MealHistory> items = mealHistoryRepository.findAllByUser_Id(user.getId());
        return items;
    }

    public Map<LocalDate, List<MealHistory>> getCurrentWeek(LocalDate date, User user) {
        LocalDate start = DateUtil.getFirstWeekDate(date);
        LocalDate end = DateUtil.getLastWeekDate(date);

        Map<LocalDate, List<MealHistory>> datesMap = getAllDatesBetweenInclusiveAsMap(start, end);
        List<MealHistory> mealHistoryList = mealHistoryRepository.findAllByUser_IdAndMealDateBetweenOrderByMealTime(user.getId(), start, end);
        Map<LocalDate, List<MealHistory>> mealHistoryMap = groupByDate(mealHistoryList);
        datesMap.putAll(mealHistoryMap);

        return datesMap;
    }

    public static Map<LocalDate, List<MealHistory>> getAllDatesBetweenInclusiveAsMap(LocalDate start, LocalDate end) {
        List<LocalDate> list = DateUtil.getAllDatesBetweenInclusive(start, end);

        Map<LocalDate, List<MealHistory>> map = list.stream().collect(Collectors.toMap(
                Function.identity(),
                item-> new ArrayList<>(),
                (k, v) -> {
                    throw new IllegalStateException("Duplicate key");
                },
                LinkedHashMap::new));

        return map;
    }

    public static Map<LocalDate, List<MealHistory>> groupByDate(List<MealHistory> list) {
        Map<LocalDate, List<MealHistory>> byDay = list.stream().collect(groupingBy(MealHistory::getMealDate, LinkedHashMap::new, toList()));

        return byDay;
    }

    public MealHistory findById(Long id) {
        Optional<MealHistory> mealHistory = mealHistoryRepository.findById(id);
        return mealHistory.orElse(null);
    }
}
