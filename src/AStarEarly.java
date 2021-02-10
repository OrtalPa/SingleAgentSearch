import Puzzles.IMove;
import Puzzles.IPuzzle;
import Puzzles.IPuzzleState;
import Puzzles.TopSpinPuzzle.TopSpinPuzzle;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleMove;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarEarly implements ISearch
{
    private AStarSearch searchParams;
    private int amountOfNodesDeveloped;
    private int amountOfTimesInSol;
    private int duplicateNodes;

    public AStarEarly(){
        searchParams = new AStarSearch();
    }


    public List<IMove> solve (IPuzzle problem)
    {
        amountOfNodesDeveloped = 0;
        amountOfTimesInSol = 0;
        IPuzzleState 		problemState	= problem.StartState();
        ASearchNode				goal			= search(problemState);
        List<IMove>	solution		= goalNodeToSolutionPath(goal);
        return solution;
    }

    @Override
    public String getSolverName() {
        return "A* Early";
    }

    @Override
    public int amountOfNodesDeveloped() {
        return amountOfNodesDeveloped;
    }

    @Override
    public int amountOfTimesInSol() {
        return amountOfTimesInSol;
    }

    @Override
    public int getDuplicateNodes() {
        return duplicateNodes;
    }

    private	ASearchNode search(IPuzzleState problemState)
    {
        searchParams.initLists();
        ASearchNode Vs 		= searchParams.createSearchRoot(problemState);
        if (Vs.isGoal()){
            return Vs;
        }

        ASearchNode current	= null;
        ASearchNode solution = null;
        searchParams.addToOpen(Vs);
        double U = Double.MAX_VALUE;
        searchParams.updateFmin(Vs); // The minimal value currently in open
        while (searchParams.openSize() > 0 && searchParams.Fmin() < U)
        {
            current = searchParams.getBest();
            searchParams.addToClosed(current);
            List<ASearchNode> neighbors = current.getNeighbors();
            documentNode(current);
            for (ASearchNode Vn : neighbors)
            {
                /*if (searchParams.isClosed(Vn) || searchParams.isOpen(Vn)){
                    if (searchParams.getPreviousG(Vn) > Vn.G()){
                        searchParams.removeFromClosed(Vn);
                        searchParams.removeFromOpen(Vn);
                    }
                    else {
                        continue;
                    }
                }*/
                if ((!searchParams.isOpen(Vn) && !searchParams.isClosed(Vn))
                        || searchParams.getPreviousG(Vn) > Vn.G())
                {
                    if (Vn.isGoal()) {
                        if (Vn.G() < U) {
                            U = Vn.G();
                            solution = Vn;
                            searchParams.emptyOpenLessThanU(U);
                            amountOfTimesInSol++;
//                            System.out.println("hola");
                            // empty open from all nodes with V == U
                        }
                    }
                    if (Vn.G() + Vn.H() < U) {
                        searchParams.addToOpen(Vn);
                        // update fmin with the minimal f value from open
                        searchParams.updateFmin(Vn);
                    }
                }
            }
        }
        return solution;
    }

    private void documentNode(ASearchNode current) {
        amountOfNodesDeveloped++;
        if (searchParams.isVisited(current)){
            duplicateNodes++;
        }
        searchParams.addToVisited(current);
    }

    private List<IMove> goalNodeToSolutionPath
            (
                    ASearchNode goal
            )
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
