package lab4.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import lab4.data.GameGrid;
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


		GamePanel gamePanel = new GamePanel(gamestate.getGameGrid());


		display.addMouseListener(new Listner(gamePanel, gamestate));

		connectbutton = new JButton("Connect");
		connectbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				new ConnectionWindow(client);
			}
		});

		newgamebutton = new JButton("New game");
		newgamebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gamestate.newGame();
			}
		});

		disconnectbutton = new JButton("Disconnect");
		disconnectbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gamestate.disconnect();
			}
		});

		messagelabel = new JLabel(gamestate.getMessageString());

		display.add(gamePanel);
		display.add(connectbutton);
		display.add(newgamebutton);
		display.add(disconnectbutton);
		display.add(messagelabel);

		display.pack();

		display.setSize(gamePanel.getUNIT_SIZE() * gamestate.getDEFAULT_SIZE() + 40, gamePanel.getUNIT_SIZE() * gamestate.getDEFAULT_SIZE() + 200);

		layout.putConstraint(SpringLayout.WEST, gamePanel, 5, SpringLayout.WEST, display);
		layout.putConstraint(SpringLayout.NORTH, gamePanel, 5, SpringLayout.NORTH, display);
		layout.putConstraint(SpringLayout.NORTH, connectbutton, 5, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.NORTH, newgamebutton, 5, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.NORTH, disconnectbutton, 5, SpringLayout.SOUTH, gamePanel);

		layout.putConstraint(SpringLayout.WEST, messagelabel, 5, SpringLayout.WEST, display);
		layout.putConstraint(SpringLayout.NORTH, messagelabel, 5, SpringLayout.SOUTH, connectbutton);

		layout.putConstraint(SpringLayout.WEST, connectbutton, 5, SpringLayout.WEST, display);
		layout.putConstraint(SpringLayout.WEST, newgamebutton, 5, SpringLayout.EAST, connectbutton);
		layout.putConstraint(SpringLayout.WEST, disconnectbutton, 5, SpringLayout.EAST, newgamebutton);

		display.setVisible(true);



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

	private class Listner implements MouseListener {

		private GamePanel panel;
		private GomokuGameState state;

		public Listner(GamePanel panel, GomokuGameState state) {
			this.panel = panel;
			this.state = state;
		}

		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			int btn = mouseEvent.getButton();
			if (btn != 1)
				return;

			Point point = mouseEvent.getPoint();
			int[] realCoordinates = panel.getGridPosition(point.x, point.y);
			state.move(realCoordinates[0], realCoordinates[1]);
		}

		@Override
		public void mousePressed(MouseEvent mouseEvent) {

		}

		@Override
		public void mouseReleased(MouseEvent mouseEvent) {

		}

		@Override
		public void mouseEntered(MouseEvent mouseEvent) {

		}

		@Override
		public void mouseExited(MouseEvent mouseEvent) {

		}
	}
	
}
