package uet.CodeToanBug.bomberMan.entities.tile.destroyable;

import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.bomb.DirectionalExplosion;
import uet.CodeToanBug.bomberMan.entities.tile.Tile;
import uet.CodeToanBug.bomberMan.graphics.Sprite;
import uet.CodeToanBug.bomberMan.sound_effective.Sound;

public class DestroyableTile extends Tile {

	private final int MAX_ANIMATE = 7500; //save the animation status and dont let this get too big
	private int _animate = 0;
	protected boolean _destroyed = false;
	protected int _timeToDisapear = 20;
	protected Sprite _belowSprite = Sprite.grass; //default
	
	public DestroyableTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		if(_destroyed) {
			if(_animate < MAX_ANIMATE) _animate++; else _animate = 0; //reset animation
			if(_timeToDisapear > 0) 
				_timeToDisapear--;
			else
				remove();
		}
	}


	public void destroy() {
		_destroyed = true;
		Sound.playDestroy();
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof DirectionalExplosion)
			destroy();
			
		return false;
	}
	
	public void addBelowSprite(Sprite sprite) {
		_belowSprite = sprite;
	}
	
	protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
		int calc = _animate % 30;
		
		if(calc < 10) {
			return normal;
		}
			
		if(calc < 20) {
			return x1;
		}
			
		return x2;
	}
	
}
