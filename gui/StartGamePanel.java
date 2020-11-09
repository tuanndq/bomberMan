package com.carlosflorencio.bomberman.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.carlosflorencio.bomberman.Game;
import com.carlosflorencio.bomberman.exceptions.BombermanException;

public class StartGamePanel extends JPanel {
    private Game _game;

    public StartGamePanel(Frame frame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

        try {
            _game = new Game(frame);

            add(_game);

            _game.setVisible(true);

        } catch (BombermanException e) {
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
