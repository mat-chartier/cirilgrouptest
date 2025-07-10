package com.example.backend.config;

import org.springframework.stereotype.Component;

@Component
public class ForestFireConfiguration {
    public final int width = 2;
    public final int height = 2;
    public final float propagationProbability = 1f;
    public final int[][] initialBurnedCells = {{0, 0}};
}