package Puzzles.TopSpinPuzzle;

import Puzzles.IHeuristic;
import Puzzles.IPuzzleState;

public class TopSpinPuzzleHeuristic implements IHeuristic
{
	public double getHeuristic(IPuzzleState problemState)
	{
		if  (problemState instanceof TopSpinPuzzleState){
			//return swapHeuristic((TopSpinPuzzleState) problemState);
			return swapAndMoveHeuristic((TopSpinPuzzleState) problemState);
		}
		return 0;
	}

	/**
	 * Heuristic that considers the number of swaps and moves
	 */
	private double swapAndMoveHeuristic(TopSpinPuzzleState problemState){
		int[] current_state = problemState._TopSpinPuzzle;
		double  distanceOfZero = 0;
		// count the distance of 0 from the beginning
		for (int j = 0; j < current_state.length; j++) {
			if (current_state[j] == 0){
				distanceOfZero = Math.min(j, current_state.length - j);
				break;
			}
		}

		double couples = 0;
		for(int i=0; i < current_state.length-1;i++) {
			//find the couples that have to be flipped
			if( Math.abs(current_state[i] - current_state[i+1]) > 1){
				//found a couple, has to be different than 0&9
				if(!(current_state[i] ==0 && current_state[i+1] ==9 ) && !(current_state[i] ==9 && current_state[i+1] ==0 )) {
					couples++;
					i++;
				}
			}
		}
		//check the end and beginning of the array
		if(Math.abs(current_state[0] - current_state[current_state.length-1]) > 1){
			if(!(current_state[0] ==0 && current_state[current_state.length-1] ==9 ) && !(current_state[0] ==9 && current_state[current_state.length-1] ==0 )) {
				couples++;
			}
		}
		return couples/2;
	}

	/**
	 * Heuristic that counts number of swaps
	 */
	private double swapHeuristic(TopSpinPuzzleState problemState){
		int[] current_state = problemState._TopSpinPuzzle;
		double couples = 0;
		int min_weighted = 0;
		for(int i=0; i < current_state.length-1;i++) {
			//find the couples that have to be flipped
			if( Math.abs(current_state[i] - current_state[i+1]) > 1){
				//found a couple, has to be different than 0&9
				if(!(current_state[i] ==0 && current_state[i+1] ==9 ) && !(current_state[i] ==9 && current_state[i+1] ==0 )) {
					couples++;
					min_weighted += Math.min(current_state[i], current_state[i + 1]);
					i++;
				}
			}
		}
		//check the end and beginning of the array
		if(Math.abs(current_state[0] - current_state[current_state.length-1]) > 1){
			if(!(current_state[0] ==0 && current_state[current_state.length-1] ==9 ) && !(current_state[0] ==9 && current_state[current_state.length-1] ==0 )) {
				couples++;
				min_weighted += Math.min(current_state[0], current_state[current_state.length-1]);
			}
		}
		return (couples/2)*min_weighted;
	}


}
