package com.studios.lib;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.studios.entities.Bullet;
import com.studios.main.Game;
import com.studios.wold.Camera;
import com.studios.wold.World;

public class Enemy extends Entity{
	// Declara��o dos valores inalteraveis
	// Declara��o dos valores inalteraveis
	private static final int maxIndex = 4;
	private static final int maxFrames = 20;
	public static int speed = 1;
	private static final int maskx = 8;
	private static final int masky = 8;
	private static final int maskw = 10;
	private static final int maskh = 10;

	// Declara��o dos Arrays sprites
	private BufferedImage[] RightEnemy;
	private BufferedImage[] LeftEnemy;
	private BufferedImage[] TopEnemy;
	private BufferedImage[] DownEnemy;
	private BufferedImage[] RightEnemyDamege;
	private BufferedImage[] LeftEnemyDamege;
	private BufferedImage[] TopEnemyDamege;
	private BufferedImage[] DownEnemyDamege;

	// Variaveis padroes

	public static int life = 1;
	private int frames = 0;
	private int index = 0;
	private int damageFrames = 10;
	private int damageCurrent = 0;
	private int last = 0;
	private int MaxDamege = 3;

	private boolean right;
	private boolean up;
	private boolean left;
	private boolean down = false;
	private boolean moved = false;
	private boolean isDamege = false;
	private boolean isDamaged = false;

