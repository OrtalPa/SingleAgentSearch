import Puzzles.IHeuristic;
import Puzzles.IPuzzleState;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleHeuristic;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleState;

public class HeuristicSearchNode extends BlindSearchNode
{
	double					_h;
	IHeuristic _heuristic;
	
	public HeuristicSearchNode(IPuzzleState currentProblemState	)
	{
		super(currentProblemState);
		_heuristic 	= currentProblemState.Problem().Heuristic();
		_h			= _heuristic.getHeuristic(currentProblemState);
	}
	
	
	public HeuristicSearchNode
	(
		ASearchNode				prev,
		IPuzzleState currentProblemState,
		double 					g,
		int						depth,
		IHeuristic 	heuristic
	) 
	{
		super(prev, currentProblemState, g, depth);
		_heuristic 	= heuristic;
		_h			= _heuristic.getHeuristic(currentProblemState);
	}
	
	@Override
	public double H()
	{
		return _h;
	}
	
	
	@Override
	public double F()
	{
		return _g + _h;
	}
	
	
	@Override
	public ASearchNode createSearchNode
	(
		IPuzzleState 	currentProblemState
	) 
	{
		double 		g		= _g + currentProblemState.getStateLastMoveCost();
		_stateLastMoveCost = currentProblemState.getStateLastMoveCost();
		int			depth	= _depth + 1;
		ASearchNode newNode = new HeuristicSearchNode(this, currentProblemState, g, depth, _heuristic);
		return newNode;
	}

	public ASearchNode getCopy(){
		HeuristicSearchNode n = new HeuristicSearchNode(_currentProblemState);
		n._prev = this._prev;
		n._g = this._g;
		n._depth = this._depth;
		n._stateLastMoveCost = this._stateLastMoveCost;
		return n;
	}

	@Override
	public String toString() {
		return _currentProblemState.toString();
	}
}
