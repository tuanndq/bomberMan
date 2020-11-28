package uet.CodeToanBug.bomberMan.entities.tile;


import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		//this._sprite.setColor(0xff50a000);
	}



	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
