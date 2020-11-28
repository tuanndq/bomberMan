package uet.CodeToanBug.bomberMan.entities.bomb;

import uet.CodeToanBug.bomberMan.Board;
import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.mob.Mob;
import uet.CodeToanBug.bomberMan.graphics.Screen;
import uet.CodeToanBug.bomberMan.graphics.Sprite;
import uet.CodeToanBug.bomberMan.sound_effective.Sound;


public class Explosion extends Entity {

	protected boolean _last = false;
	protected Board _board;
	protected Sprite _sprite1;

	public Explosion(int x, int y, int direction, boolean last, Board board) {
		_x = x;
		_y = y;
		_last = last;
		_board = board;
		
		switch (direction) {
			case 0:
				if(last == false) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_top_last2;
				}
			break;
			case 1:
				if(last == false) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(last == false) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(last == false) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
		Sound.playBombExplose();
	}
	
	@Override
	public void render(Screen screen) {
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	@Override
	public void update() {}

	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Mob) {
			((Mob)e).kill();
		}
		
		return true;
	}
	

}