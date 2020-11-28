package uet.CodeToanBug.bomberMan;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uet.CodeToanBug.bomberMan.entities.Entity;

import uet.CodeToanBug.bomberMan.entities.bomb.Bomb;
import uet.CodeToanBug.bomberMan.entities.bomb.Explosion;
import uet.CodeToanBug.bomberMan.entities.mob.Mob;
import uet.CodeToanBug.bomberMan.entities.mob.Player;
import uet.CodeToanBug.bomberMan.entities.tile.powerup.Powerup;
import uet.CodeToanBug.bomberMan.graphics.Screen;
import uet.CodeToanBug.bomberMan.input.Keyboard;
import uet.CodeToanBug.bomberMan.level.FileLevel;
import uet.CodeToanBug.bomberMan.level.Level;
import uet.CodeToanBug.bomberMan.sound_effective.Sound;

public class Board {


	protected Level _level;
	protected Game _game;
	protected Keyboard _input;
	protected Screen _screen;
	
	public Entity[] _entities;
	public List<Mob> _mobs = new ArrayList<Mob>();
	protected List<Bomb> _bombs1 = new ArrayList<Bomb>();
	protected List<Bomb> _bombs2 = new ArrayList<Bomb>();

	
	private int _screenToShow = -1; //1:endgame, 2:changelevel, 3:paused
	
	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	private int _lives = Game.LIVES;
	
	public Board(Game game, Keyboard input, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		
		changeLevel(1);
	}
	


	public void update() {
		if( _game.isPaused() ) return;
		
		updateEntities();
		updateMobs();
		updateBombs();
		detectEndGame();
		
		for (int i = 0; i < _mobs.size(); i++) {
			Mob a = _mobs.get(i);
			if(((Entity)a).isRemoved()) _mobs.remove(i);
		}
	}



	public void render(Screen screen) {
		if( _game.isPaused() ) return;

		for (int y = 0; y < 13; y++) {
			for (int x = 0; x < 31; x++) {
				_entities[x + y * _level.getWidth()].render(screen);
			}
		}
		
		renderBombs(screen);
		renderMobs(screen);
		
	}
	


	
	@SuppressWarnings("static-access")
	private void resetProperties() {
		_points = Game.POINTS;
		_lives = getLives();
		Player._powerups1.clear();
		Player._powerups2.clear();
		
		_game.playerSpeed1 = 1.0;
		_game.playerSpeed2 = 1.0;

		_game.bombRadius1 = 1;
		_game.bombRadius2 = 1;

		_game.bombRate1 = 1;
		_game.bombRate2 = 1;
	}

	public void restartLevel() {
		resetProperties();
		changeLevel(_level.getLevel());
	}
	
	public void nextLevel() {
		addLives(1);
		changeLevel(_level.getLevel() + 1);
	}
	
	public void changeLevel(int level) {
		Sound.playStartStage();
		_time = Game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_mobs.clear();
		_bombs1.clear();
		_bombs2.clear();

		
		try {
			_level = new FileLevel("levels/Level" + level + ".txt", this);
			_entities = new Entity[_level.getHeight() * _level.getWidth()];
			
			_level.createEntities();
		} catch (Exception e) {
			endGame();
		}
	}
	
	public boolean isPowerupUsed(int x, int y, int level) {
		Powerup p;
		for (int i = 0; i < Player._powerups1.size(); i++) {
			p = Player._powerups1.get(i);
			if(p.getX() == x && p.getY() == y && level == p.getLevel())
				return true;
		}

		for (int i = 0; i < Player._powerups2.size(); i++) {
			p = Player._powerups2.get(i);
			if(p.getX() == x && p.getY() == y && level == p.getLevel())
				return true;
		}
		
		return false;
	}
	

