package uet.CodeToanBug.bomberMan.level;

import uet.CodeToanBug.bomberMan.Board;


import java.io.IOException;

public abstract class Level {

	protected int _width, _height;
	protected static int _level;
	protected String[] _lineTiles;
	protected Board _board;



	public Level(String path, Board board) throws IOException {
		loadLevel(path);
		_board = board;
	}


	public abstract void loadLevel(String path) throws IOException;
	
	public abstract void createEntities();



	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public static int getLevel() {
		return _level;
	}

}
