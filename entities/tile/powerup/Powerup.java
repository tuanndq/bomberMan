package uet.CodeToanBug.bomberMan.entities.tile.powerup;

import uet.CodeToanBug.bomberMan.entities.tile.Tile;
import uet.CodeToanBug.bomberMan.graphics.Sprite;
import uet.CodeToanBug.bomberMan.level.FileLevel;

public abstract class Powerup extends Tile {

	protected int _duration = 15 ;
	protected boolean _active = false;
	protected int _level;
	
	public Powerup(int x, int y, int level, Sprite sprite) {
		super(x, y, sprite);
		_level = level;
	}
	
	public abstract void setValues1();

	public abstract void setValues2();

	public void removeLive() {
		if(_duration > 0)
			_duration--;

		if(_duration == 0)
			_active = false;
	}

	public int getLevel() {
		return _level;
	}

	public boolean isActive() {
		return _active;
	}
}
