package lab4.data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Observable;
import java.util.function.Function;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	private final TileState[][] grid;
	private final int gridSize;

	private final int INROW = 5;
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		//create new 2D array
		gridSize = size;
		grid = new TileState[this.gridSize][this.gridSize];

		//fills the grid with tiles
		clearGrid();
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public TileState getLocation(int x, int y) throws IndexOutOfBoundsException{
		return grid[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return gridSize;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player the enum representing the moved player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, TileState player){
		if (getLocation(x, y) == TileState.NONE) {
			grid[x][y] = player;
			return true;
		}

		return false;

	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){

		for (int x = 0; x < gridSize; x++)
			for (int y = 0; y < gridSize; y++)
				grid[x][y] = TileState.NONE;
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(TileState player){


		//check vertical
		boolean vertical = isStraightWinner(player, point -> getLocation(point.X, point.Y));
		if (vertical)
			return true;

		//check horizontal
		boolean horizontal = isStraightWinner(player, point -> getLocation(point.Y, point.X));
		if (horizontal)
			return true;

		//check diagonal
		return isDiagonalWinner(player);
	}

	private boolean isStraightWinner(TileState player, Function<Point, TileState> getLocation) {
		for (int x = 0; x < gridSize; x++) {
			int concurrent_tiles = 0;
			for (int y = 0; y < gridSize; y++) {
				if (getLocation.apply(new Point(x, y)) == player)
					concurrent_tiles++;
				else
					concurrent_tiles = 0;

				if (concurrent_tiles >= INROW)
					return true;
			}
		}

		return false;
	}

	private boolean isDiagonalWinner(TileState player) {

		for (int ix = 0; ix < gridSize; ix++) {
			int y = 0;
			if (CheckDiagonal(player, ix, y)) return true;
		}

		for (int iy = 1; iy < gridSize; iy++) {
			int x = 0;
			if (CheckDiagonal(player, x, iy)) return true;
		}

		return false;


	}

	private boolean CheckDiagonal(TileState player, int x, int y) {
		int concurrentTiles = 0;
		while (x < gridSize && y < gridSize) {
			if (getLocation(x, y) == player)
				concurrentTiles++;
			else
				concurrentTiles = 0;

			if (concurrentTiles >= INROW)
				return true;
			x++;
			y++;
		}
		return false;
	}

	private static class Point {
		public final int X;
		public final int Y;

		public Point(int x, int y) {
			X = x;
			Y = y;
		}
	}


	
	
}
