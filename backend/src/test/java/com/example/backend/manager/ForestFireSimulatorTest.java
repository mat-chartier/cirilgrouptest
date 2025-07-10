package com.example.backend.manager;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForestFireSimulatorTest {

    ForestFireSimulator simulator = new ForestFireSimulator();

    @Nested
    class OneCellLandState {

        ForestFireSimulator.LandState[][] initialStates = {
            {ForestFireSimulator.LandState.ALIVE}
        };

        @Test
        void testAliveCellRemainsAlive() {

            ForestFireSimulator.SimulationStep step = simulator.getNextStates(initialStates, 1f);

            // Assert that the next state is still ALIVE
            assert step.states()[0][0] == ForestFireSimulator.LandState.ALIVE;
        }

        @Test
        void testBurningCellBecomesBurned() {
            initialStates[0][0] = ForestFireSimulator.LandState.BURNING;
            ForestFireSimulator.SimulationStep step = simulator.getNextStates(initialStates, 1f);
            // Assert that the next state is BURNED
            assert step.states()[0][0] == ForestFireSimulator.LandState.BURNED;
        }

        @Test
        void testBurnedCellRemainsBurned() {
            initialStates[0][0] = ForestFireSimulator.LandState.BURNED;
            ForestFireSimulator.SimulationStep step = simulator.getNextStates(initialStates, 1f);
            // Assert that the next state is still BURNED
            assert step.states()[0][0] == ForestFireSimulator.LandState.BURNED;
        }
    }

    @Nested
    class FourCellsLandState {

        ForestFireSimulator.LandState[][] initialStates = {
            {ForestFireSimulator.LandState.ALIVE, ForestFireSimulator.LandState.ALIVE},
            {ForestFireSimulator.LandState.ALIVE, ForestFireSimulator.LandState.ALIVE}
        };

        @Test
        void testBurningCellPropagatesToNeighbors() {
            initialStates[0][0] = ForestFireSimulator.LandState.BURNING;
            ForestFireSimulator.SimulationStep step = simulator.getNextStates(initialStates, 1f);

            // Assert that the neighboring cells become BURNING and then BURNED
            assert step.states()[0][1] == ForestFireSimulator.LandState.BURNING;
            assert step.states()[1][0] == ForestFireSimulator.LandState.BURNING;
            assert step.states()[0][0] == ForestFireSimulator.LandState.BURNED;

            step = simulator.getNextStates(step.states(), 1f);
            assert step.states()[0][1] == ForestFireSimulator.LandState.BURNED;
            assert step.states()[1][0] == ForestFireSimulator.LandState.BURNED;
            assert step.states()[0][0] == ForestFireSimulator.LandState.BURNED;
            assert step.states()[1][1] == ForestFireSimulator.LandState.BURNING;

            step = simulator.getNextStates(step.states(), 1f);
            assert step.states()[0][1] == ForestFireSimulator.LandState.BURNED;
            assert step.states()[1][0] == ForestFireSimulator.LandState.BURNED;
            assert step.states()[0][0] == ForestFireSimulator.LandState.BURNED;
            assert step.states()[1][1] == ForestFireSimulator.LandState.BURNED;
        }

        @Test
        void testNoPropagationWhenNoProbability() {
            initialStates[0][0] = ForestFireSimulator.LandState.BURNING;
            ForestFireSimulator.SimulationStep step = simulator.getNextStates(initialStates, 0f);
            // Assert that no neighboring cells become BURNING
            assert step.states()[0][1] == ForestFireSimulator.LandState.ALIVE;
            assert step.states()[1][0] == ForestFireSimulator.LandState.ALIVE;
            assert step.states()[1][1] == ForestFireSimulator.LandState.ALIVE;
            assert step.states()[0][0] == ForestFireSimulator.LandState.BURNED;
            assert !step.cellsStillBurning();
        }
    }
}
