import Puzzles.IMove;
import Puzzles.IPuzzle;
import Puzzles.IPuzzleState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IDAStar implements ISearch {
    private int nodes;
    private int solFound;
    private AStarSearch searchParams;

    public IDAStar() {
        searchParams = new AStarSearch();
    }

    @Override
    public List<IMove> solve(IPuzzle problem) {
        nodes = 0;
        solFound = 0;
        IPuzzleState problemState	= problem.StartState();
        ASearchNode goal			= search(problemState);
        List<IMove>	solution		= goalNodeToSolutionPath(goal);
        return solution;
    }

    private ASearchNode search(IPuzzleState problemState) {
        searchParams.initLists();
        ASearchNode Vs 		= searchParams.createSearchRoot(problemState);
        double costLimit = Vs.G()+Vs.H(); //F (G=0)
        ASearchNode[] solution = new ASearchNode[]{null};
        while (costLimit != Double.MAX_VALUE){
            costLimit = depthSearch(solution, Vs, costLimit);
            if (solution[0] != null){
                return solution[0];
            }
        }
        return solution[0];
    }

    private double depthSearch(ASearchNode[] solution, ASearchNode current, double costLimit) {
        double minCost = current.H() + current.G();
        if (minCost > costLimit){
            //System.out.println("pruned");
            return minCost;
        }
        if (current.isGoal()){
            System.out.println("goal");
            solFound++;
            solution[0] = current;
            return costLimit;
        }
        double nextCostLimit = Double.MAX_VALUE;
        List<ASearchNode> neighbors = current.getNeighbors();
        nodes++;
        for (ASearchNode Vn : neighbors) {
            if (!isInCurrentNodeToStartPath(current, Vn)){
                double newCostLimit = depthSearch(solution, Vn, costLimit);
                if (solution[0] != null) {
//                    System.out.println("newCostLimit");
                    return newCostLimit;
                }
                nextCostLimit = Math.min(nextCostLimit, newCostLimit);
            }
        }
        //System.out.println("nextCostLimit");
        return nextCostLimit;
    }

    @Override
    public String getSolverName() {
        return "IDA*";
    }

    @Override
    public int amountOfNodesDeveloped() {
        return nodes;
    }

    @Override
    public int amountOfTimesInSol() {
        return solFound;
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

    private boolean isInCurrentNodeToStartPath(ASearchNode current, ASearchNode isInPath)
    {
        if (current == null)
            return false;
        ASearchNode currentNode = current.getCopy();
        while (currentNode._prev != null)
        {
            if (currentNode.equals(isInPath)){
                //System.out.println("skip");
                return true;
            }
            currentNode = currentNode._prev;
        }
        return false;
    }
}