	// Construtor para instancia
	public Enemy(int x, int y, int width, int height, BufferedImage Spritesheet) {
		super(x, y, width, height, null);

		// Declara�ao da quantidade de quadros dos Sprites
		RightEnemy = new BufferedImage[5];
		LeftEnemy = new BufferedImage[5];
		TopEnemy = new BufferedImage[5];
		DownEnemy = new BufferedImage[5];
		RightEnemyDamege = new BufferedImage[5];
		LeftEnemyDamege = new BufferedImage[5];
		TopEnemyDamege = new BufferedImage[5];
		DownEnemyDamege = new BufferedImage[5];

		// La�os para pegar os Sprites separadamente montando a anima��o
		for (int i = 0; i < 5; i++) {

			RightEnemy[i] = Game.spritesheet.getSprite(160, 80 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			LeftEnemy[i] = Game.spritesheet.getSprite(144, 80 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			TopEnemy[i] = Game.spritesheet.getSprite(112, 80 + (World.TILE_SIZE * i), World.TILE_SIZE, World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			DownEnemy[i] = Game.spritesheet.getSprite(128, 80 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			RightEnemyDamege[i] = Game.spritesheet.getSprite(160, 176 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			LeftEnemyDamege[i] = Game.spritesheet.getSprite(144, 176 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			TopEnemyDamege[i] = Game.spritesheet.getSprite(112, 176 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

		for (int i = 0; i < 5; i++) {

			DownEnemyDamege[i] = Game.spritesheet.getSprite(128, 176 + (World.TILE_SIZE * i), World.TILE_SIZE,
					World.TILE_SIZE);

		}

	}

	// Logica do Inimigo
	public void tick() {

		// Caso nao exista coliz�o entre o player e o inimigo, o inimigo devera andar
		// at� o player
		if (!isColiddingWithPlayer()) {

			// Verifica a posi��o do jogador em rela��o ao inimigo para movimentalo,
			// verifica tambem se ele esta colidindo com algo, Verifica a distancia que o
			// inimigo esta do jogador
			if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
					&& !isColidding((int) (x + speed), this.getY())) {

				x += speed;
				last = 1;

				left = true;
				this.moved = true;

			}

			else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
					&& !isColidding((int) (x - speed), this.getY())) {

				x -= speed;
				last = 2;

				right = true;
				this.moved = true;

			}

			if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
					&& !isColidding(this.getX(), (int) (y + speed))) {

				y += speed;
				last = 3;

				up = true;
				this.moved = true;

			}

			else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
					&& !isColidding(this.getX(), (int) (y - speed))) {

				last = 4;
				y -= speed;

				setDown(true);
				this.moved = true;

			} else {

				switch (last) {

				case 1:

					left = true;

					break;

				case 2:

					right = true;

					break;

				case 3:

					up = true;

					break;

				case 4:

					setDown(true);

					break;

				}

			}

		} else {

			// Player colidindo com inimigo

			// Decrementa um valor aleatorio entre 0 e 3 da vida do jogador
			if (Game.player.sobrevida >= 1) {

				Game.player.sobrevida -= Game.rand.nextInt(MaxDamege);

				if (Game.player.sobrevida < 0) {

					Game.player.life += Game.player.sobrevida;
					Game.player.sobrevida = 0;

				}

			} else {

				Game.player.life -= Game.rand.nextInt(MaxDamege);

			}

			Game.player.isDamege = true;
			Sound.DamegePlayer.play();

		}

		// Verifica se o inimigo esta se movimentando, caso esteja o array sera
		// executado do 2 ao 5 caso nao do 0 ao 1

		if (moved) {

			// incrementa frames para ter controle do tempo de anima��o

			frames++;

			if (frames == maxFrames) {

				frames = 0;
				index++;

				// Retorna a anima��o para o 0
				if (index > maxIndex)
					index = 2;

			}

		} else {

			// Mesma coisa porem para o personagem parado
			frames++;

			if (frames == maxFrames) {

				frames = 0;
				index++;

				if (index > 1)
					index = 0;
			}
		}

		// System.out.println(index);
		// Destroi o inimigo com vida 0
		if (life <= 0) {

			destroySelf();
			return;

		}

		if (isDamaged) {

			this.damageCurrent++;

			if (this.damageCurrent == this.damageFrames) {

				this.damageCurrent = 0;
				this.isDamaged = false;

			}
		}

		collidionBullet();

		if (life <= 0) {

			destroySelf();

		}
	}

	public void collidionBullet() {

		for (int i = 0; i < Game.Bullets.size(); i++) {

			Entity e = Game.Bullets.get(i);

			if (e instanceof Bullet) {

				if (Entity.isColidding(this, e)) {

					Sound.DamegeEnemy.play();
					life--;

					Game.Bullets.remove(i);
					return;

				}
			}
		}
	}

	public void destroySelf() {

		Game.enemies.remove(this);
		Game.entities.remove(this);

	}

	public boolean isColiddingWithPlayer() {

		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);

	}

	public boolean isColidding(int xnext, int ynext) {

		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);

		for (int i = 0; i < Game.enemies.size(); i++) {

			Enemy e = Game.enemies.get(i);

			if (e == this)
				continue;

			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);

			if (enemyCurrent.intersects(targetEnemy)) {

				return true;

			}

		}

		return false;
	}

	public void render(Graphics g) {

		if (this.isDamaged) {

			if (right) {

				this.right = false;
				this.moved = false;

				g.drawImage(RightEnemyDamege[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			} else if (left) {

				this.left = false;
				this.moved = false;

				g.drawImage(LeftEnemyDamege[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			} else if (up) {

				this.up = false;
				this.moved = false;

				g.drawImage(TopEnemyDamege[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			} else {

				setDown(false);
				this.moved = false;

				g.drawImage(DownEnemyDamege[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			}
		} else {

			if (right) {

				this.right = false;
				this.moved = false;

				g.drawImage(RightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			} else if (left) {

				this.left = false;
				this.moved = false;

				g.drawImage(LeftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			} else if (up) {

				this.up = false;
				this.moved = false;

				g.drawImage(TopEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			} else {

				setDown(false);
				this.moved = false;

				g.drawImage(DownEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			}
		}
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isDamege() {
		return isDamege;
	}

	public void setDamege(boolean isDamege) {
		this.isDamege = isDamege;
	}
}
