import Puzzles.IMove;
import Puzzles.IPuzzle;
import Puzzles.IPuzzleState;
import Puzzles.NPuzzle.NPuzzle;
import Puzzles.TopSpinPuzzle.TopSpinPuzzle;
import Puzzles.TopSpinPuzzle.TopSpinPuzzleMove;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Main
{
	private static String CSV_FILE_NAME = "NPuzzle.csv";
	private static String INSTANCE_PATH_TOP_SPIN = "C:\\uni\\SingleAgentSearch\\instances\\topSpin10\\";
	private static String INSTANCE_PATH_NPuzzle = "C:\\uni\\SingleAgentSearch\\instances\\NPuzzle\\";
	private static String FILE_NAME_TOP_SPIN = "topSpin";
	private static String FILE_NAME_NPuzzle = "puzzle";
	private static String TOP_SPIN = "topSpin10";
	private static String NPuzzle = "nPuzzle";

	public static void main(String [ ] args)
	{
		System.out.println("Start!");
		String puzzle = NPuzzle;
		AStarLate(puzzle);
		IDAStar(puzzle);

		AStarEarly(puzzle);
		System.out.println("");
		System.out.println("Done!");
	}

	public static void IDAStar(String puzzle)
	{
		System.out.println("---------- IDAStar -----------");
		List<ISearch> 		solvers 	= new ArrayList<>();
		solvers.add(new IDAStar());
		solveInstances(solvers, puzzle);
	}
	
	public static void AStarLate(String puzzle)
	{
		System.out.println("---------- AStarLate -----------");
		List<ISearch> 		solvers 	= new ArrayList<>();
		solvers.add(new AStarLate());
		solveInstances(solvers, puzzle);
	}
	
	public static void AStarEarly(String puzzle)
	{
		System.out.println("---------- AStarEarly -----------");
		List<ISearch> 		solvers 	= new ArrayList<>();
		solvers.add(new AStarEarly());
		solveInstances(solvers, puzzle);
	}
	
	public static void solveInstances
	(
		List<ISearch> 	solvers,
		String 			instancesType
	) 
	{
		try {
			writeToCsv(new String[]{"Instance", "Solver", "Nodes", "Cost", "Moves", "Time", "hola"});
			long totalTime = 0;
			List<String> instances = getInstances(instancesType);
			for (String instance : instances)
			//for (int i = 0; i < 3; i++)
			{
				try {


//				String instance = instances.get(i);
					System.out.println("---- " + instance.substring(instance.indexOf(FILE_NAME_NPuzzle)) + " ----");
					IPuzzle problem = new NPuzzle(instance);
					for (ISearch solver : solvers) {
						System.out.println("Solver: " + solver.getSolverName());
						long startTime = System.nanoTime();
						List<IMove> solution = solver.solve(problem);
						long finishTime = System.nanoTime();
						double cost = checkSolution(problem, solution);
						if (cost >= 0)        // valid solution
						{
							System.out.println("Nodes: " + solver.amountOfNodesDeveloped());
							System.out.println("Cost:  " + cost);
							System.out.println("Moves: " + solution.size());
							System.out.println("Time:  " + (finishTime - startTime) / 1000000.0 + " ms");
							System.out.println(solution);
							totalTime += (finishTime - startTime) / 1000000.0;
							String name = instance.substring(INSTANCE_PATH_NPuzzle.length()).split("\\.")[0];
							writeToCsv(new String[]{name, solver.getSolverName(), solver.amountOfNodesDeveloped() + "", cost + "", solution.size() + "", (finishTime - startTime) / 1000000.0 + "", solver.amountOfTimesInSol() + ""});
						} else                // invalid solution
							System.out.println("Invalid solution.");
					}
					System.out.println("");
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
			System.out.println("Total time:  " + totalTime/60000.0 + " min");
			System.out.println("");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static List<String> getInstances
	(
		String type
	) throws IOException
	{
		List<String> instances = new ArrayList<String>();
		String currentDir = new java.io.File( "." ).getCanonicalPath() + "\\instances\\" + type + "\\";
		File folder = new File(currentDir);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				instances.add(currentDir + listOfFiles[i].getName());
			} 
		}
		return instances;
	}
	
	public static double checkSolution
	(
		IPuzzle 			instance,
		List<IMove> solution
	)
	{
		if (solution == null)
			return -1;
		double cost = 0;
		IPuzzleState currentState = instance.StartState();
		for (IMove move : solution)
		{
			currentState = currentState.getChildState(move);
			if (currentState.getStateLastMove() != null)
				cost += currentState.getStateLastMoveCost();
		}
		if (currentState.isGoalState())
			return cost;
		return -1;
	}
	
	public static void printSolution
	(
		IPuzzle instance,
		List<IMove> 	solution
	)
	{
		IPuzzleState currentState = instance.StartState();
		for (IMove move : solution)
		{
			currentState = currentState.getChildState(move);
			System.out.println(move);
			System.out.println(currentState);
		}
	}

	public static void writeToCsv(String[] dataLines){
		FileWriter csvOutputFile = null;
		try {
			csvOutputFile = new FileWriter(CSV_FILE_NAME, true);
			try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
				 String s = convertToCSV(dataLines);
				 pw.println(s);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			if (csvOutputFile != null){
				try {
					csvOutputFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String convertToCSV(String[] data) {
		return String.join(",", data);
	}
}
