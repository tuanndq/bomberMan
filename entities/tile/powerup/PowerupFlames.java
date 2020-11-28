package uet.CodeToanBug.bomberMan.entities.tile.powerup;

import uet.CodeToanBug.bomberMan.Game;
import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.mob.Player;
import uet.CodeToanBug.bomberMan.graphics.Sprite;

public class PowerupFlames extends Powerup {

	public PowerupFlames(int x, int y, int level, Sprite sprite) {
		super(x, y, level, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			((Player) e).addPowerup(this);
			remove();

			return true;
		}
		
		return false;
	}
	
	@Override
	public void setValues1() {
		_active = true;
		Game.addBombRadius1(1);
	}

	public void setValues2() {
		_active = true;
		Game.addBombRadius2(1);
	}

	public void addValue1(int i) {
		Game.addBombRadius1(i);
	}

	public void addValue2(int i) {
		Game.addBombRadius2(i);
	}
}
