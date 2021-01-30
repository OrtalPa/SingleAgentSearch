import Puzzles.IMove;
import Puzzles.IPuzzleState;
import Puzzles.NPuzzle.NPuzzleState;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleMove;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleState;

import java.util.ArrayList;
import java.util.List;

abstract public class ASearchNode 
{
	ASearchNode			_prev;
	IPuzzleState _currentProblemState;
	
	public List<ASearchNode> getNeighbors() 
	{
		List<ASearchNode> 	neighbors = null;
		List<IPuzzleState> neighborStates = null;
		try
		{
			/*List<ASearchNode> 	*/neighbors 			= new ArrayList<ASearchNode>();
			/*List<IPuzzleState> */neighborStates = _currentProblemState.getNeighborStates();

			for (IPuzzleState state : neighborStates)
			{
				ASearchNode newNode = createSearchNode(state);
				neighbors.add(newNode);
			}
			return neighbors;
		}
		catch (Exception e){
			System.out.println("ex");
			return null;
		}

	}
	
	public boolean isGoal()
	{
		return _currentProblemState.isGoalState();
	}
	
	public IMove getLastMove()
	{
		return _currentProblemState.getStateLastMove();
	}
	
	@Override
	public boolean equals
	(
		Object o
	) 
	{
		if (this == o) return true;
		if (!(o instanceof ASearchNode)) 
			return false;

		ASearchNode that = (ASearchNode) o;
		return _currentProblemState.equals(that._currentProblemState);
	}

	@Override
	public int hashCode() 
	{
		return _currentProblemState.hashCode();
	}
	
	abstract public double 		H();
	
	abstract public double 		G();
	
	abstract public double 		F();
	
	abstract public int			Depth();

	abstract public double stateLastMoveCost();

	abstract public ASearchNode getCopy();
	
	abstract public ASearchNode createSearchNode(IPuzzleState currentProblemState);

}
