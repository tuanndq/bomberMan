package com.carlosflorencio.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keysp1 = new boolean[120]; //120 is enough to this game
	private boolean[] keysp2 = new boolean[120]; //120 is enough to this game
	public boolean up1, down1, left1, right1, space;
	public boolean up2, down2, left2, right2, enter;

	public void update() {
		up1 = keysp1[KeyEvent.VK_W];
		down1 = keysp1[KeyEvent.VK_S];
		left1 = keysp1[KeyEvent.VK_A];
		right1 = keysp1[KeyEvent.VK_D];
		space = keysp1[KeyEvent.VK_SPACE];

		up2 = keysp2[KeyEvent.VK_UP];
		down2 = keysp2[KeyEvent.VK_DOWN];
		left2 = keysp2[KeyEvent.VK_LEFT];
		right2 = keysp2[KeyEvent.VK_RIGHT];
		enter = keysp2[KeyEvent.VK_ENTER];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keysp1[e.getKeyCode()] = true;
		keysp2[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysp1[e.getKeyCode()] = false;
		keysp2[e.getKeyCode()] = false;
	}

}
