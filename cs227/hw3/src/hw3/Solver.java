package hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import hw3.Board;
import static api.Direction.*;
import api.Direction;
import static api.Orientation.*;
import api.Orientation;
import api.Move;

/**
 * A puzzle solver for the the Block Slider game.
 * <p>
 * THE ONLY METHOD YOU ARE CHANGING IN THIS CLASS IS solve().
 */
public class Solver {
	/**
	 * Maximum number of moves allowed in the search.
	 */
	private int maxMoves;

	/**
	 * Associates a string representation of a grid with the move count required to
	 * reach that grid layout.
	 */
	private Map<String, Integer> seen = new HashMap<String, Integer>();

	/**
	 * All solutions found in this search.
	 */
	private ArrayList<ArrayList<Move>> solutions = new ArrayList<ArrayList<Move>>();

	/**
	 * Constructs a solver with the given maximum number of moves.
	 * 
	 * @param givenMaxMoves maximum number of moves
	 */
	public Solver(int givenMaxMoves) {
		maxMoves = givenMaxMoves;
		solutions = new ArrayList<ArrayList<Move>>();
	}

	/**
	 * Returns all solutions found in the search. Each solution is a list of moves.
	 * 
	 * @return list of all solutions
	 */
	public ArrayList<ArrayList<Move>> getSolutions() {
		return solutions;
	}

	/**
	 * Prints all solutions found in the search.
	 */
	public void printSolutions() {
		for (ArrayList<Move> moves : solutions) {
			System.out.println("Solution:");
			for (Move move : moves) {
				System.out.println(move);
			}
			System.out.println();
		}
	}

	/**
	 * EXTRA CREDIT 15 POINTS
	 * <p>
	 * Recursively search for solutions to the given board instance according to the
	 * algorithm described in the assignment pdf. This method does not return
	 * anything its purpose is to update the instance variable solutions with every
	 * solution found.
	 * 
	 * @param board any instance of Board
	 */
	
	//one thing i could not solve was updating the blocks indices. i feel my code is correct,
	//but all methods of storing the block being moved failed. i tried a deep copy but that threw a 
	//ton of errors so i decided i'd leave it as is.
	public void solve(Board board) {
		if (board.getMoveCount() > maxMoves) {
			//if current moves > maxMoves from constructor
			return;
		} 
		else if (board.isGameOver()) {
			//if game is over, add the move history list to the solutions list
			solutions.add((ArrayList<Move>)board.getMoveHistory().clone());
			return;
		} 
		else if (seen.containsKey(board.toString())) {
			//if the board has been seen, get the num of moves from the lowest value (num of moves) of the key
			int moves = seen.get(board.toString());
			//if number of moves is >= current num moves for this board configuration
			if (board.getMoveCount() >= moves) {
				//if current move count of this board is greater than the same board's lowest move count, return
				return;
			} 
			else {
				//update the new move count as the value for this board's configuration (the key)
				seen.put(board.toString(), board.getMoveCount());
			}
		} 
		else {
			//this means the board hasn't been seen, so add it to the map
			seen.put(board.toString(), board.getMoveCount());
		}
		//create a list of all possible valid moves of the board
		ArrayList<Move> list = board.getAllPossibleMoves();
		//loop through this list
		for (int i = 0; i < list.size(); i++) {
			//grab the block at the cell which has the block in the current iteration of list
			board.grabBlockAtCell(list.get(i).getBlock().getFirstRow(), list.get(i).getBlock().getFirstCol());
			//if a block can be placed in this Move object's direction from the Move object's block, move it there!
			//check for the direction and update the board accordingly
			//i know this is a lot of code, but i saw no other way to accurately implement it
			if(list.get(i).getDirection() == RIGHT){
				if (board.canPlaceBlock(list.get(i).getBlock().getFirstRow(), list.get(i).getBlock().getFirstCol()+list.get(i).getBlock().getLength())) {
					board.moveGrabbedBlock(list.get(i).getDirection());
					//recursion to solve for this new configuration
					//ArrayList<Move> moveCopy = (ArrayList<Move>) new ArrayList<String>().clone()
					solve(board);
					if(board.isGameOver()) {
						return;
					}
					//undo the move to return to the board's previous state
					board.undoMove();
				}
			}
			else if(list.get(i).getDirection() == LEFT){
				if (board.canPlaceBlock(list.get(i).getBlock().getFirstRow(), list.get(i).getBlock().getFirstCol()-1)) {
					board.moveGrabbedBlock(list.get(i).getDirection());
					//recursion to solve for this new configuration
					solve(board);
					if(board.isGameOver()) {
						return;
					}
					
					//undo the move to return to the board's previous state
					board.undoMove();
				
			}
			else if(list.get(i).getDirection() == UP){
				if (board.canPlaceBlock(list.get(i).getBlock().getFirstRow()-1, list.get(i).getBlock().getFirstCol())) {
					board.moveGrabbedBlock(list.get(i).getDirection());
					//recursion to solve for this new configuration
					solve(board);
					if(board.isGameOver()) {
						return;
					}
					//undo the move to return to the board's previous state
					board.undoMove();
				}
			}
			else if(list.get(i).getDirection() == DOWN){
				if (board.canPlaceBlock(list.get(i).getBlock().getFirstRow()+list.get(i).getBlock().getLength(), list.get(i).getBlock().getFirstCol())) {
					board.moveGrabbedBlock(list.get(i).getDirection());
					//recursion to solve for this new configuration
					solve(board);
					if(board.isGameOver()) {
						return;
					}
					//undo the move to return to the board's previous state
					board.undoMove();
				}
			}
		}
			
		}
	}
}
	

