import Puzzles.IPuzzleState;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleState;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarSearch
{
	// Define lists here ...
	private HashMap<ASearchNode,ASearchNode> openListHash;
	private HashMap<ASearchNode,ASearchNode> closedListHash;
	private HashSet<ASearchNode> closedList;
	private PriorityQueue<ASearchNode> openList;
	private double Fmin;

	public double Fmin() {
		return Fmin;
	}

	public ASearchNode createSearchRoot(IPuzzleState problemState)
	{	
		ASearchNode newNode = new HeuristicSearchNode(problemState);
		return newNode;
	}

	public void initLists() 
	{
		openList = new PriorityQueue<>(new Comparator<ASearchNode>() {
		@Override
		public int compare(ASearchNode o1, ASearchNode o2) {
			double o1Weight = o1.H()+o1.G();
			double o2Weight = o2.H()+o2.G();
			if(o1Weight > o2Weight)
				return 1;
			else if(o2Weight > o1Weight)
			    return -1;
			else if (o1.H() > o2.H()){
				return 1;
			}
			return -1;
			/*
			else // if(o1Weight < o1Weight)
				return -1;
			//if weight is equal - compare heuristics and take the highest
			//return 0;//equal
			*/
			/*
			 else if(o2Weight >o1Weight ){
			 	return -1;
			}
			 else if(o1.H() < o2.H()){
			 	return 1;
			}
			 return -1;
			 */

		}
	});
		openListHash = new HashMap<>();
		closedListHash = new HashMap<>();
		closedList = new HashSet<>();
		Fmin = Double.MAX_VALUE;
	}

	public ASearchNode getOpen(ASearchNode node)
	{
		return openListHash.get(node);
	}

	public boolean isOpen(ASearchNode node)
	{
		return openListHash.containsKey(node);
	}

	public boolean isClosed(ASearchNode node)
	{
		return closedList.contains(node);
	}

	public void addToOpen(ASearchNode node)
	{
		removeFromClosed(node);
		openList.add(node);
		openListHash.put(node, node);
	}

	public void addToClosed(ASearchNode node)
	{
		//the node was removed from open in getBest, only need to be added to the closed list
		closedList.add(node);
		closedListHash.put(node, node);
	}

	public int openSize() 
	{
		return openListHash.size();
	}

	public ASearchNode getBest()
	{
		ASearchNode ans = null;
		//remove from both open lists
        boolean found = false;
        while (!found){
			ans = openList.poll();
			if(openListHash.remove(ans)!=null)
				found = true;
		}
        return ans;
	}

	public void removeFromOpen(ASearchNode node) {
		openList.remove(node);
		openListHash.remove(node, node);
	}

	public void removeFromClosed(ASearchNode node) {
		closedList.remove(node);
		closedListHash.remove(node, node);
	}

	public void updateFmin(ASearchNode node) {
		if (node.G() + node.H() < Fmin){
			Fmin = node.G() + node.H();
		}
	}

	public double getPreviousG(ASearchNode Vn) {
		ASearchNode open = getOpen(Vn);
		if (open != null){
			return open.G();
		}
		ASearchNode closed = closedListHash.get(Vn);
		if (closed != null){
			//System.out.println("duplicate");
			return closed.G();
		}
		return -1;
	}

	public void emptyOpenLessThanU(double U) {
		ASearchNode f = null;
		openList.removeIf(aSearchNode -> {
			if (aSearchNode.H() + aSearchNode.G() >= U){
				openListHash.remove(aSearchNode);
				return true;
			}
			return false;
		});
	}
}
