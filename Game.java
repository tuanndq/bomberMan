package uet.CodeToanBug.bomberMan;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;


import uet.CodeToanBug.bomberMan.graphics.Screen;
import uet.CodeToanBug.bomberMan.gui.Frame;
import uet.CodeToanBug.bomberMan.input.Keyboard;
import uet.CodeToanBug.bomberMan.sound_effective.Sound;
import uet.CodeToanBug.bomberMan.entities.mob.Player;

public class Game extends Canvas {
	
	/*
	|--------------------------------------------------------------------------
	| Options & Configs
	|--------------------------------------------------------------------------
	 */

	
	public static final int TILES_SIZE = 16,
							WIDTH = TILES_SIZE * 31, //minus one to ajust the window,
							HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 4;
	
	public static final String TITLE = "Nguyễn Quyết Thắng";
	
	//initial configs
	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final double PLAYERSPEED = 1.0;
	
	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 3;
	
	protected static int SCREENDELAY = 3;
	
	
	//can be modified with bonus
	protected static int bombRate1 = BOMBRATE;
	protected static int bombRate2 = BOMBRATE;
	protected static int bombRadius1 = BOMBRADIUS;
	protected static int bombRadius2 = BOMBRADIUS;
	protected static double playerSpeed1 = PLAYERSPEED;
	protected static double playerSpeed2 = PLAYERSPEED;

	//Time in the level screen in seconds
	protected int _screenDelay = SCREENDELAY;
	
	private Keyboard _input;
	private boolean _running = false;
	private boolean _paused = true;
	
	private Board _board;
	private Screen screen;
	private Frame _frame;
	
	//this will be used to render the game, each render is a calculated image saved here
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) throws IOException {
		_frame = frame;
		_frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
	}
	
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		_board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		
		g.dispose();
		bs.show(); //make next buffer visible
	}
	
	private void renderScreen() { //TODO: merge these render methods
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
		_board.update();
	}

	public void start() {
		_running = true;
		long  lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		requestFocus();
		while(_running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
			}

			if(_paused) {
				if(_screenDelay <= 0) { //time passed? lets reset status to show the game
					_board.setShow(-1);
					_paused = false;
				}
				renderScreen();
			} else {
				renderGame();
			}

			if(System.currentTimeMillis() - timer > 1000) { //once per second
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				_frame.setLives(_board.getLives());
				timer += 1000;

//				_board.updatePowerUp();

				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}

	public void reset() {

	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */
	public static double getPlayerSpeed1() {
		return playerSpeed1;
	}

	public static double getPlayerSpeed2() {
		return playerSpeed2;
	}
	
	public static int getBombRate1() {
		return bombRate1;
	}

	public static int getBombRate2() {
		return bombRate2;
	}
	
	public static int getBombRadius1() {
		return bombRadius1;
	}

	public static int getBombRadius2() {
		return bombRadius2;
	}
	
	public static void addPlayerSpeed1(double i) {
		playerSpeed1 += i;
	}

	public static void addPlayerSpeed2(double i) {
		playerSpeed2 += i;
	}
	
	public static void addBombRadius1(int i) {
		bombRadius1 += i;
	}

	public static void addBombRadius2(int i) {
		bombRadius2 += i;
	}
	
	public static void addBombRate1(int i) {
		bombRate1 += i;
	}

	public static void addBombRate2(int i) {
		bombRate2 += i;
	}
	

	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public Keyboard getInput() {
		return _input;
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public void run() {
		_running = true;
		_paused = false;
	}
	
	public void stop() {
		_running = false;
	}
	
	public boolean isRunning() {
		return _running;
	}
	
	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
		_paused = true;
	}
	
}
