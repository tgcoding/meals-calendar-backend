package com.tgcoding.mealscalendar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadyProbeController {

    @GetMapping("/ready")
    public String readyProbe() {
        return "Ready";
    }
}
