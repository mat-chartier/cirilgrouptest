package com.example.backend.config;

import org.springframework.stereotype.Component;

@Component
public class ForestFireConfiguration {
    public final int width = 50;
    public final int height = 30;
    public final float propagationProbability = 0.7f;
    public final int[][] initialBurningCells = {{0, 0}, {10, 10}, {20, 20}, {29, 30}, {29, 49}};
}