package Puzzles;

public interface IPuzzle {
    IPuzzleState StartState();
    IHeuristic Heuristic();
    int Size();
}
