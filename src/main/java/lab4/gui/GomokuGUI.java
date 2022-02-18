package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import lab4.data.GomokuGameState;
import lab4.client.GomokuClient;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The lab4.client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		
	}
	
	
	public void update(Observable arg0, Object arg1) {
//
//		// update the buttons if the connection status has changed
//		if(arg0 == lab4.client){
//			if(lab4.client.getconnectionstatus() == gomokuclient.unconnected){
//				connectbutton.setenabled(true);
//				newgamebutton.setenabled(false);
//				disconnectbutton.setenabled(false);
//			}else{
//				connectbutton.setenabled(false);
//				newgamebutton.setenabled(true);
//				disconnectbutton.setenabled(true);
//			}
//		}
//
//		// update the status text if the gamestate has changed
//		if(arg0 == gamestate){
//			messagelabel.settext(gamestate.getmessagestring());
//		}
		
	}
	
}
