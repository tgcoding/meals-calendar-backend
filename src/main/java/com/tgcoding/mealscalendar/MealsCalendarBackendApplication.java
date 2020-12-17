package com.tgcoding.mealscalendar;

import com.tgcoding.mealscalendar.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MealsCalendarBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealsCalendarBackendApplication.class, args);
    }

}
