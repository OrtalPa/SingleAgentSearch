package Puzzles.NPuzzle;

import Puzzles.IHeuristic;
import Puzzles.IPuzzleState;

public class NPuzzleHeuristic implements IHeuristic {

    @Override
    public double getHeuristic(IPuzzleState state){
        if (state instanceof NPuzzleState)
            return getHeuristic((NPuzzleState)state);
        return 0;
    }

    public double getHeuristic(NPuzzleState problemState)
    {
        /*
        2|3|4   00|01|02  1|2|3   1|2|3|4      00|01|02|03    first*size+second+1
        1|5|8   10|11|12  4|5|6   5|6|7|8      10|11|12|13
        6|7|0   20|21|22  7|8|0   9|10|11|12   20|21|22|23
                                  13|14|15|0   30|31|32|33

           final = size^2
           result = final-value ==> x = (int)result/size ==> goalRow == size-1-x
           goalRow = (int)(value-1)/size
           if remains !=0 : goalCol = remains(value/size)-1
           else:  size-1
         */
        int sum = 0;
        int [][] puzzle = problemState._nPuzzle;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                int dx = Math.abs(i - goalRow(puzzle[i][j], puzzle.length));
                int dy = Math.abs(j - goalCol(puzzle[i][j], puzzle.length));
                sum += (dx+dy);
            }
        }
        return sum;
    }

    private int goalCol(int value, int size) {
        /*
        if remains !=0 : goalCol = remains(value/size) - 1
                else:  size-1
         */
        int remains = value%size;
        if (remains == 0){
            return size-1;
        }
        return remains-1;
    }

    private int goalRow(int value, int size) {
        // goalRow = (int)(value-1)/size
        return (value-1)/size;
    }

}
