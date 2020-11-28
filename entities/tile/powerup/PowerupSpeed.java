package uet.CodeToanBug.bomberMan.entities.tile.powerup;

import uet.CodeToanBug.bomberMan.Game;
import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.mob.Player;
import uet.CodeToanBug.bomberMan.graphics.Sprite;

public class PowerupSpeed extends Powerup {

	public PowerupSpeed(int x, int y, int level, Sprite sprite) {
		super(x, y, level, sprite);
	}

	@Override
	public void setValues1() {
		_active = true;
		Game.addPlayerSpeed1(0.5);
	}

	@Override
	public void setValues2() {
		_active = true;
		Game.addPlayerSpeed2(0.5);
	}

	public void addValue1(int i) {
		Game.addPlayerSpeed1(i);
	}

	public void addValue2(int i) {
		Game.addPlayerSpeed2(i);
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
}
