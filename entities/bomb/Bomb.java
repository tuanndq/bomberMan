package uet.CodeToanBug.bomberMan.entities.bomb;

import uet.CodeToanBug.bomberMan.Board;
import uet.CodeToanBug.bomberMan.Game;
import uet.CodeToanBug.bomberMan.entities.AnimatedEntitiy;
import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.mob.Mob;
import uet.CodeToanBug.bomberMan.entities.mob.Player;
import uet.CodeToanBug.bomberMan.graphics.Screen;
import uet.CodeToanBug.bomberMan.graphics.Sprite;
import uet.CodeToanBug.bomberMan.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 90;
	public int _timeAfter = 20;
	
	protected Board _board;
	protected boolean _allowedToPassThrough = true;
	protected DirectionalExplosion[] _explosions = null;
	protected boolean _exploded = false;
	protected int _index;
	
	public Bomb(int x, int y,Board board, int index) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
		_index = index;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explosion();
			else
				updateExplosions();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderExplosions(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderExplosions(Screen screen) {
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].render(screen);
		}
	}
	
	public void updateExplosions() {
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].update();
		}
	}
	
	public void explode() {
		_timeToExplode = 0;
	}
	
	protected void explosion() {
		_allowedToPassThrough = true;
		_exploded = true;
		
		Mob a = _board.getMobAt(_x, _y);
		if(a != null)  {
			a.kill();
		}
		
		_explosions = new DirectionalExplosion[4];
		
		for (int i = 0; i < _explosions.length; i++) {
			if (_index == 1) {
				_explosions[i] = new DirectionalExplosion((int)_x, (int)_y, i, Game.getBombRadius1(), _board);
			}
			if (_index == 2) {
				_explosions[i] = new DirectionalExplosion((int)_x, (int)_y, i, Game.getBombRadius2(), _board);
			}
		}
	}
	
	public Explosion explosionAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _explosions.length; i++) {
			if(_explosions[i] == null) return null;
			Explosion e = _explosions[i].explosionAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}
	

	

	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			double diffX = e.getX() - Coordinates.tileToPixel(getX());
			double diffY = e.getY() - Coordinates.tileToPixel(getY());
			
			if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) {
				// differences to see if the player has moved out of the bomb, tested values
				_allowedToPassThrough = false;
			}
			
			return _allowedToPassThrough;
		}
		
		if(e instanceof DirectionalExplosion) {
			explode();
			return true;
		}
		
		return false;
	}
}
