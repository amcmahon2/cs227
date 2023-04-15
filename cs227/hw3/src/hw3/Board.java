package hw3;

import static api.Direction.*;
import static api.Orientation.*;
import static api.CellType.*;
import java.util.ArrayList;
import api.Cell;
import api.Direction;
import api.Move;

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left corner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of blocks that are positioned on the board.
	 */
	private ArrayList<Block> blocks;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory = new ArrayList<Move>();

	/**
	 * Block object which is the Block at the index that the user has grabbed
	 */
	private Block grabbedBlock;

	/**
	 * Cell object which is the Cell at the index that the user has grabbed
	 */
	private Cell grabbedCell;

	/**
	 * boolean variable which is used in the Board constructor to make sure the
	 * current iteration of the blocks list is a placeable block
	 */
	private boolean canPlace = true;

	/**
	 * boolean variable which is used to keep track of the current game's status
	 * (true = over, false = still going)
	 */
	private boolean isOver = false;

	/**
	 * moveCount is an int variable used to keep track of the number of moves a user
	 * has made in a game
	 */
	private int moveCount = 0;

	/**
	 * possMoves is an ArrayList of type Move which is used to store all possible
	 * valid moves of all blocks on the board, utilized in getAllPossibleMoves()
	 */
	private ArrayList<Move> possMoves = new ArrayList<Move>();

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		this.grid = grid;
		this.blocks = blocks;
		for (int i = 0; i < blocks.size(); i++) {
			// if block object at index i can be placed at blocks original orientation
			for (int j = 0; j < blocks.get(i).getLength() - 1; j++) {
				if (blocks.get(i).getOrientation() == VERTICAL) {
					// checks if block can be placed for its full length
					if (!canPlaceBlock(blocks.get(i).getFirstRow() - j, blocks.get(i).getFirstCol())) {
						canPlace = false;
					}
				} else if (blocks.get(i).getOrientation() == HORIZONTAL) {
					// checks if block can be placed for its full length
					if (!canPlaceBlock(blocks.get(i).getFirstRow(), blocks.get(i).getFirstCol() - j)) {
						canPlace = false;
					}
				}
			}
			if (canPlace == true) {
				for (int p = 0; p < blocks.get(i).getLength(); p++) {
					// set cell = block type
					if (blocks.get(i).getOrientation() == VERTICAL) {
						grid[blocks.get(i).getFirstRow() + p][blocks.get(i).getFirstCol()].setBlock(blocks.get(i));

					} else if (blocks.get(i).getOrientation() == HORIZONTAL) {
						grid[blocks.get(i).getFirstRow()][blocks.get(i).getFirstCol() + p].setBlock(blocks.get(i));

					}
				}
			}

		}
	}

	/**
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		if (grid[row][col].hasBlock()) {
			grabbedCell = grid[row][col];
			grabbedBlock = grid[row][col].getBlock();
		}
	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		grabbedBlock = null;
	}

	/**
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 */
	public Block getGrabbedBlock() {
		return grabbedBlock;
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {
		return grabbedCell;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	public boolean canPlaceBlock(int row, int col) {
		try {
			if ((grid[row][col].isExit() || grid[row][col].isFloor()) && !grid[row][col].hasBlock()) {
				return true;
			}
		}
		catch(ArrayIndexOutOfBoundsException Ex) {
			return false;
		}
		return false;
	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		return grid.length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		return grid[0].length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];

	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	public ArrayList<Block> getBlocks() {
		return this.blocks;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		return this.isOver;
	}

	/**
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBlock(Direction dir) {
		// this method took the longest of my time out of all of the methods. with
		// trying to update the
		// grabbedCell and grabbedBlock, i ran into a lot of errors. i eventually
		// determined if i clear the block,
		// move its index, and put it back on the board after updating the cell that no
		// longer has a block on it.
		// i understand this is a complicated way to go about the method, but i found
		// that putting all the code in one place
		// was much easier for me to comprehend in comparison to a helper method. i know
		// a lot of this could be simplified or
		// reworded, but this code here is the way i thought of it in my head.

		// check to make sure the game is still going and the block that has been
		// grabbed exists
	
		if (isOver == false && grabbedBlock != null) {
			if (grabbedBlock.getOrientation() == HORIZONTAL) {
				if (dir == LEFT) {
					//check to make sure the cell wanting to be moved to doesn't have a block and is a floor
					if (grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() - 1].isFloor()&& !grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() -1].hasBlock()) {
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						// clear block
						for (int i = grabbedBlock.getFirstCol() + grabbedBlock.getLength() - 1; i >= grabbedBlock
								.getFirstCol(); i--) {
							grid[grabbedBlock.getFirstRow()][i] = new Cell(FLOOR, grabbedBlock.getFirstRow(), i);
							grid[grabbedBlock.getFirstRow()][i].setBlock(null);
						}
						// move block
						grabbedBlock.move(LEFT);
						// place block
						for (int x = grabbedBlock.getFirstCol(); x < grabbedBlock.getFirstCol()
								+ grabbedBlock.getLength(); x++) {
							grid[grabbedBlock.getFirstRow()][x].setBlock(grabbedBlock);
						}
						moveHistory.add(new Move(temp, dir));
						moveCount++;
						// update the grabbed cell so more moves can be made while holding the mouse
						grabbedCell = grid[grabbedCell.getRow()][grabbedCell.getCol() - 1];
					}
					// if cell wanting to be moved to is not a floor, check if its an exit
					else if (grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() - 1].isExit()&& !grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() -1].hasBlock()) {	
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						// if it is an exit, set the rightmost block position's cell equal to null to
						// remove the block, and
						// set the cell equal to a floor type
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength()] = null;
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()+ grabbedBlock.getLength()] = new Cell(FLOOR, grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol());
						grabbedBlock.move(dir);
						moveHistory.add(new Move(temp, dir));
						// update grabbedCell
						grabbedCell = grid[grabbedCell.getRow()][grabbedCell.getCol() - 1];
						// set the game over
						isOver = true;
						moveCount++;
					}
				} else if (dir == RIGHT) {
					// same as before
					if (grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength()].isFloor() && !grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength()].hasBlock()) {
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						// clear block
						for (int i = 0; i < grabbedBlock.getLength(); i++) {
							grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + i] = new Cell(FLOOR,
									grabbedBlock.getFirstRow(), grabbedBlock.getFirstCol() + i);
						}
						moveHistory.add(new Move(temp, dir));

						grabbedBlock.move(RIGHT);
						// place block
						for (int x = grabbedBlock.getFirstCol(); x < grabbedBlock.getFirstCol()
								+ grabbedBlock.getLength(); x++) {
							grid[grabbedBlock.getFirstRow()][x].setBlock(grabbedBlock);
						}
						
						moveCount++;
						grabbedCell = grid[grabbedCell.getRow()][grabbedCell.getCol() + 1];
					} else if (grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength()].isExit() && !grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength()].hasBlock()) {
						// if it is an exit, set the leftmost block position's cell equal to null to
						// remove the block, and
						// set the cell equal to a floor type
						
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()] = null;
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()] = new Cell(FLOOR, grabbedBlock.getFirstRow(), grabbedBlock.getFirstCol());
						moveHistory.add(new Move(temp, dir));
						grabbedBlock.move(dir);
						// update grabbedCell
						grabbedCell = grid[grabbedCell.getRow()][grabbedCell.getCol() + 1];
						// set the game over
						isOver = true;
						moveCount++;
					}
				}
			}

			else if (grabbedBlock.getOrientation() == VERTICAL) {
				if (dir == UP) {
					if (grid[grabbedBlock.getFirstRow() - 1][grabbedBlock.getFirstCol()].isFloor()&& !grid[grabbedBlock.getFirstRow()-1][grabbedBlock.getFirstCol()].hasBlock()) {
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						// clear block
						for (int i = grabbedBlock.getFirstRow() + grabbedBlock.getLength() - 1; i >= grabbedBlock
								.getFirstRow(); i--) {
							grid[i][grabbedBlock.getFirstCol()] = new Cell(FLOOR, i, grabbedBlock.getFirstCol());
						}
						grabbedBlock.move(UP);

						// place block
						for (int x = grabbedBlock.getFirstRow(); x < grabbedBlock.getFirstRow()
								+ grabbedBlock.getLength(); x++) {
							grid[x][grabbedBlock.getFirstCol()].setBlock(grabbedBlock);
						}
						moveHistory.add(new Move(temp, dir));
						moveCount++;
						grabbedCell = grid[grabbedCell.getRow() - 1][grabbedCell.getCol()];

					}

					else if (grid[grabbedBlock.getFirstRow() - 1][grabbedBlock.getFirstCol()].isExit()&& !grid[grabbedBlock.getFirstRow()-1][grabbedBlock.getFirstCol()].hasBlock()) {
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()] = null;
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()] = new Cell(FLOOR,
								grabbedBlock.getFirstRow(), grabbedBlock.getFirstCol());
						grabbedBlock.move(UP);
						moveHistory.add(new Move(temp, dir));
						isOver = true;
						moveCount++;
						grabbedCell = grid[grabbedCell.getRow() - 1][grabbedCell.getCol()];

					}
				} else if (dir == DOWN) {
					if (grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength()][grabbedBlock.getFirstCol()].isFloor() && !grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength()][grabbedBlock.getFirstCol()].hasBlock()) {
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						// clear block
						for (int i = 0; i < grabbedBlock.getLength(); i++) {
							grid[grabbedBlock.getFirstRow() + i][grabbedBlock.getFirstCol()] = new Cell(FLOOR,
									grabbedBlock.getFirstRow() + i, grabbedBlock.getFirstCol());
						}
						grabbedBlock.move(DOWN);
						// place block
						for (int x = grabbedBlock.getFirstRow(); x < grabbedBlock.getFirstRow()
								+ grabbedBlock.getLength(); x++) {
							grid[x][grabbedBlock.getFirstCol()].setBlock(grabbedBlock);
						}
						moveHistory.add(new Move(temp, dir));
						moveCount++;
						grabbedCell = grid[grabbedCell.getRow() + 1][grabbedCell.getCol()];
					} else if (grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength()][grabbedBlock.getFirstCol()].isExit() && !grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength()][grabbedBlock.getFirstCol()].hasBlock()) {
						//set a temp block to store the current values of the block, used to keep track of moves for solver
						Block temp = new Block(grabbedBlock.getFirstRow(),grabbedBlock.getFirstCol(),grabbedBlock.getLength(),grabbedBlock.getOrientation());
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()] = null;
						grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()] = new Cell(FLOOR,
								grabbedBlock.getFirstRow(), grabbedBlock.getFirstCol());

						grabbedBlock.move(DOWN);

						for (int x = grabbedBlock.getFirstRow(); x < grabbedBlock.getFirstRow()
								+ grabbedBlock.getLength() - 1; x++) {
							grid[x][grabbedBlock.getFirstCol()].setBlock(grabbedBlock);
						}

						moveHistory.add(new Move(temp, dir));
						grabbedCell = grid[grabbedCell.getRow() + 1][grabbedCell.getCol()];

						isOver = true;
						moveCount++;
					}
				}
			}
		}
	}
	

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 */
	public void reset() {
		// store the list of all blocks
		ArrayList<Block> blockList = getBlocks();
		// copy the grid's dimensions into a new empty grid
		Cell[][] newGrid = new Cell[grid.length][grid[0].length];
		// iterate through old grid to set floors, walls, and exits for the new grid
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].isWall()) {
					newGrid[i][j] = grid[i][j];
				} else if (grid[i][j].isExit()) {
					newGrid[i][j] = grid[i][j];
				} else if (grid[i][j].hasBlock()) {
					newGrid[i][j] = new Cell(FLOOR, i, j);
				}
			}
		}
		for (int i = 0; i < blockList.size(); i++) {
			for (int p = 0; p < blockList.get(i).getLength(); p++) {
				// remove type from the cells that have blocks on them
				grid[blockList.get(i).getFirstRow()][blockList.get(i).getFirstCol()].getBlock().reset();

				if (blockList.get(i).getOrientation() == VERTICAL) {
					// place each index of the block
					grid[blockList.get(i).getFirstRow() + p][blockList.get(i).getFirstCol()].setBlock(blockList.get(i));

					if (grid[blockList.get(i).getFirstRow() + blockList.get(i).getLength()][blockList.get(i)
							.getFirstCol()].hasBlock()) {
						grid[blockList.get(i).getFirstRow() + blockList.get(i).getLength()][blockList.get(i)
								.getFirstCol()] = null;
						grid[blockList.get(i).getFirstRow() + blockList.get(i).getLength()][blockList.get(i)
								.getFirstCol()] = new Cell(FLOOR,
										blockList.get(i).getFirstRow() + blockList.get(i).getLength(),
										blockList.get(i).getFirstCol());
					}
				} else if (blockList.get(i).getOrientation() == HORIZONTAL) {
					grid[blockList.get(i).getFirstRow()][blockList.get(i).getFirstCol() + p].setBlock(blockList.get(i));

					if (grid[blockList.get(i).getFirstRow()][blockList.get(i).getFirstCol()
							+ blockList.get(i).getLength()].hasBlock()) {
						grid[blockList.get(i).getFirstRow()][blockList.get(i).getFirstCol()
								+ blockList.get(i).getLength()] = null;
						grid[blockList.get(i).getFirstRow()][blockList.get(i).getFirstCol()
								+ blockList.get(i).getLength()] = new Cell(FLOOR, blockList.get(i).getFirstRow(),
										blockList.get(i).getFirstCol() + blockList.get(i).getLength());
					}

				}
			}
		}
		// reset moveCount, game status, and clear the moveHistory list
		moveCount = 0;
		isOver = false;
		moveHistory.clear();
	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		// loop through every index, if hasBlock, grab block and try to move in all
		// directions. if any change occurs, add it in
		// the list of moves
		if (isOver == true) {
			possMoves.clear();
			return possMoves;
		} else {
			// is game is still going, return list of moves
			// iterate through grid to find blocks
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j].hasBlock()) {
						grabBlockAtCell(i, j);
						// try moves L,R,U, or D dependent on the orientation of the block at the grid's
						// current indices
						if (grabbedBlock.getOrientation() == HORIZONTAL) {
							int endIndex = grabbedBlock.getFirstCol() + grabbedBlock.getLength() - 1;
							if (canPlaceBlock(i, endIndex + 1)) {
								// move right
								possMoves.add(new Move(grabbedBlock, RIGHT));
							}
							if (canPlaceBlock(i, grabbedBlock.getFirstCol() - 1)) {
								// move left
								possMoves.add(new Move(grabbedBlock, LEFT));
							}
						} else if (grabbedBlock.getOrientation() == VERTICAL) {
							int endIndex = grabbedBlock.getFirstRow() + grabbedBlock.getLength() - 1;
							if (canPlaceBlock(endIndex + 1, j)) {
								// move down
								possMoves.add(new Move(grabbedBlock, DOWN));
							}
							if (canPlaceBlock(grabbedBlock.getFirstRow() - 1, j)) {
								// move up
								possMoves.add(new Move(grabbedBlock, UP));
							}
						}
					}
				}
			}

		}
		// release the block
		releaseBlock();
		// return list of possible moves
		return possMoves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		// make sure there has been at least 1 move made
		if (moveHistory.size() > 0) {
			if (moveHistory.get(moveHistory.size() - 1).getDirection() == RIGHT) {
				// grab the block that was last moved (at the very end of the list (size-1))
				grabBlockAtCell(moveHistory.get(moveHistory.size() - 1).getBlock().getFirstRow(),
						moveHistory.get(moveHistory.size() - 1).getBlock().getFirstCol());
				// move left
				moveGrabbedBlock(LEFT);
				// if the grabbedBlocks previous RIGHT move moved it into an exit, the undo()
				// moved it LEFT, so the game is still going now
				// check rightmost+1 index for exit
				if (grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength()-1].isExit()) {
					isOver = false;
				}
				releaseBlock();
				moveCount = moveCount - 2;
			} else if (moveHistory.get(moveHistory.size() - 1).getDirection() == LEFT) {
				grabBlockAtCell(moveHistory.get(moveHistory.size() - 1).getBlock().getFirstRow(),
						moveHistory.get(moveHistory.size() - 1).getBlock().getFirstCol());
				// move right
				moveGrabbedBlock(RIGHT);
				// if the grabbedBlocks previous UP move moved it into an exit, the undo() moved
				// it down, so the game is still going now
				// check leftmost-1 index for exit
				if (grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() - 1].isExit()) {
					isOver = false;
				}
				releaseBlock();
				moveCount = moveCount - 2;

			} else if (moveHistory.get(moveHistory.size() - 1).getDirection() == UP) {
				grabBlockAtCell(moveHistory.get(moveHistory.size() - 1).getBlock().getFirstRow(),
						moveHistory.get(moveHistory.size() - 1).getBlock().getFirstCol());
				// move down
				grabBlockAtCell(moveHistory.get(moveHistory.size() - 1).getBlock().getFirstRow(),
						moveHistory.get(moveHistory.size() - 1).getBlock().getFirstCol());
				moveGrabbedBlock(DOWN);
				// if the grabbedBlocks previous UP move moved it into an exit, the undo() moved
				// it down, so the game is still going now
				// check upmost-1 index for exit
				if (grid[grabbedBlock.getFirstRow() - 1][grabbedBlock.getFirstCol()].isExit()) {
					isOver = false;
				}
				releaseBlock();
				moveCount = moveCount - 2;
			} else if (moveHistory.get(moveHistory.size() - 1).getDirection() == DOWN) {
				grabBlockAtCell(moveHistory.get(moveHistory.size() - 1).getBlock().getFirstRow(),
						moveHistory.get(moveHistory.size() - 1).getBlock().getFirstCol());
				// move up
				moveGrabbedBlock(UP);
				// if the grabbedBlocks previous DOWN move moved it into an exit, the undo()
				// moved it UP, so the game is still going now
				// check downmost+1 index for exit
				if (grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength()-1][grabbedBlock.getFirstCol()].isExit()) {
					isOver = false;
				}
				releaseBlock();
				moveCount = moveCount - 2;
			}
			// remove last move made
			moveHistory.remove(moveHistory.size() - 1);
		}
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
