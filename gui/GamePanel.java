package uet.CodeToanBug.bomberMan.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;

import uet.CodeToanBug.bomberMan.Game;


public class GamePanel extends JPanel {

	private Game _game;
	
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
		
		try {
			_game = new Game(frame);
			
			add(_game);
			
			_game.setVisible(true);
			
		} catch (IOException e) {
			e.printStackTrace();
			//TODO: so we got a error hum..
			System.exit(0);
		}
		setVisible(true);
		setFocusable(true);
		
	}
	

	
	public Game getGame() {
		return _game;
	}
	
}
