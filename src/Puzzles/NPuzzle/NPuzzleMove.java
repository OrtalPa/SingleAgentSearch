package Puzzles.NPuzzle;

import Puzzles.IMove;

/**
 * Represents how the empty cell moves in the board
 */
public enum NPuzzleMove implements IMove {
    LEFT
    {
        public String toString()
        {
            return "LEFT";
        }
    },
    RIGHT
    {
        public String toString()
        {
            return "RIGHT";
        }
    },
    UP
    {
        public String toString()
        {
            return "UP";
        }
    },
    DOWN
    {
        public String toString()
        {
            return "DOWN";
        }
    }
}
