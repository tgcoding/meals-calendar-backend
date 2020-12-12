package com.tgcoding.mealscalendar.service;

import com.tgcoding.mealscalendar.exception.KnownErrorException;
import com.tgcoding.mealscalendar.model.MealHistory;
import com.tgcoding.mealscalendar.repository.MealHistoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class MealHistoryServiceTest {

    @Mock
    private MealHistoryRepository mealHistoryRepository;

    @InjectMocks
    private MealHistoryService mealHistoryService;

    @Test
    public void save_new() throws Exception {
        MealHistory mealHistory = new MealHistory();

        when(mealHistoryRepository.save(Mockito.any())).thenReturn(new MealHistory());

        MealHistory saved = mealHistoryService.save(mealHistory);
        assertNotNull(saved);
    }

    @Test
    public void save_existing_bad_id() {
        MealHistory mealHistory = new MealHistory();
        mealHistory.setId(1L);
        when(mealHistoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Exception e = Assert.assertThrows("Exception is thrown", KnownErrorException.class, () -> {
            mealHistoryService.save(mealHistory);
        });
        assertThat("Exception message is correct", e.getMessage(), is("ID does not exist"));
    }

    @Test
    public void save_existing_good_id() throws Exception {
        MealHistory mealHistory = new MealHistory();
        mealHistory.setId(1L);

        when(mealHistoryRepository.findById(Mockito.any())).thenReturn(Optional.of(new MealHistory()));
        when(mealHistoryRepository.save(Mockito.any())).thenReturn(new MealHistory());

        MealHistory savedMealHistory = mealHistoryService.save(mealHistory);
        assertNotNull(savedMealHistory);
    }

    @Test
    public void saveThrowsException_nullObject() {
        Exception e = Assert.assertThrows("Exception is thrown", KnownErrorException.class, () -> {
            mealHistoryService.save(null);
        });
        assertThat("Exception message is correct", e.getMessage(), is("MealHistory is null"));
    }

    @Test
    public void getAll() {
        Iterable<MealHistory> allMeals = mealHistoryService.getAll();
        assertNotNull(allMeals);
    }

    @Test
    public void findById_success() {
        when(mealHistoryRepository.findById(Mockito.any())).thenReturn(Optional.of(new MealHistory()));

        MealHistory mealHistory = mealHistoryService.findById(1L);
        assertNotNull("Return MealHistory if good id", mealHistory);
    }

    @Test
    public void findById_fail() {
        when(mealHistoryRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        MealHistory mealHistory = mealHistoryService.findById(1L);
        assertNull("Return null if bad id", mealHistory);
    }
}
