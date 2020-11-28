package uet.CodeToanBug.bomberMan.entities.mob;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uet.CodeToanBug.bomberMan.Board;
import uet.CodeToanBug.bomberMan.Game;
import uet.CodeToanBug.bomberMan.entities.Entity;
import uet.CodeToanBug.bomberMan.entities.bomb.Bomb;
import uet.CodeToanBug.bomberMan.entities.bomb.DirectionalExplosion;
import uet.CodeToanBug.bomberMan.entities.mob.enemy.Enemy;
import uet.CodeToanBug.bomberMan.entities.tile.powerup.Powerup;
import uet.CodeToanBug.bomberMan.entities.tile.powerup.PowerupBombs;
import uet.CodeToanBug.bomberMan.entities.tile.powerup.PowerupFlames;
import uet.CodeToanBug.bomberMan.entities.tile.powerup.PowerupSpeed;
import uet.CodeToanBug.bomberMan.graphics.Screen;
import uet.CodeToanBug.bomberMan.graphics.Sprite;
import uet.CodeToanBug.bomberMan.input.Keyboard;
import uet.CodeToanBug.bomberMan.level.Coordinates;
import uet.CodeToanBug.bomberMan.sound_effective.Sound;

public class Player extends Mob {
	
	private List<Bomb> _bombs1;
	private List<Bomb> _bombs2;
	protected Keyboard _input;
	protected Sprite _sprite2;

	protected int _index;

	protected int _timeBetweenPutBombs = 0;


	public static List<Powerup> _powerups1 = new ArrayList<Powerup>();

	public static List<Powerup> _powerups2 = new ArrayList<Powerup>();
	
	
	public Player(int x, int y, Board board, int index) {
		super(x, y, board);
		if (index == 1) {
			_bombs1 = _board.getBombs1();
			_sprite = Sprite.player_right;
		}
		if (index == 2) {
			_sprite = Sprite.player_right;
			_bombs2 = _board.getBombs2();
			_sprite2 = Sprite.player2_right;
		}
		_input = _board.getInput();
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


		if (_index == 1) {
			if(_alive)
				chooseSprite();
			else
				_sprite = Sprite.player_dead1;

			screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
		} else {
			if(_alive)
				chooseSprite2();
			else
				_sprite2 = Sprite.player2_dead1;

			screen.renderEntity((int)_x, (int)_y - _sprite2.SIZE, this);
		}
	}


	private void detectPlaceBomb() {
		if (_index == 1) {
			if(_input.space && Game.getBombRate1() > 0 && _timeBetweenPutBombs < 0) {

				int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
				int yt = Coordinates.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() );

				placeBomb(xt,yt);
				Game.addBombRate1(-1);

				_timeBetweenPutBombs = 30;
			}
		}

		if (_index == 2) {
			if(_input.enter && Game.getBombRate2() > 0 && _timeBetweenPutBombs < 0) {

				int xt = Coordinates.pixelToTile(_x + _sprite2.getSize() / 2);
				int yt = Coordinates.pixelToTile( (_y + _sprite2.getSize() / 2) - _sprite2.getSize() ); //subtract half player height and minus 1 y position

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
		Sound.playPlaceNewBomb();
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
	

	@Override
	public void kill() {
		if(!_alive) return;
		Sound.playBomberDie();
		_alive = false;

		_board.addLives(-1);
	}
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if (_index == 1) {
					if(_board.getLives() == 0)
						_board.endGame();
					else
						_board.restartLevel();
			}

			if (_index == 2) {

					if(_board.getLives() == 0)
						_board.endGame();
					else
						_board.restartLevel();
			}
		}
	}
	

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
		for (int c = 0; c < 4; c++) {
			double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE;
			double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE;
			
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

		if(canMove(0, ya)) {
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
	

	public void addPowerup(Powerup p) {
		if(p.isRemoved()) return;
		Sound.playGetNewItem();

		if (_index == 1) {
			_powerups1.add(p);
			p.setValues1();
		} else if (_index == 2) {
			p.setValues2();
			_powerups2.add(p);
		}
	}
	
	public void clearUsedPowerups() {
		Powerup p;
		if (_index == 1) {
			for (int i = 0; i < _powerups1.size(); i++) {
				p = _powerups1.get(i);
				if(p.isActive() == false) {
					_powerups1.remove(i);
					removePowerups(p);
				} else {
					p.removeLive();
				}
			}
		} else {
			for (int i = 0; i < _powerups2.size(); i++) {
				p = _powerups2.get(i);
				if(p.isActive() == false) {
					_powerups2.remove(i);
					removePowerups(p);
				} else {
					p.removeLive();
				}
			}
		}
	}
	
	public void removePowerups(Powerup p) {
		if (_index == 1) {
			if (p instanceof PowerupBombs) {
				((PowerupBombs) p).addValue1(-1);
			}
			if (p instanceof PowerupFlames) {
				((PowerupFlames) p).addValue1(-1);
			}
			if (p instanceof PowerupSpeed) {
				((PowerupSpeed) p).addValue1(-1);
			}
		}
		if (_index == 2) {
			if (p instanceof PowerupBombs) {
				((PowerupBombs) p).addValue2(-1);
			}
			if (p instanceof PowerupFlames) {
				((PowerupFlames) p).addValue2(-1);
			}
			if (p instanceof PowerupSpeed) {
				((PowerupSpeed) p).addValue2(-1);
			}
		}
	}
	

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
		}
	}

	private void chooseSprite2() {
		switch(_direction) {
			case 0:
				_sprite2 = Sprite.player2_up;
				if(_moving) {
					_sprite2 = Sprite.movingSprite(Sprite.player2_up_1, Sprite.player2_up_2, _animate, 20);
				}
				break;
			case 1:
				_sprite2 = Sprite.player2_right;
				if(_moving) {
					_sprite2 = Sprite.movingSprite(Sprite.player2_right_1, Sprite.player2_right_2, _animate, 20);
				}
				break;
			case 2:
				_sprite2 = Sprite.player2_down;
				if(_moving) {
					_sprite2 = Sprite.movingSprite(Sprite.player2_down_1, Sprite.player2_down_2, _animate, 20);
				}
				break;
			case 3:
				_sprite2 = Sprite.player2_left;
				if(_moving) {
					_sprite2 = Sprite.movingSprite(Sprite.player2_left_1, Sprite.player2_left_2, _animate, 20);
				}
				break;
		}
	}

	@Override
	public Sprite getSprite() {
		if (_index == 2) {
			return _sprite2;
		}

		if (_index == 1) {
			return _sprite;
		}
		return _sprite;

	}
}
