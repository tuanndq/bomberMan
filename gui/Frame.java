package uet.CodeToanBug.bomberMan.gui;

import uet.CodeToanBug.bomberMan.Game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

	public static int _numberPlayer;
	public GamePanel _gamePanel;
	private JPanel _containerPanel;
	private InfoPanel _infopanel;

	
	private Game _game;

	public Frame(int index) {
		_numberPlayer = index;
		_containerPanel = new JPanel(new BorderLayout());

		_gamePanel = new GamePanel(this);
		_infopanel = new InfoPanel(_gamePanel.getGame());

		_containerPanel.add(_infopanel, BorderLayout.PAGE_START);
		_containerPanel.add(_gamePanel, BorderLayout.PAGE_END);
		
		_game = _gamePanel.getGame();
		
		this.add(_containerPanel);
		
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
	}

	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}
	
	public void setLives(int lives) {
		_infopanel.setLives(lives);
	}
	
	public void setPoints(int points) {
		_infopanel.setPoints(points);
	}
	

	
}
