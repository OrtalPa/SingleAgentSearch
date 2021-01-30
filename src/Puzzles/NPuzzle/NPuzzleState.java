package Puzzles.NPuzzle;

import Puzzles.IMove;
import Puzzles.IPuzzleState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPuzzleState implements IPuzzleState {
    NPuzzle _problem;		// The original problem
    int[][] _nPuzzle;	// Current state
    IMove _lastMove;		// The last move made
    public int _zeroRow;
    public int _zeroCol;


    public NPuzzleState(NPuzzle problem,int[][]	nPuzzle, IMove lastMove)
    {
        _problem		= problem;
        _nPuzzle = deepCopy(nPuzzle);
        _lastMove		= lastMove;
        int [] zeroIndex = findZeroIndex();
        _zeroRow = zeroIndex[0];
        _zeroCol = zeroIndex[1];
    }

    private int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }


    public List<IPuzzleState> getNeighborStates()
    {
        List<IPuzzleState>	neighborStates 	= new ArrayList<>();
        List<NPuzzleMove>		legalMoves 		= getLegalMoves();

        for (NPuzzleMove move : legalMoves)
        {
            NPuzzleState newState = getChildState(move);
            neighborStates.add(newState);
        }
        return neighborStates;
    }

    private List<NPuzzleMove> getLegalMoves()
    {
        List<NPuzzleMove> newList = new ArrayList<>();
        NPuzzleMove leftMove 	= NPuzzleMove.LEFT;
        NPuzzleMove rightMove = NPuzzleMove.RIGHT;
        NPuzzleMove upMove 	= NPuzzleMove.UP;
        NPuzzleMove downMove 	= NPuzzleMove.DOWN;
        if (isInArray(_zeroRow+1)){
            newList.add(downMove);
        }
        if (isInArray(_zeroRow-1)){
            newList.add(upMove);
        }
        if (isInArray(_zeroCol+1)){
            newList.add(rightMove);
        }
        if (isInArray(_zeroCol-1)){
            newList.add(leftMove);
        }
        return newList;
    }

    private int[] findZeroIndex() {
        int[] index = new int[2];
        for (int i = 0; i < _nPuzzle.length; i++) {
            for (int j = 0; j < _nPuzzle[i].length; j++) {
                if (_nPuzzle[i][j] == 0) {
                    index[0] = i;
                    index[1] = j;
                    break;
                }
            }
        }
        return index;
    }


    private boolean isInArray(int index){
        return index >= 0 && index < _problem._size;
    }

    public NPuzzle Problem()
    {
        return _problem;
    }

    public boolean isGoalState()
    {
        int index = 1;
        for(int[] row : _nPuzzle) {
            for(int cell : row){
                if (cell != index && !(cell == 0 && index == 16))
                    return false;
                index++;
            }
        }
        return true;
    }

    public IMove getStateLastMove()
    {
        return _lastMove;
    }

    public double getStateLastMoveCost()
    {
        if (_lastMove != null){
            return 1;
        }
        return 0;
    }

    public NPuzzleState getChildState(IMove NPuzzleMove)
    {
        NPuzzleState newState = new NPuzzleState(_problem, _nPuzzle, NPuzzleMove);
        newState.performMove(NPuzzleMove);
        return newState;
    }

    public void performMove(IMove nPuzzleMove)
    {
        // Perform the action
        if (nPuzzleMove == NPuzzleMove.LEFT)
            performLeftMove();
        else if (nPuzzleMove == NPuzzleMove.RIGHT)
            performRightMove();
        else if (nPuzzleMove == NPuzzleMove.UP)
            performUpMove();
        else if (nPuzzleMove == NPuzzleMove.DOWN)
            performDownMove();

    }

    private void performLeftMove()
    {
        int newIndex = _zeroCol-1;
        _nPuzzle[_zeroRow][_zeroCol] = _nPuzzle[_zeroRow][newIndex];
        _nPuzzle[_zeroRow][newIndex] = 0;
        _zeroCol = newIndex;
    }

    private void performRightMove()
    {
        int newIndex = _zeroCol+1;
        _nPuzzle[_zeroRow][_zeroCol] = _nPuzzle[_zeroRow][newIndex];
        _nPuzzle[_zeroRow][newIndex] = 0;
        _zeroCol = newIndex;
    }

    private void performUpMove()
    {
        int newIndex = _zeroRow-1;
        _nPuzzle[_zeroRow][_zeroCol] = _nPuzzle[newIndex][_zeroCol];
        _nPuzzle[newIndex][_zeroCol] = 0;
        _zeroRow = newIndex;
    }

    private void performDownMove()
    {
        int newIndex = _zeroRow+1;
        _nPuzzle[_zeroRow][_zeroCol] = _nPuzzle[newIndex][_zeroCol];
        _nPuzzle[newIndex][_zeroCol] = 0;
        _zeroRow = newIndex;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof NPuzzleState)) return false;
        NPuzzleState that = (NPuzzleState) o;
        return java.util.Arrays.deepEquals(_nPuzzle, that._nPuzzle);
    }

    @Override
    public int hashCode() {
        return java.util.Arrays.deepHashCode(_nPuzzle);
    }


    private int[] getNPuzzleCopy()
    {
        throw new NotImplementedException();
    }

    @Override
    public String toString()
    {
        StringBuilder toPrint = new StringBuilder().append("| ");
        for (int[] ints : _nPuzzle) {
            for (int anInt : ints) {
                toPrint.append(anInt).append(" | ");
            }
            toPrint.append(" \n ");
        }
        return toPrint.toString();
    }
}
