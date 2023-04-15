package hw3;

import static api.Orientation.*;
import static api.Direction.*;
import api.Direction;
import api.Orientation;

/**
 * Represents a block in the Block Slider game.
 */
public class Block {

	/**
	 * firstRow is an int value used to store each blocks first row of appearance,
	 * called via "this. "
	 */
	private int firstRow;

	/**
	 * firstCol is an int value used to store each blocks first column of apperance,
	 * called via "this. "
	 */
	private int firstCol;

	/**
	 * length is an int value used to store each blocks length, called via "this. "
	 */
	private int length;

	/**
	 * orientation is an Orientation value used to store each blocks orientation,
	 * called via "this. "
	 */
	private Orientation orientation;

	/**
	 * originalRow is an int value used to store each blocks original row, via
	 * "this. " this value does not change and is used when resetting the block's
	 * position
	 */
	private int originalRow;

	/**
	 * originalCol is an int value used to store each blocks original column, via
	 * "this. " this value does not change and is used when resetting the block's
	 * position
	 */
	private int originalCol;

	/**
	 * Constructs a new Block with a specific location relative to the board. The
	 * upper/left most corner of the block is given as firstRow and firstCol. All
	 * blocks are only one cell wide. The length of the block is specified in cells.
	 * The block can either be horizontal or vertical on the board as specified by
	 * orientation.
	 * 
	 * @param firstRow    the first row that contains the block
	 * @param firstCol    the first column that contains the block
	 * @param length      block length in cells
	 * @param orientation either HORIZONTAL or VERTICAL
	 */
	public Block(int firstRow, int firstCol, int length, Orientation orientation) {
		this.firstRow = firstRow;
		this.firstCol = firstCol;
		this.length = length;
		this.orientation = orientation;

		this.originalRow = firstRow;
		this.originalCol = firstCol;
	}

	/**
	 * Resets the position of the block to the original firstRow and firstCol values
	 * that were passed to the constructor during initialization of the the block.
	 */
	public void reset() {
		this.firstRow = this.originalRow;
		this.firstCol = this.originalCol;
	}

	/**
	 * Move the blocks position by one cell in the direction specified. The blocks
	 * first column and row should be updated. The method will only move VERTICAL
	 * blocks UP or DOWN and HORIZONTAL blocks RIGHT or LEFT. Invalid movements are
	 * ignored.
	 * 
	 * @param dir direction to move (UP, DOWN, RIGHT, or LEFT)
	 */
	public void move(Direction dir) {
		if (getOrientation() == VERTICAL) {
			if (dir == UP) {
				this.firstRow--;
			} else if (dir == DOWN) {
				this.firstRow++;
			}
		} else if (getOrientation() == HORIZONTAL) {
			if (dir == LEFT) {
				this.firstCol--;
			} else if (dir == RIGHT) {
				this.firstCol++;
			}
		}
	}

	/**
	 * Gets the first row of the block on the board.
	 * 
	 * @return first row
	 */
	public int getFirstRow() {
		return this.firstRow;
	}

	/**
	 * Sets the first row of the block on the board.
	 * 
	 * @param firstRow first row
	 */
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	/**
	 * Gets the first column of the block on the board.
	 * 
	 * @return first column
	 */
	public int getFirstCol() {
		return this.firstCol;
	}

	/**
	 * Sets the first column of the block on the board.
	 * 
	 * @param firstCol first column
	 */
	public void setFirstCol(int firstCol) {

		this.firstCol = firstCol;

	}

	/**
	 * Gets the length of the block.
	 * 
	 * @return length measured in cells
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Gets the orientation of the block.
	 * 
	 * @return either VERTICAL or HORIZONTAL
	 */
	public Orientation getOrientation() {
		return this.orientation;
	}

	@Override
	public String toString() {
		return "(row=" + getFirstRow() + ", col=" + getFirstCol() + ", len=" + getLength() + ", ori=" + getOrientation()
				+ ")";
	}

}
