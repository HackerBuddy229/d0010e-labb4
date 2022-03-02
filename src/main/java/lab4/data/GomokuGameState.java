/*
 * Created on 2007 feb 8
 */
package lab4.data;

import lab4.client.GomokuClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Observable;
import java.util.Observer;



/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

	public int getDEFAULT_SIZE() {
		return DEFAULT_SIZE;
	}

	// Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;

	private void setCurrentState(GameState currentState) {
		this.currentState = currentState;

		switch (currentState) {
			case MY_TURN:
				setMessage("It's your turn");
				break;
			case OTHER_TURN:
				setMessage("Waiting for other players turn");
				break;
			default:
				this.setChanged();
				this.notifyObservers();
		}
	}

	private GameState currentState;
	
	private GomokuClient client;

	private void setMessage(String message) {
		this.message = message;
		this.setChanged();
		this.notifyObservers();
	}

	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The lab4.client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = GameState.NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		// is players turn
		if (currentState == GameState.MY_TURN) {
			//is in bounds
			try {
				TileState currentTile = gameGrid.getLocation(x, y);
				if (currentTile == TileState.NONE) {
					boolean result = gameGrid.move(x, y, TileState.ME);
					if (result) {
						client.sendMoveMessage(x, y);
						setCurrentState(GameState.OTHER_TURN);
						
						//check win
						if (gameGrid.isWinner(TileState.ME))
							setWinner(TileState.ME);
					}
						
				}
			} catch (IndexOutOfBoundsException ex) {
				setMessage("tried to move out of bounds!");
			}
		} else switch (currentState) {
			case OTHER_TURN:
				setMessage("Not your turn");
				break;
			case NOT_STARTED:
				setMessage("Game not started");
		}
	}

	private void setWinner(TileState player) {
		setCurrentState(GameState.NOT_STARTED);
		switch (player) {
			case ME:
				setMessage("You have won!!!");
				break;
			case OTHER:
				setMessage("Your opponent won");
		}
	}

	/**
	 * Starts a new game with the current lab4.client
	 */
	public void newGame(){
		setCurrentState(GameState.OTHER_TURN);
		gameGrid.clearGrid();
		client.sendNewGameMessage();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		setCurrentState(GameState.MY_TURN);
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		gameGrid.clearGrid();
		setCurrentState(GameState.NOT_STARTED);
		setMessage("Opponent disconnected!");
	}
	
	/**
	 * The player disconnects from the lab4.client
	 */
	public void disconnect(){
		setCurrentState(GameState.NOT_STARTED);
		setMessage("No game in progress");
		client.disconnect();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y){
		boolean result = gameGrid.move(x, y, TileState.OTHER);
		if (!result)
			throw new RuntimeException("Received faulty move");
		setCurrentState(GameState.MY_TURN);
		setMessage("Your turn");

		//check win

		if (gameGrid.isWinner(TileState.OTHER)) {
			setCurrentState(GameState.NOT_STARTED);
			setMessage("The other player has won!!!");
		}
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = GameState.MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = GameState.OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();
	}
	
}
