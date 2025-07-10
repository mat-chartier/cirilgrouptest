package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SimulationController {

    @GetMapping("/next")
    public String getNextSimulationStep() {
        // Logic to get the next simulation step
        return "{\"msg\":\"Next simulation step\"}";
    }
}