	protected void detectEndGame() {
		if(_time <= 0)
			restartLevel();
	}
	
	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();
		Sound.playLose();
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < _mobs.size(); i++) {
			if(_mobs.get(i) instanceof Player == false)
				++total;
		}
		
		return total == 0;
	}
	
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g, _points, "Gà Quá");
				break;
			case 2:
				_screen.drawChangeLevel(g, _level.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
		}
	}
	

	public Entity getEntity(double x, double y, Mob m) {
		
		Entity res = null;
		
		res = getExplosionAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getMobAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs1() {
		return _bombs1;
	}

	public List<Bomb> getBombs2() {
		return _bombs2;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs1 = _bombs1.iterator();
		Iterator<Bomb> bs2 = _bombs2.iterator();
		Bomb b;
		while(bs1.hasNext()) {
			b = bs1.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		while(bs2.hasNext()) {
			b = bs2.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}
	
	public Mob getMobAt(double x, double y) {
		Iterator<Mob> itr = _mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur.getXTile() == x && cur.getYTile() == y)
				return cur;
		}
		
		return null;
	}
	
	public Player getPlayer() {
		Iterator<Mob> itr = _mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Player)
				return (Player) cur;
		}
		
		return null;
	}
	
	public Mob getMobAtExcluding(int x, int y, Mob a) {
		Iterator<Mob> itr = _mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public Explosion getExplosionAt(int x, int y) {
		Iterator<Bomb> bs1 = _bombs1.iterator();
		Iterator<Bomb> bs2 = _bombs2.iterator();
		Bomb b;
		while(bs1.hasNext()) {
			b = bs1.next();
			
			Explosion e = b.explosionAt(x, y);
			if(e != null) {
				return e;
			}
		}
		while(bs2.hasNext()) {
			b = bs2.next();

			Explosion e = b.explosionAt(x, y);
			if(e != null) {
				return e;
			}
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return _entities[(int)x + (int)y * _level.getWidth()];
	}
	

	public void addEntitie(int pos, Entity e) {
		_entities[pos] = e;
	}
	
	public void addMob(Mob e) {
		_mobs.add(e);
	}
	
	public void addBomb1(Bomb e) {
		_bombs1.add(e);
	}

	public void addBomb2(Bomb e) {
		_bombs2.add(e);
	}
	


	protected void renderEntities(Screen screen) {
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].render(screen);
		}
	}
	
	protected void renderMobs(Screen screen) {
		Iterator<Mob> itr = _mobs.iterator();
		
		while(itr.hasNext())
			itr.next().render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr1 = _bombs1.iterator();
		Iterator<Bomb> itr2 = _bombs2.iterator();

		while(itr1.hasNext())
			itr1.next().render(screen);
		while(itr2.hasNext())
			itr2.next().render(screen);
	}
	

	

	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}
	
	protected void updateMobs() {
		if( _game.isPaused() ) return;
		Iterator<Mob> itr = _mobs.iterator();
		
		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( _game.isPaused() ) return;
		Iterator<Bomb> itr1 = _bombs1.iterator();
		
		while(itr1.hasNext())
			itr1.next().update();

		Iterator<Bomb> itr2 = _bombs2.iterator();

		while(itr2.hasNext())
			itr2.next().update();
	}
	


	public void updatePowerUp() {
		if( _game.isPaused() ) return;
		int length = _mobs.size();
		for (int i = 0; i < length; i++) {
			if (_mobs.get(i) instanceof Player) {
				((Player) _mobs.get(i)).clearUsedPowerups();
			}
		}
	}
	

	public Keyboard getInput() {
		return _input;
	}
	
	public Level getLevel() {
		return _level;
	}
	
	public Game getGame() {
		return _game;
	}
	
	public int getShow() {
		return _screenToShow;
	}
	
	public void setShow(int i) {
		_screenToShow = i;
	}
	
	public int getTime() {
		return _time;
	}
	
	public int getLives() {
		return _lives;
	}

	public int subtractTime() {
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int points) {
		this._points += points;
	}

	public void addLives(int lives) {
		this._lives += lives;
	}
	

	
}
