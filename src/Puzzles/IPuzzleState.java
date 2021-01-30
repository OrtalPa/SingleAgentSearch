package Puzzles;

import java.util.List;

public interface IPuzzleState {

    List<IPuzzleState> getNeighborStates();
    IPuzzle Problem();
    boolean isGoalState();
    IMove getStateLastMove();
    double getStateLastMoveCost();
    IPuzzleState getChildState(IMove move);
    void performMove(IMove move);
}
