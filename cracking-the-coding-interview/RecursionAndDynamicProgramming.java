import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;

public class RecursionAndDynamicProgramming {

	/** Question 8.1 - Naive version
	 * Calculate the number of ways that a staircase can
	 * be climbed, allowing one, two or three steps at a time.
	 */
	int naiveTripleStep(int n) {
		if(n < 0) return 0;
		if(n == 1) return 1;
		if(n == 2) return 2;

		return tripleStep(n-1) + tripleStep(n-2) + tripleStep(n-3);
	}

	/** Question 8.1 - Optimized (space & time) version
	 * Calculate the number of ways that a staircase can
	 * be climbed, allowing one, two or three steps at a time.
	 */
	int tripleStep(int n) {
		int s1, s2, s3;

		if(n < 0) return 0;
		else s3 = 0;
		if(n == 1) return 1;
		else s2 = 1;
		if(n == 2) return 2;
		else s1 = 2;

		for(int i=3; i<n; i++) {
			int s = s1 + s2 + s3;
			s3 = s2;
			s2 = s1;
			s1 = s;
		}

		return s1 + s2 + s3;
	}

	/** Question 8.2
	 * Find a path in a grid of dimensions r x c from location (0,0) to
	 * (r-1, c-1), moving only to the right and down, and traveling 
	 * through cells that are open.  Cells are open if their value is 0.
	 */
	public class Cell {
		public final int col;
		public final int row;
		public Cell(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	List<Cell> robotInAGrid(int[][] grid) {	
		int r = grid.length;		// number of rows in grid
		int c = grid[0].length;	// number of columns in grid
		Cell goalCell = new Cell(r-1, c-1);

		HashSet<Cell> queued = new HashSet<Cell>();
		Queue<Cell> toVisit = new LinkedList<Cell>();
		HashMap<Cell, Cell> parentMap = new HashMap<Cell, Cell>();

		if(grid[0][0] != 0) return null;	// no path
		
		Cell startCell = new Cell(0,0);
		Cell cell, adjacentCell;
		toVisit.add(startCell);
		while(!toVisit.isEmpty()) {
			cell= toVisit.remove();
			if(cell.row == r-1 && cell.col == c-1) { // reached lower right cell!  reconstruct path.
				List<Cell> path = new ArrayList<Cell>();
				while(cell != startCell) {
					path.add(0, cell);
					cell = parentMap.get(cell);
				}
				path.add(0, startCell);
				return path;
			}

			// Check adjacent cells.  If still on grid and cell not closed, add to ququed set, so
			// will not be revisited, add to visited queue and add mapping to parent in parentMap.
			if((cell.col + 1 < c) && grid[cell.row][cell.col+1] == 0) {	// first check downward cell
				adjacentCell = new Cell(cell.row, cell.col + 1);
				queued.add(adjacentCell);
				toVisit.add(adjacentCell);
				parentMap.put(adjacentCell, cell);
			}

			if((cell.row + 1 < r) && grid[cell.row +1][cell.col] == 0) {	// next check cell to the right
				adjacentCell = new Cell(cell.row + 1, cell.col);
				queued.add(adjacentCell);
				toVisit.add(adjacentCell);
				parentMap.put(adjacentCell, cell);
			}
		}
		return null;	// No path found.
	}


	public static void main(String args[]) {
		RecursionAndDynamicProgramming test = new RecursionAndDynamicProgramming();

		int count;
		count = test.naiveTripleStep(4);
		System.out.println("naiveTripleStep(4) = " + count);
		count = test.tripleStep(4);
		System.out.println("tripleStep(4) = " + count);

		int[][] grid = {{0,0,1,0},{0,0,0,0},{0,1,0,0}};
		List<Cell> path = test.robotInAGrid(grid);
		System.out.println("robtInAGrid({{0,0,1,0},{0,0,0,0},{0,1,0,0}}) = ");
		for(Cell cell: path)
			System.out.format("(%d,%d)\n", cell.row, cell.col);
	}
}