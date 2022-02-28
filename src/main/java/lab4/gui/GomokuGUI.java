package lab4.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import lab4.data.GomokuGameState;
import lab4.client.GomokuClient;

import javax.swing.*;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private JButton connectbutton;
	private JButton newgamebutton;
	private JButton disconnectbutton;
	private JLabel messagelabel;


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

		JFrame display = new JFrame();
		SpringLayout layout = new SpringLayout();
		display.setLayout(layout);

		connectbutton = new JButton("");
		connectbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

			}
		});

		newgamebutton = new JButton("");
		newgamebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

			}
		});

		disconnectbutton = new JButton("");
		disconnectbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

			}
		});

		messagelabel = new JLabel("Label");



	}
	
	
	public void update(Observable arg0, Object arg1) {

		// update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectbutton.setEnabled(true);
				newgamebutton.setEnabled(false);
				disconnectbutton.setEnabled(false);
			}else{
				connectbutton.setEnabled(false);
				newgamebutton.setEnabled(true);
				disconnectbutton.setEnabled(true);
			}
		}

		// update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messagelabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
