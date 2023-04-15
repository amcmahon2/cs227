package hw3;
import java.io.FileNotFoundException; 
import java.util.ArrayList;

import api.Cell;
import api.DescriptionUtil; 
import api.Move; 
import hw3.Board; 
import hw3.Solver; 
public class SolverTests { 
//	public static final String[][] testDescription2 =
//	{ { "*", "*", "*", "*", "*" },
//	  { "*", ".", ".", ".", "*" },
//	  { "*", "[", "]", ".", "e" },
//	  { "*", ".", ".", ".", "*" },
//	  { "*", "*", "*", "*", "*" } };
  public static void main(String args[]) throws FileNotFoundException { 
    ArrayList<String[][]> gameDescriptions = DescriptionUtil.readBoardDescriptionsFromFile("simple-games.txt"); 
    int boardIndex = 0; // change to select different board setup 
    Board board = new Board(gameDescriptions.get(boardIndex)); 
 
    Solver solver = new Solver(100000000); // set higher for larger puzzles 
    solver.solve(board); 
    ArrayList<ArrayList<Move>> solutions = solver.getSolutions(); 
 
    System.out.println("Number of solutions found: " + solutions.size()); 
    solver.printSolutions(); 
  } 
}
/*
	
	public static void main (String [] args) {
		Cell[][] cells = GridUtil.createGrid(testDescription2);
		System.out.println(cells);
		ArrayList<Block> blocks = GridUtil.findBlocks(testDescription2);
		Board board = new Board(cells, blocks);
		System.out.println(board.toString());
	/*	* * * * * * 
		* . . . . * 
		* . . . . * 
		* [ ] . . e 
		* * * * * * 
		 
		* * * * * * 
		* . . . . * 
		* . . ^ . * 
		* [ ] v . e 
		* * * * * * */
		/*
  
	public static final String[][] testDescription2 =
	{ {"*", "*", "*", "*", "*", "*", "*" },
	{"*", ".", ".", ".", ".", ".", "*" },
	{"*", ".", ".", ".", "[", "]", "*" },
	{"*", "[", "]", ".", ".", "^", "e" },
	{"*", ".", "^", "^", ".", "v", "*" },
	{"*", ".", "v", "v", "[", "]", "*" },
	{"*", "*", "*", "*", "*", "*", "*" } };*/
	


