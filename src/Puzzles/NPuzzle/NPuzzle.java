package Puzzles.NPuzzle;

import Puzzles.IPuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NPuzzle implements IPuzzle {

    int[][] _NPuzzle;    // Problem instance
    int _size;            // Puzzle size
    NPuzzleHeuristic _heuristic;        // The problem heuristic

    public NPuzzle() {
        _heuristic = new NPuzzleHeuristic();
    }

    public NPuzzle(String problemName) {
        importInstance(problemName);
        _heuristic = new NPuzzleHeuristic();
    }


    public NPuzzleState StartState() {
        NPuzzleState root = new NPuzzleState(this, _NPuzzle, null);
        return root;
    }

    public NPuzzleHeuristic Heuristic() {
        return _heuristic;
    }

    public int Size() {
        return _size;
    }

    private void importInstance(String problemName) {
        // Get the file
        File file = new File(problemName);
        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Read the file
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            if (currentLine.contains("Size:"))                    // Puzzle size
            {
                currentLine = sc.nextLine();
                _size = (int)Math.pow(Integer.parseInt(currentLine)+1, 0.5);
                _NPuzzle = new int[_size][_size];
            } else if (currentLine.contains("Puzzle:"))            // Puzzle instance
            {
                currentLine = sc.nextLine();
                String[] rows = currentLine.split("-");
                for (int i = 0; i < rows.length; i++) {
                    String[] tokens = rows[i].split("\\|");
                    for (int cell = 0; cell < _size; cell++) {
                        _NPuzzle[i][cell] = Integer.parseInt(tokens[cell]);
                    }
                }
            }
        }
        sc.close();
    }


}
