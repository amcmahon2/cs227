package hw3;

import static api.CellType.*;
import static api.Orientation.*;
import java.util.ArrayList;

import api.Cell;
import api.Orientation;

/**
 * Utilities for parsing string descriptions of a grid.
 */
public class GridUtil {
	private static boolean canCreate = true;

	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a floor.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a block. A block is not a
	 * type of cell, it is something placed on a cell floor. For these descriptions
	 * a cell is created with CellType of FLOOR. This method does not create any
	 * blocks or set blocks on cells.</li>
	 * </ul>
	 * The method only creates cells and not blocks. See the other utility method
	 * findBlocks which is used to create the blocks.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any blocks present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		Cell[][] grid = new Cell[desc.length][desc[0].length];
		for (int x = 0; x < desc.length; x++) {
			for (int y = 0; y < desc[0].length; y++) {
				if (desc[x][y].equals("*")) {
					grid[x][y] = new Cell(WALL, x, y);
				} else if (desc[x][y].equals(".")) {
					grid[x][y] = new Cell(FLOOR, x, y);
				} else if (desc[x][y].equals("e")) {

					grid[x][y] = new Cell(EXIT, x, y);
				} else {
					grid[x][y] = new Cell(FLOOR, x, y);
				}
			}
		}
		ArrayList<Block> temp = findBlocks(desc);
		for (int i = 0; i < temp.size(); i++) {
			int r = temp.get(i).getFirstRow();
			int c = temp.get(i).getFirstCol();
			int length = temp.get(i).getLength();
			Orientation o = temp.get(i).getOrientation();
			if (o == HORIZONTAL) {
				// r is constant, loop through c
				for (int j = c; j < c + length; j++) {
					grid[r][j].setBlock(temp.get(i));
				}
			} else if (o == VERTICAL) {
				// c is constant, loop through r
				for (int j = r; j < r + length; j++) {
					grid[j][c].setBlock(temp.get(i));
				}
			}
		}
		// if cells are left null, that means a block was attempted to be placed but
		// could not, so make these floors
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (grid[x][y] == null) {
					grid[x][y] = new Cell(FLOOR, x, y);
				}
			}

		}
		return grid;
	}

	/**
	 * Returns a list of blocks that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal block</li>
	 * <li>"]" the end (right most column) of a horizontal block</li>
	 * <li>"^" the start (top most row) of a vertical block</li>
	 * <li>"v" the end (bottom most column) of a vertical block</li>
	 * <li>"#" inner segments of a block, these are always placed between the start
	 * and end of the block</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * block currently over the cell</li>
	 * </ul>
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of blocks found in the given grid description
	 */
	public static ArrayList<Block> findBlocks(String[][] desc) {
		ArrayList<Block> list = new ArrayList<Block>();
		int count = 0;
		int length = 0;
		for (int x = 0; x < desc.length; x++) {
			if (x > desc[0].length) {
				break;
			} else {
				for (int y = 0; y < desc[0].length; y++) {
					// eliminate all spaces except
					if (!desc[x][y].equals("*") || !desc[x][y].equals(".") || !desc[x][y].equals("e")) {
						// start of horizontal block
						if (desc[x][y].equals("[")) {
							count = 0;
							for (int i = y + 1; i < desc.length + y; i++) {
								// if block doesnt end or have a #, cannot be created
								if (!desc[x][i].equals("#") && !desc[x][i].equals("]")) {
									canCreate = false;
								} else if (desc[x][i].equals("]")) {
									count++;
									break;
								}
							}
							if (canCreate) {
								// since count was 0 and the blocks length is measured from 1+, length = 1+count
								length = 1 + count;
								list.add(new Block(x, y, length, HORIZONTAL));
							}
							count = 0;
							length = 0;
						}
						// start of vertical block
						else if (desc[x][y].equals("^")) {
							count = 0;
							for (int i = x + 1; i < desc[0].length + x; i++) {
								// if block doesnt end or have a #, cannot be created
								if (!desc[i][y].equals("#") && !desc[i][y].equals("v")) {
									canCreate = false;
								} else if (desc[i][y].equals("v")) {
									count++;
									break;
								}
							}
							if (canCreate) {
								length = 1 + count;
								list.add(new Block(x, y, length, VERTICAL));
							}
							count = 0;
							length = 0;
						}
					}

				}
			}
		}
		return list;
	}
}
