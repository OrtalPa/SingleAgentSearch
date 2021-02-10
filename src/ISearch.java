import Puzzles.IMove;
import Puzzles.IPuzzle;
import java.util.List;

public interface ISearch {
    List<IMove> solve(IPuzzle problem);
    String getSolverName();
    int amountOfNodesDeveloped();
    int amountOfTimesInSol();
    int getDuplicateNodes();
    double maxMemoryUsage();
}
