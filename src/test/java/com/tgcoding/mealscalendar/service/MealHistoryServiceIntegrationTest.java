package com.tgcoding.mealscalendar.service;

import com.tgcoding.mealscalendar.model.MealHistory;
import com.tgcoding.mealscalendar.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MealHistoryServiceIntegrationTest {

    @Autowired
    private MealHistoryService mealHistoryService;

    @Autowired
    private UserService userService;

    private static User user = new User();
    private Long userId = null;
    private static final String EMAIL = "test@gmail.com";

    @Before
    synchronized public void setup() {
        if (user.getId() == null) {
            user.setEmail(EMAIL);
            user = userService.save(user);
            userId = user.getId();
        }
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void save_empty_name() throws Exception {
        MealHistory mealHistory = new MealHistory();
        mealHistoryService.save(mealHistory);

        fail();
    }

    @Test
    public void save_new() throws Exception {
        LocalDate date = LocalDate.now();

        MealHistory mealHistory = new MealHistory();
        mealHistory.setName("save_new");
        mealHistory.setUser(user);
        mealHistory.setMealDate(date);
        mealHistory.setMealTime(1);
        mealHistory.setCalories(800);
        MealHistory saved = mealHistoryService.save(mealHistory);

        assertThat("returned MealHistory id greater than 0", saved.getId(), greaterThan(0L));
        assertThat("MealHistory user is not null", mealHistory.getUser(), notNullValue());
        assertThat("MealHistory user saved correctly", mealHistory.getUser().getId(), is(userId));
        assertThat("parameter MealHistory id greater than 0", mealHistory.getId(), greaterThan(0L));
        assertThat("MealHistory name saved correctly", mealHistory.getName(), is("save_new"));
        assertThat("MealHistory meal time saved correctly", mealHistory.getMealTime(), is(1));
        assertThat("MealHistory calories saved correctly", mealHistory.getCalories(), is(800));
        assertThat("MealHistory meal date saved correctly", mealHistory.getMealDate(), is(date));
    }

    @Test
    public void getCurrentWeek() throws Exception {
        LocalDate now1 = LocalDate.of(2001, Month.AUGUST, 30);
        MealHistory meal1 = new MealHistory();
        meal1.setName("getCurrentMonth meal1");
        meal1.setUser(user);
        meal1.setMealDate(now1);
        mealHistoryService.save(meal1);

        LocalDate now2 = LocalDate.of(2001, Month.AUGUST, 30);
        MealHistory meal2 = new MealHistory();
        meal2.setName("getCurrentMonth meal2");
        meal2.setUser(user);
        meal2.setMealDate(now2);
        mealHistoryService.save(meal2);

        LocalDate now3 = LocalDate.of(2001, Month.AUGUST, 30);
        MealHistory meal3 = new MealHistory();
        meal3.setName("getCurrentMonth meal3");
        meal3.setUser(user);
        meal3.setMealDate(now3);
        mealHistoryService.save(meal3);

        LocalDate now4 = LocalDate.of(2001, Month.AUGUST, 23);
        MealHistory meal4 = new MealHistory();
        meal4.setName("getCurrentMonth meal4");
        meal4.setUser(user);
        meal4.setMealDate(now4);
        mealHistoryService.save(meal4);

        LocalDate now5 = LocalDate.of(2000, Month.AUGUST, 16);
        MealHistory meal5 = new MealHistory();
        meal5.setName("getCurrentMonth meal5");
        meal5.setUser(user);
        meal5.setMealDate(now5);
        mealHistoryService.save(meal5);

        Map<LocalDate, List<MealHistory>> map = mealHistoryService.getCurrentWeek(LocalDate.of(2001, Month.AUGUST, 30));

        LocalDate with3 = LocalDate.of(2001, Month.AUGUST, 30);
        LocalDate with1Aug23 = LocalDate.of(2001, Month.AUGUST, 23);
        LocalDate with1Aug16 = LocalDate.of(2001, Month.AUGUST, 16);
        assertThat("Aug 30 has size of 3", map.get(with3).size(), is(3));
        assertThat("Aug 23 has size of 1", map.get(with1Aug23).size(), is(1));
        assertNull("Aug 16 has size of 0", map.get(with1Aug16));
        assertThat("Aug 23 meal has correct name", map.get(with1Aug23).get(0).getName(), is("getCurrentMonth meal4"));
    }

    @Test
    public void groupByDate() {
        LocalDate now1 = LocalDate.now();
        MealHistory meal1 = new MealHistory();
        meal1.setName("meal1");
        meal1.setMealDate(now1);

        LocalDate now2 = LocalDate.now();
        MealHistory meal2 = new MealHistory();
        meal2.setName("meal2");
        meal2.setMealDate(now2);

        LocalDate now3 = LocalDate.now();
        MealHistory meal3 = new MealHistory();
        meal3.setName("meal3");
        meal3.setMealDate(now3);

        LocalDate now4 = LocalDate.now();
        now4 = now4.minusWeeks(1);
        MealHistory meal4 = new MealHistory();
        meal4.setName("meal4");
        meal4.setMealDate(now4);

        List<MealHistory> mealHistoryList = Stream.of(meal1, meal2, meal3, meal4).collect(Collectors.toCollection(ArrayList::new));

        Map<LocalDate, List<MealHistory>> byDay = MealHistoryService.groupByDate(mealHistoryList);

        assertNotNull(byDay);
        assertThat("Map is not empty", byDay.size(), greaterThan(0));
    }

    @Test
    public void groupByDate_merged() {
        LocalDate start = LocalDate.of(2018, Month.JULY, 22);
        LocalDate end = LocalDate.of(2018, Month.SEPTEMBER, 1);

        Map<LocalDate, List<MealHistory>> map = MealHistoryService.getAllDatesBetweenInclusiveAsMap(start, end);

        LocalDate now1 = LocalDate.of(2018, Month.AUGUST, 30);
        MealHistory meal1 = new MealHistory();
        meal1.setName("meal1");
        meal1.setMealDate(now1);

        LocalDate now2 = LocalDate.of(2018, Month.AUGUST, 30);
        MealHistory meal2 = new MealHistory();
        meal2.setName("meal2");
        meal2.setMealDate(now2);

        LocalDate now3 = LocalDate.of(2018, Month.AUGUST, 30);
        MealHistory meal3 = new MealHistory();
        meal3.setName("meal3");
        meal3.setMealDate(now3);

        LocalDate now4 = LocalDate.of(2018, Month.AUGUST, 23);
        MealHistory meal4 = new MealHistory();
        meal4.setName("meal4");
        meal4.setMealDate(now4);

        List<MealHistory> mealHistoryList = Stream.of(meal1, meal2, meal3, meal4).collect(Collectors.toCollection(ArrayList::new));

        Map<LocalDate, List<MealHistory>> byDay = MealHistoryService.groupByDate(mealHistoryList);

        map.putAll(byDay);

        LocalDate with3 = LocalDate.of(2018, Month.AUGUST, 30);
        LocalDate with1 = LocalDate.of(2018, Month.AUGUST, 23);
        assertThat("Aug 30 has size of 3", map.get(with3).size(), is(3));
        assertThat("Aug 23 has size of 1", map.get(with1).size(), is(1));
    }

    @Test
    public void findById() throws Exception {
        MealHistory mealHistory = new MealHistory();
        mealHistory.setName("findById");
        mealHistory.setUser(user);
        mealHistory.setMealDate(LocalDate.now());
        MealHistory saved = mealHistoryService.save(mealHistory);

        MealHistory fromDB = mealHistoryService.findById(saved.getId());

        assertNotNull("Good id should not return null",fromDB);
        assertThat("IDs match", fromDB.getId(), is(saved.getId()));
    }
}
