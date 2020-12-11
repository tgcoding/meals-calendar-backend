package com.tgcoding.mealscalendar.controller;

import com.tgcoding.mealscalendar.exception.KnownErrorException;
import com.tgcoding.mealscalendar.model.MealHistory;
import com.tgcoding.mealscalendar.service.MealHistoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mealhistory")
public class MealHistoryController {
    private final MealHistoryService mealHistoryService;

    public MealHistoryController(MealHistoryService mealHistoryService) {
        this.mealHistoryService = mealHistoryService;
    }

    @PostMapping(
            value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public MealHistory save(@RequestBody MealHistory mealHistory) throws KnownErrorException {
        MealHistory saved = mealHistoryService.save(mealHistory);
        return saved;
    }

    @GetMapping("/")
    public Iterable<MealHistory> getAll() {
        Iterable<MealHistory> items = mealHistoryService.getAll();
        return items;
    }

    @GetMapping("/{id}")
    public MealHistory getById(@PathVariable("id") long id) {
        MealHistory item = mealHistoryService.findById(id);
        return item;
    }
}
