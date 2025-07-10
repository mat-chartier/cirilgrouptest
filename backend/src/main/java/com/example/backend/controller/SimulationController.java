package com.example.backend.controller;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.config.ForestFireConfiguration;
import com.example.backend.manager.ForestFireSimulator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/api/")
public class SimulationController {

    @Autowired
    private ForestFireConfiguration forestFireConfiguration;

    @Autowired
    private ForestFireSimulator forestFireSimulator;

    @PostMapping("/next")
    public String getNextSimulationStep(@RequestBody String simulationStateJson) {
        Gson gson = new GsonBuilder().create();
        if (simulationStateJson == null || simulationStateJson.isEmpty()) {
             return gson.toJson(forestFireSimulator.getInitialState(forestFireConfiguration));
        }
        ForestFireSimulator.LandState[][] currentStates = gson.fromJson(simulationStateJson, SimulationInput.class).getStates();
        ForestFireSimulator.SimulationStep nextStep = forestFireSimulator.getNextStates(currentStates, forestFireConfiguration.propagationProbability);
        return gson.toJson(nextStep);
    }
}
