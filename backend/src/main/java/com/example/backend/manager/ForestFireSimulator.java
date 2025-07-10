package com.example.backend.manager;

import org.springframework.stereotype.Component;

import com.example.backend.config.ForestFireConfiguration;

@Component
public class ForestFireSimulator {

    public enum LandState {
        ALIVE, BURNING, BURNED
    }

    public record SimulationStep(LandState[][] states, boolean cellsStillBurning) {

    }

    public SimulationStep getNextStates(LandState[][] currentStates, float propagationProbability) {
        // Scan all cells and determine the next state.
        // For each cell, if it is BURNING, it will become BURNED.
        // If it is ALIVE, it may become BURNING based on the propagation probability

        // Copy current states to next states
        LandState[][] nextStates = new LandState[currentStates.length][currentStates[0].length];
        for (int i = 0; i < currentStates.length; i++) {
            System.arraycopy(currentStates[i], 0, nextStates[i], 0, currentStates[i].length);
        }

        // Iterate through each cell and apply the rules
        boolean cellsStillBurning = false;
        for (int x = 0; x < currentStates.length; x++) {
            for (int y = 0; y < currentStates[x].length; y++) {
                LandState currentCell = currentStates[x][y];
                if (currentCell == LandState.BURNING) {
                    nextStates[x][y] = LandState.BURNED;
                    if (Math.random() < propagationProbability) {
                        // Set fire to all 4 neighboring cells
                        if (x > 0 && currentStates[x - 1][y] == LandState.ALIVE) {
                            nextStates[x - 1][y] = LandState.BURNING;
                            cellsStillBurning = true;
                        }
                        if (x < currentStates.length - 1 && currentStates[x + 1][y] == LandState.ALIVE) {
                            nextStates[x + 1][y] = LandState.BURNING;
                            cellsStillBurning = true;
                        }
                        if (y > 0 && currentStates[x][y - 1] == LandState.ALIVE) {
                            nextStates[x][y - 1] = LandState.BURNING;
                            cellsStillBurning = true;
                        }
                        if (y < currentStates[x].length - 1 && currentStates[x][y + 1] == LandState.ALIVE) {
                            nextStates[x][y + 1] = LandState.BURNING;
                            cellsStillBurning = true;
                        }
                    }
                }
            }
        }
        return new SimulationStep(nextStates, cellsStillBurning);
    }

    public SimulationStep getInitialState(ForestFireConfiguration config) {
        // Initialize the forest with ALIVE cells
        // To be close to the later visual representation, the 2D array is organized as [height][width]
        // This means that the first index is the row (y-coordinate) and the second index
        // is the column (x-coordinate) in the visual representation.
        LandState[][] initialStates = new LandState[config.height][config.width];
        for (int x = 0; x < config.height; x++) {
            for (int y = 0; y < config.width; y++) {
                initialStates[x][y] = LandState.ALIVE;
            }
        }

        // Set the initial burning cells
        for (int[] cell : config.initialBurnedCells) {
            if (cell[0] >= 0 && cell[0] < config.height && cell[1] >= 0 && cell[1] < config.width) {
                initialStates[cell[0]][cell[1]] = LandState.BURNING;
            }
        }

        return new SimulationStep(initialStates, true);
    }
}
