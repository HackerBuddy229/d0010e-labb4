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

	private boolean hasWon = false;
	private TileState winner = TileState.NONE;
	
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
			setChanged();
			notifyObservers();
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

		clearWin();

		setChanged();
		notifyObservers();
	}

	private void clearWin() {
		this.hasWon = false;
		this.winner = TileState.NONE;
	}
	
	/**
	 * Check if a player has INROW in row
	 * 
	 * @param player the player to check for
	 * @return true if player has INROW in row, false otherwise
	 */
	public boolean isWinner(TileState player){

		if (hasWon)
			return this.winner == player;
		return false;
	}


	/**
	 * Checks if a certain move wins a game for a player
	 * @param y the y coordinate
	 * @param x the x coordinate
	 * @param player the player making the move
	 */
	private void checkWin(int y, int x, TileState player) {
		if (hasWon)
			return;

		for (Axis axis: Axis.values()) {
			if (checkTileArrayWin(getAxis(y, x, (INROW*2)+1, axis), player)) {
				hasWon = true;
				winner = player;
				break;
			}
		}
	}

	/**
	 * Checks if an array has a continuous stretch of tileState equal to INROW
	 * @param array the checked array
	 * @param player the state which needs to be continues
	 * @return weather or not the array meets the condition
	 */
	private boolean checkTileArrayWin(TileState[] array, TileState player) {
		//dec cont
		int cont = 0;

		//for all
		for (TileState state: array) {
			//if tile == player; + or 0
			cont = state == player ? cont + 1 : 0;
			if (cont >= INROW)
				return true;
		}
		return false;
	}

	/**
	 * Gets the tileStates along an axis and range depending on a point
	 * @param originY the y coordinate
	 * @param originX the x coordinate
	 * @param range the total range along the axis where to include tileStates
	 * @param axis the axis to follow
	 * @return an array of TileStates for comparison
	 */
	private TileState[] getAxis(int originY, int originX, int range, Axis axis) {
		//declare out[]
		//declare index
		TileState[] out = new TileState[range];
		int index = 0;

		//for 0 -> range/2
		for (int count = 0; count <= range/2; count++) {
			if (originX <= 0 || originX >= gridSize-1 || originY <= 0 || originY >= gridSize-1)
				break;

			// switch xy on axis
			switch (axis) {
				case EAST_WEST:
					originX--;
					break;
				case NORTH_SOUTH:
					originY--;
					break;
				case NORTHEAST_SOUTHWEST:
					originX--;
					originY++;
					break;
				case NORTHWEST_SOUTHEAST:
					originY--;
					originX--;
			}

		}



		//for 0 -> range
		for (int count = 0; count < range; count++) {
			//add xy
			try {
				out[count] = getLocation(originX, originY);
			} catch (IndexOutOfBoundsException ex) {
				out[count] = TileState.NONE;
			}

			//switch xy
			switch (axis) {
				case EAST_WEST:
					originX++;
					break;
				case NORTH_SOUTH:
					originY++;
					break;
				case NORTHEAST_SOUTHWEST:
					originX++;
					originY--;
					break;
				case NORTHWEST_SOUTHEAST:
					originY++;
					originX++;
			}

			//until try or i == range
			if (originX <= 0 || originX >= gridSize-1 || originY <= 0 || originY >= gridSize-1)
				break;
		}

		return out;
	}

	/**
	 * The 2 dimensional axis where a game can be won
	 */
	private enum Axis {
		NORTH_SOUTH,
		EAST_WEST,
		NORTHEAST_SOUTHWEST,
		NORTHWEST_SOUTHEAST
	}





	
	
}
