import Puzzles.IMove;
import Puzzles.IPuzzle;
import Puzzles.IPuzzleState;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleMove;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarLate implements ISearch
{
	private AStarSearch searchParams;
	private int amountOfNodesDeveloped;

	public AStarLate(){
		searchParams = new AStarSearch();
	}

	public List<IMove> solve(IPuzzle problem)
	{
		amountOfNodesDeveloped = 0;
		IPuzzleState problemState	= problem.StartState();
		ASearchNode				goal			= search(problemState);
		List<IMove>	solution		= goalNodeToSolutionPath(goal);
		return solution;
	}

	@Override
	public String getSolverName() {
		return "A* Late";
	}

	@Override
	public int amountOfNodesDeveloped() {
		return amountOfNodesDeveloped;
	}

	@Override
	public int amountOfTimesInSol() {
		return 1;
	}

	private	ASearchNode search(IPuzzleState problemState)
	{
		searchParams.initLists();
		ASearchNode Vs 		= searchParams.createSearchRoot(problemState);
		ASearchNode current	= null;
		searchParams.addToOpen(Vs);
		
		while (searchParams.openSize() > 0)
		{
			current = searchParams.getBest();
			if (current.isGoal()){
//				System.out.println(current.toString());
				return current;
			}
			List<ASearchNode> neighbors = current.getNeighbors();
			amountOfNodesDeveloped++;
			for (ASearchNode Vn : neighbors)
			{
				if ((!searchParams.isOpen(Vn) && !searchParams.isClosed(Vn))
						|| searchParams.getPreviousG(Vn) > Vn.G()){
					searchParams.addToOpen(Vn);}

				/*if (searchParams.isClosed(Vn))
					continue;

				if (!searchParams.isOpen(Vn) || searchParams.getOpen(Vn).G() > Vn.G())
					searchParams.addToOpen(Vn);*/
			}
			searchParams.addToClosed(current);
		}
		return null;
	}
	
	private List<IMove> goalNodeToSolutionPath(ASearchNode goal)
	{
		if (goal == null)
			return null;
		ASearchNode 			currentNode		= goal;
		List<IMove> solutionPath 	= new ArrayList<>();
		while (currentNode._prev != null)
		{
			solutionPath.add(currentNode.getLastMove());
			currentNode = currentNode._prev;
		}
		Collections.reverse(solutionPath);
		return solutionPath;
	}
}
