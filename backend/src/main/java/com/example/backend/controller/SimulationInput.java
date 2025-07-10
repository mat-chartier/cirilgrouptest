package com.example.backend.controller;

import com.example.backend.manager.ForestFireSimulator;

public class SimulationInput {
    private ForestFireSimulator.LandState[][] states;

    public ForestFireSimulator.LandState[][] getStates() {
        return states;
    }
}
