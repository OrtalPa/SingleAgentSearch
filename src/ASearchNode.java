import Puzzles.IMove;
import Puzzles.IPuzzleState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

abstract public class ASearchNode 
{
	ASearchNode			_prev;
	IPuzzleState _currentProblemState;
	HashSet<ASearchNode> _previousNodesInPath;

	public List<ASearchNode> getNeighbors() 
	{
		List<ASearchNode> 	neighbors;
		List<IPuzzleState> neighborStates;
		try
		{
			neighbors 			= new ArrayList<>();
			neighborStates = _currentProblemState.getNeighborStates();

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

	public boolean isNodeInPath(ASearchNode node){
		if (_previousNodesInPath != null){
			return _previousNodesInPath.contains(node);
		}
		return false;
	}

	public void addToPath(ASearchNode node){
		if (_previousNodesInPath != null){
			_previousNodesInPath.add(node);
		}
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
