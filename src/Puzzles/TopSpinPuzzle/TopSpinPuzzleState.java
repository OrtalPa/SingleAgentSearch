package Puzzles.TopSpinPuzzle;

import Puzzles.IMove;
import Puzzles.IPuzzle;
import Puzzles.IPuzzleState;

import java.util.ArrayList;
import java.util.List;

public class TopSpinPuzzleState implements IPuzzleState
{
	TopSpinPuzzle		_problem;		// The original problem
	int[] 				_TopSpinPuzzle;	// Current state
	IMove	_lastMove;		// The last move made
	
	
	public TopSpinPuzzleState(TopSpinPuzzle problem, int[] TopSpinPuzzle,IMove lastMove)
	{
		_problem		= problem;
		_TopSpinPuzzle	= TopSpinPuzzle.clone();
		_lastMove		= lastMove;
	}

	
	public List<IPuzzleState> getNeighborStates()
	{
		List<IPuzzleState>	neighborStates 	= new ArrayList<>();
		List<IMove>		legalMoves 		= getLegalMoves();
		
		for (IMove move : legalMoves)
		{
			IPuzzleState newState = getChildState(move);
			neighborStates.add(newState);
		}
		return neighborStates;
	}
	
	private List<IMove> getLegalMoves()
	{
		List<IMove> newList = new ArrayList<>();
		TopSpinPuzzleMove leftMove 	= TopSpinPuzzleMove.LEFT;
		TopSpinPuzzleMove rightMove = TopSpinPuzzleMove.RIGHT;
		TopSpinPuzzleMove swapMove 	= TopSpinPuzzleMove.SWAP;
		newList.add(leftMove);
		newList.add(swapMove);
		newList.add(rightMove);

		// Add only the next moves and not the father move
		/*if (_lastMove == null){
			newList.add(leftMove);
			newList.add(swapMove);
			newList.add(rightMove);
		}
		else if (_lastMove == Puzzles.TopSpinPuzzle.TopSpinPuzzleMove.LEFT){
			newList.add(leftMove);
			newList.add(swapMove);
		}
		else if (_lastMove == Puzzles.TopSpinPuzzle.TopSpinPuzzleMove.RIGHT) {
			newList.add(rightMove);
			newList.add(swapMove);
		}
		else if (_lastMove == Puzzles.TopSpinPuzzle.TopSpinPuzzleMove.SWAP) {
			newList.add(rightMove);
			newList.add(leftMove);
		}*/
		return newList;
	}
	
	public IPuzzle Problem()
	{
		return _problem;
	}
	
	public boolean isGoalState() 
	{
		int size = _problem._size;
		
		for (int cellIndex = 0; cellIndex < size; cellIndex ++)
			if (_TopSpinPuzzle[cellIndex] != cellIndex)
				return false;
		return true;
	}

	public IMove getStateLastMove()
	{
		return _lastMove;
	}
	
	public double getStateLastMoveCost() 
	{
		if (_lastMove != null){
			return 1;
		}
		return 0;
		/*if (	_lastMove == TopSpinPuzzleMove.SWAP)
		{
			int startCellIndex 	= _problem._size / 2 - 2;
			int endCellIndex 	= startCellIndex + 3;
			int cost	 		= 0;
			for (int index = startCellIndex; index <= endCellIndex; index++)
				cost += _TopSpinPuzzle[index];
			return cost;
		}
		if (	_lastMove == TopSpinPuzzleMove.RIGHT	||
				_lastMove == TopSpinPuzzleMove.LEFT)
			return 1;
		else
			return 0;*/
	}

	public IPuzzleState getChildState(IMove topSpinPuzzleMove)
	{
		IPuzzleState newState = new TopSpinPuzzleState(_problem, _TopSpinPuzzle, topSpinPuzzleMove);
		newState.performMove(topSpinPuzzleMove);
		return newState;
	}
	
	public void performMove(IMove topSpinPuzzleMove)
	{
		// Perform the action
		if (topSpinPuzzleMove == TopSpinPuzzleMove.LEFT)
			performLeftMove();
		else if (topSpinPuzzleMove == TopSpinPuzzleMove.RIGHT)
			performRightMove();
		else if (topSpinPuzzleMove == TopSpinPuzzleMove.SWAP)
			performSwapMove();
	}
	
	private void performLeftMove()
	{
		int cellZero = _TopSpinPuzzle[0];
		for (int cellIndex = 0; cellIndex < _problem.Size() - 1; cellIndex++)
		{
			_TopSpinPuzzle[cellIndex] = _TopSpinPuzzle[cellIndex + 1];
		}
		_TopSpinPuzzle[_problem.Size() - 1] = cellZero;
	}
	
	private void performRightMove()
	{
		int lastCell = _TopSpinPuzzle[_problem.Size() - 1];
		for (int cellIndex = _problem.Size() - 1; cellIndex > 0; cellIndex --)
		{
			_TopSpinPuzzle[cellIndex] = _TopSpinPuzzle[cellIndex - 1];
		}
		_TopSpinPuzzle[0] = lastCell;
	}
	
	private void performSwapMove()
	{
		int startCellIndex 	= _problem.Size() / 2 - 2;
		int endCellIndex 	= startCellIndex + 3;
		int tempValue;
		
		tempValue 							= _TopSpinPuzzle[startCellIndex];
		_TopSpinPuzzle[startCellIndex]		= _TopSpinPuzzle[endCellIndex];
		_TopSpinPuzzle[endCellIndex]		= tempValue;
		
		tempValue 							= _TopSpinPuzzle[startCellIndex + 1];
		_TopSpinPuzzle[startCellIndex + 1] 	= _TopSpinPuzzle[endCellIndex	- 1];
		_TopSpinPuzzle[endCellIndex   - 1]	= tempValue;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof TopSpinPuzzleState)) return false;

		TopSpinPuzzleState that = (TopSpinPuzzleState) o;

		return java.util.Arrays.equals(_TopSpinPuzzle, that._TopSpinPuzzle);

	}

	@Override
	public int hashCode() {
		return java.util.Arrays.hashCode(_TopSpinPuzzle);
	}

	
	private int[] getTopSpinPuzzleCopy()
	{
		int 	size				= _problem._size;
		int[] 	newTopSpinPuzzle	= new int[size];
		
		for (int index = 0; index < size; index ++)
		{
				newTopSpinPuzzle[index] = _TopSpinPuzzle[index];
		}
		return newTopSpinPuzzle;
	}

	@Override
	public String toString()
	{
		int size = _problem._size;
		String toPrint = "| ";
		for(int index = 0; index < size; index ++)
		{
			toPrint += _TopSpinPuzzle[index] + " | ";
		}
		return toPrint;
	}

}
