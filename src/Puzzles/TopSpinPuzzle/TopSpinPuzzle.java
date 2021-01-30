package Puzzles.TopSpinPuzzle;

import Puzzles.IHeuristic;
import Puzzles.IPuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TopSpinPuzzle implements IPuzzle
{
	int[] 					_TopSpinPuzzle;	// Problem instance
	int						_size;			// Puzzle size
	IHeuristic	_heuristic;		// The problem heuristic

	public TopSpinPuzzle()
	{
		_heuristic = new TopSpinPuzzleHeuristic();
	}
	
	public TopSpinPuzzle(String problemName)
	{
		importInstance(problemName);
		_heuristic = new TopSpinPuzzleHeuristic();
	}
	
	
	public TopSpinPuzzleState StartState() 
	{
		TopSpinPuzzleState root = new TopSpinPuzzleState(this, _TopSpinPuzzle, null);
		return root;
	}
	
	public IHeuristic Heuristic()
	{
		return _heuristic;
	}
	
	public int Size()
	{
		return _size;
	}
	
	private void importInstance
	(
		String problemName
	)
	{
		// Get the file
		File file 	= new File(problemName); 
	    Scanner sc;
		try 
		{
			sc = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		} 
	    
		// Read the file
	    while (sc.hasNextLine()) 
	    {
	    	String cuurentLine = sc.nextLine();
	    	if (cuurentLine.contains("Size:"))					// Puzzle size
	    	{
	    		cuurentLine = sc.nextLine();
	    		_size 		= Integer.parseInt(cuurentLine);
	    		_TopSpinPuzzle = new int [_size];
	    	}
	    	else if (cuurentLine.contains("Puzzle:"))			// Puzzle instance
	    	{
    			cuurentLine 	= sc.nextLine();
    			String[] tokens = cuurentLine.split("\\|");
    			for (int cell = 0; cell < _size; cell ++)
    			{
    				_TopSpinPuzzle[cell] = Integer.parseInt(tokens[cell]);
    			}
	    	}
	    }
	    sc.close();
	}	
}
