package com.tgcoding.mealscalendar.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class MealHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private int mealTime;

    @NotNull
    @Column(nullable = false)
    private LocalDate mealDate;

    @NotNull
    @Column(nullable = false)
    private int calories;
}
