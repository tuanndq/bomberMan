package uet.CodeToanBug.bomberMan.entities.tile.powerup;

import uet.CodeToanBug.bomberMan.Game;
import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.bomb.Bomb;
import uet.CodeToanBug.bomberMan.entities.bomb.DirectionalExplosion;
import uet.CodeToanBug.bomberMan.entities.bomb.Explosion;
import uet.CodeToanBug.bomberMan.entities.mob.Player;
import uet.CodeToanBug.bomberMan.graphics.Sprite;

public class PowerupBombs extends Powerup {

	public PowerupBombs(int x, int y, int level, Sprite sprite) {
		super(x, y, level, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			((Player) e).addPowerup(this);
			remove();

			return true;
		}

		if (e instanceof DirectionalExplosion) {

			remove();

			return false;
		}

		return false;
	}
	
	@Override
	public void setValues1() {
		_active = true;
		Game.addBombRate1(1);
	}

	public void setValues2() {
		_active = true;
		Game.addBombRate2(1);
	}

	public void addValue1(int i) {
		Game.addBombRate1(i);
	}

	public void addValue2(int i) {
		Game.addBombRate2(i);
	}
}
