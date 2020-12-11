package com.tgcoding.mealscalendar.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class DateUtil {
    public static LocalDate getFirstWeekDate(LocalDate date) {
        LocalDate now = date == null ? LocalDate.now() : date;
        LocalDate previousWeek = now.minusWeeks(1);
        LocalDate first = previousWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        return first;
    }

    public static LocalDate getLastWeekDate(LocalDate date) {
        LocalDate now = date == null ? LocalDate.now() : date;
        LocalDate last = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        return last;
    }

    public static List<LocalDate> getAllDatesBetweenInclusive(LocalDate start, LocalDate end) {
        long numDays = ChronoUnit.DAYS.between(start, end) + 1L;
        List<LocalDate> list = IntStream.iterate(0, i -> i+1)
                .limit(numDays)
                .mapToObj(start::plusDays)
                .collect(toList());

        return list;
    }
}
