package com.carlosflorencio.bomberman.entities.mob;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.carlosflorencio.bomberman.Board;
import com.carlosflorencio.bomberman.Game;
import com.carlosflorencio.bomberman.entities.Entity;
import com.carlosflorencio.bomberman.entities.Message;
import com.carlosflorencio.bomberman.entities.bomb.Bomb;
import com.carlosflorencio.bomberman.entities.bomb.DirectionalExplosion;
import com.carlosflorencio.bomberman.entities.mob.enemy.Enemy;
import com.carlosflorencio.bomberman.entities.tile.powerup.Powerup;
import com.carlosflorencio.bomberman.graphics.Screen;
import com.carlosflorencio.bomberman.graphics.Sprite;
import com.carlosflorencio.bomberman.input.Keyboard;
import com.carlosflorencio.bomberman.level.Coordinates;

public class Player extends Mob {
	
	private List<Bomb> _bombs1;
	private List<Bomb> _bombs2;
	protected Keyboard _input;
	protected int _index;

	protected int _timeBetweenPutBombs = 0;
	protected int _timeBetweenPutBombs1 = 0;

	public static List<Powerup> _powerups = new ArrayList<Powerup>();
	
	
	public Player(int x, int y, Board board, int index) {
		super(x, y, board);
		if (index == 1) {
			_bombs1 = _board.getBombs1();
		}
		if (index == 2) {
			_bombs2 = _board.getBombs2();
		}
		_input = _board.getInput();
		_sprite = Sprite.player_right;
		_index = index;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Update & Render
	|--------------------------------------------------------------------------
	 */
	@Override
	public void update() {
		clearBombs();
		if(_alive == false) {
			afterKill();
			return;
		}
		
		if(_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0; else _timeBetweenPutBombs--;

		animate();
		
		calculateMove();
		
		detectPlaceBomb();
	}
	
	@Override
	public void render(Screen screen) {
		calculateXOffset();
		
		if(_alive)
			chooseSprite();
		else
			_sprite = Sprite.player_dead1;
		
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	public void calculateXOffset() {
		int xScroll = Screen.calculateXOffset(_board, this);
		Screen.setOffset(xScroll, 0);
	}


	/*
	|--------------------------------------------------------------------------
	| Mob Unique
	|--------------------------------------------------------------------------
	 */
	private void detectPlaceBomb() {
		if (_index == 1) {
			if(_input.space && Game.getBombRate1() > 0 && _timeBetweenPutBombs < 0) {

				int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
				int yt = Coordinates.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() ); //subtract half player height and minus 1 y position

				placeBomb(xt,yt);
				Game.addBombRate1(-1);

				_timeBetweenPutBombs = 30;
			}
		}

		if (_index == 2) {
			if(_input.enter && Game.getBombRate2() > 0 && _timeBetweenPutBombs < 0) {

				int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
				int yt = Coordinates.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() ); //subtract half player height and minus 1 y position

				placeBomb(xt,yt);
				Game.addBombRate2(-1);

				_timeBetweenPutBombs = 30;
			}
		}
	}
	
	protected void placeBomb(int x, int y) {
		Bomb b = new Bomb(x, y, _board, _index);
		if (_index == 1) {
			_board.addBomb1(b);
		}
		if (_index == 2) {
			_board.addBomb2(b);
		}
	}
	
	private void clearBombs() {
		if (_index == 1) {
			Iterator<Bomb> bs1 = _bombs1.iterator();
			Bomb b;
			while(bs1.hasNext()) {
				b = bs1.next();
				if(b.isRemoved())  {
					bs1.remove();
					Game.addBombRate1(1);
				}
			}
		}
		if (_index == 2) {
			Iterator<Bomb> bs2 = _bombs2.iterator();
			Bomb b;
			while(bs2.hasNext()) {
				b = bs2.next();
				if(b.isRemoved())  {
					bs2.remove();
					Game.addBombRate2(1);
				}
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Colide & Kill
	|--------------------------------------------------------------------------
	 */
	@Override
	public void kill() {
		if(!_alive) return;

		_alive = false;
		
		_board.addLives(-1);

		Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if (_index == 1) {
				if(_bombs1.size() == 0) {

					if(_board.getLives() == 0)
						_board.endGame();
					else
						_board.restartLevel();
				}
			}

			if (_index == 2) {
				if(_bombs2.size() == 0) {

					if(_board.getLives() == 0)
						_board.endGame();
					else
						_board.restartLevel();
				}
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Movement
	|--------------------------------------------------------------------------
	 */
	@Override
	protected void calculateMove() {
		int xa = 0, ya = 0;
		int xb = 0, yb = 0;

		if (_index == 1) {
			if(_input.up1) ya--;
			if(_input.down1) ya++;
			if(_input.left1) xa--;
			if(_input.right1) xa++;

			if(xa != 0 || ya != 0)  {
				move(xa * Game.getPlayerSpeed1(), ya * Game.getPlayerSpeed1());
				_moving = true;
			} else {
				_moving = false;
			}
		}
		if (_index == 2) {
			if(_input.up2) yb--;
			if(_input.down2) yb++;
			if(_input.left2) xb--;
			if(_input.right2) xb++;

			if(xb != 0 || yb != 0)  {
				move(xb * Game.getPlayerSpeed2(), yb * Game.getPlayerSpeed2());
				_moving = true;
			} else {
				_moving = false;
			}
		}
	}
	
	@Override
	public boolean canMove(double x, double y) {
		for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
			double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE; //divide with tiles size to pass to tile coordinate
			double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE; //these values are the best from multiple tests
			
			Entity a = _board.getEntity(xt, yt, this);
			
			if(!a.collide(this))
				return false;
		}
		
		return true;
	}

	@Override
	public void move(double xa, double ya) {
		if(xa > 0) _direction = 1;
		if(xa < 0) _direction = 3;
		if(ya > 0) _direction = 2;
		if(ya < 0) _direction = 0;
		
		if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
			_y += ya;
		}
		
		if(canMove(xa, 0)) {
			_x += xa;
		}
	}
	
	@Override
	public boolean collide(Entity e) {
		if(e instanceof DirectionalExplosion) {
			kill();
			return false;
		}
		
		if(e instanceof Enemy) {
			kill();
			return true;
		}
		
		return true;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	public void addPowerup(Powerup p) {
		if(p.isRemoved()) return;
		
		_powerups.add(p);

		if (_index == 1) {
			p.setValues1();
		} else if (_index == 2) {
			p.setValues2();
		}
	}
	
	public void clearUsedPowerups() {
		Powerup p;
		for (int i = 0; i < _powerups.size(); i++) {
			p = _powerups.get(i);
			if(p.isActive() == false)
				_powerups.remove(i);
		}
	}
	
	public void removePowerups() {
		for (int i = 0; i < _powerups.size(); i++) {
				_powerups.remove(i);
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Sprite
	|--------------------------------------------------------------------------
	 */
	private void chooseSprite() {
		switch(_direction) {
		case 0:
			_sprite = Sprite.player_up;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
			}
			break;
		case 1:
			_sprite = Sprite.player_right;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
			}
			break;
		case 2:
			_sprite = Sprite.player_down;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
			}
			break;
		case 3:
			_sprite = Sprite.player_left;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
			}
			break;
		default:
			_sprite = Sprite.player_right;
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
			}
			break;
		}
	}
}
