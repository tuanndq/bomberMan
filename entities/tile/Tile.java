package uet.CodeToanBug.bomberMan.entities.tile;

import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.graphics.Screen;
import uet.CodeToanBug.bomberMan.graphics.Sprite;
import uet.CodeToanBug.bomberMan.level.Coordinates;

public abstract class Tile extends Entity {
	
	
	public Tile(int x, int y, Sprite sprite) {
		_x = x;
		_y = y;
		_sprite = sprite;
	}
	
	@Override
	public boolean collide(Entity e) {
		return false;
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity( Coordinates.tileToPixel(_x), Coordinates.tileToPixel(_y), this);
	}
	
	@Override
	public void update() {}
}
