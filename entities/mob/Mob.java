package uet.CodeToanBug.bomberMan.entities.mob;

import uet.CodeToanBug.bomberMan.Board;
import uet.CodeToanBug.bomberMan.entities.AnimatedEntitiy;
import uet.CodeToanBug.bomberMan.graphics.Screen;

public abstract class Mob extends AnimatedEntitiy {
	
	protected Board _board;
	protected int _direction = -1;
	protected boolean _alive = true;
	protected boolean _moving = false;
	public int _timeAfter = 80;
	
	public Mob(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);
	
	protected abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);
	
	public abstract void kill();
	
	protected abstract void afterKill();
	
	protected abstract boolean canMove(double x, double y);
	


	
}
