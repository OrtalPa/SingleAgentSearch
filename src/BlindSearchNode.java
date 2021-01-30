import Puzzles.IPuzzleState;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleState;

public class BlindSearchNode extends ASearchNode
{
	double	_g;
	int		_depth;
	double _stateLastMoveCost;

	public BlindSearchNode
	(
		IPuzzleState currentProblemState
	) 
	{
		_prev					= null;
		_currentProblemState 	= currentProblemState;
		_g 						= 0;
		_depth					= 0;
	}
	
	public BlindSearchNode
	(
		ASearchNode			prev,
		IPuzzleState 	currentProblemState,
		double 				g,
		int					depth
	) 
	{
		_prev					= prev;
		_currentProblemState 	= currentProblemState;
		_g 						= g;
		_depth					= depth;
	}
	
	@Override
	public double H()
	{
		return 0;
	}
	
	@Override
	public double G()
	{
		return _g;
	}
	
	@Override
	public double F() 
	{
		return _g;
	}
	
	public int Depth() 
	{
		return _depth;
	}

	@Override
	public ASearchNode createSearchNode
	(
		IPuzzleState currentProblemState
	) 
	{
		_stateLastMoveCost = currentProblemState.getStateLastMoveCost();
		double 		g		= _g + currentProblemState.getStateLastMoveCost();
		int			depth	= _depth + 1;
		ASearchNode newNode = new BlindSearchNode(this, currentProblemState, g, depth);
		return newNode;
	}

	@Override
	public double stateLastMoveCost() {
		return _stateLastMoveCost;
	}

	@Override
	public ASearchNode getCopy() {
		HeuristicSearchNode n = new HeuristicSearchNode(_currentProblemState);
		n._prev = this._prev;
		n._g = this._g;
		n._depth = this._depth;
		n._stateLastMoveCost = this._stateLastMoveCost;
		return n;
	}


}
