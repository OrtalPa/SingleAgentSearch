package Puzzles.TopSpinPuzzle;

import Puzzles.IMove;

public enum TopSpinPuzzleMove implements IMove
{
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
    SWAP
    {
        public String toString()
        {
            return "SWAP";
        }
	}
}

