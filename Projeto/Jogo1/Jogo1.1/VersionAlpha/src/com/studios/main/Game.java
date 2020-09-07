package com.studios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.studios.Screens.MainMenu;
import com.studios.entities.Bullet;
import com.studios.entities.Player;
import com.studios.graphic.Menu;
import com.studios.graphic.Pause;
import com.studios.graphic.Screen;
import com.studios.graphic.UI;
import com.studios.lib.Enemy;
import com.studios.lib.Entity;
import com.studios.lib.Sound;
import com.studios.lib.Spritesheet;
import com.studios.wold.World;

public class Game extends Canvas implements Runnable, MouseListener, MouseMotionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 0l;

	private boolean isRunning = true;
	public InputStream stream = ClassLoader.getSystemResourceAsStream("fonts/pixelart.TTF");
	public Font newfont;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	public int mx, my;
	public static int zl = 1;
	public static int CUR_LEVEL = 1;
	public int[] pixels;
	public static MainMenu menuinicial;
	/*
	 * public BufferedImage lightMap; public int[] lightMapPixels;
	 */
	public static BufferedImage image;

	private Thread thread;
	private UI ui;

	public static List<Entity> entities;
	public static List<Bullet> Bullets;
	public static List<Enemy> enemies;

	public static JFrame frame;

	public static World world;

	public static Player player;

	public Menu menu;
	public static String GameState = "MENUINICIAL";
	public static String map;
	public static String MapTipe = "DEFAULT";

	public static Spritesheet spritesheet;
	public static Spritesheet uiSprite;
	public static Spritesheet uiDamege;

	public static Random rand;

	public boolean SaveGame = false;
	public BufferedImage f;
	public static double mouseAngle = 0;
	public static Screen screen;
	public int[] colors = new int[256];
	public void StanceValues() {
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(5f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		DefinirMusica();
		newmap();

		rand = new Random();
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		/*
		 * try { lightMap = ImageIO.read(getClass().getResource("/lightmap.png")); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } lightMapPixels = new
		 * int[lightMap.getWidth()*lightMap.getHeight()];
		 * lightMap.getRGB(0,0,WIDTH,HEIGHT,lightMapPixels,0,lightMap.getWidth());
		 */
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spritesheet = new Spritesheet("/spritesheet.png");
		uiSprite = new Spritesheet("/UI.png");
		uiDamege = new Spritesheet("/UIDamege.png");
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(33, 1, 16, 16));
		Bullets = new ArrayList<Bullet>();
		entities.add(player);
		world = new World("/" + map);

		menu = new Menu();
	}

	public Game() {

		StanceValues();
	
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		initFrame();

	}

	public void initFrame() {

		frame = new JFrame("Stalin and a Communist's Journey");

		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		int pp = 0;
		
		for(int r = 0; r<6; r++) {
			for(int g = 0; g<6; g++) {
				for(int b = 0; b<6;b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

					int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
					int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
					int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;

					colors[pp++] = r1 << 16 | g1 << 8 | b1;

				}
			}
		}
		
			screen = new Screen(WIDTH,HEIGHT);
	}

	public synchronized void start() {

		thread = new Thread(this);
		isRunning = true;
		thread.start();

	}

	public synchronized void stop() {

		isRunning = false;

		try {

			thread.join();

		} catch (InterruptedException e) {

			e.printStackTrace();

		}
	}

	public static void newmap() {

		map = "mapa" + CUR_LEVEL + ".png";
	}

	public static void resetGame() {
		newmap();

		Game.image = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.uiSprite = new Spritesheet("/UI.png");
		Game.uiDamege = new Spritesheet("/UIDamege.png");
		Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(33, 1, 16, 16));

		Game.entities.add(Game.player);
		Game.world = new World("/" + Game.map);
		Game.player.life = 100;
		Game.player.sobrevida = 0;

	}

	public static void DefinirMusica() {
		if (GameState == "MENUINICIAL") {

			Sound.musicBackground.loop();
			Sound.musicGameBackground.stop();
			System.out.println("JAAJ");

		} else if (GameState == "NORMAL") {
			Sound.musicGameBackground.loop();
			Sound.musicBackground.stop();
			System.out.println("JeeJ");

		}
	}

	public void tick() {

		if (this.SaveGame) {

			String[] opt1 = { "level", "bullet", "reserva" };
			int[] opt2 = { CUR_LEVEL, player.getAmmo(), player.getReservaMuni() };

			Menu.saveGame(opt1, opt2, 42);
			System.out.println("Save game");
		}

		if (GameState.equals("NORMAL")) {

			for (int i = 0; i < entities.size(); i++) {

				Entity e = entities.get(i);

				newmap();

				e.tick();
			}
			for (int i = 0; i < Bullets.size(); i++) {
				Bullets.get(i).tick();
			}

		} else if (GameState.equals("GAME_OVER")) {

		} else if (GameState.equals("MENUINCIAL")) {
			menuinicial.tick();
		} else if (GameState.equals("PAUSE")) {
			Pause.tick();
		}
	}

	public void render() {

		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {

			this.createBufferStrategy(3);
			return;

		}

		Graphics g = image.getGraphics();
		//Graphics gp = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		world.render(g);
		for (int i = 0; i < Bullets.size(); i++) {
			Bullets.get(i).render(g);
		}

		for (int i = 0; i < entities.size(); i++) {

			Entity e = entities.get(i);

			e.render(g);

		}
		ui.render(g);
		g.setFont(newfont);
		if (player.getAmmo() == 0) {
			g.setColor(new Color(255, 0, 0));
		} else {
			g.setColor(new Color(0, 255, 0));
		}
		g.drawString("" + player.getAmmo(), 45, 153);
		if (player.getReservaMuni() == 0) {
			g.setColor(new Color(255, 0, 0));
		} else {
			g.setColor(new Color(0, 255, 0));
		}
		g.drawString("Level: " + zl, 100, 10);
		g.drawString("" + player.getReservaMuni(), 30, 153);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		if (Game.GameState.equals("GAME_OVER")) {

			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(new Color(0, 0, 0, 100));
			g2d.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("GAME OVER", ((WIDTH * SCALE)) / 2 - 50, ((HEIGHT * SCALE)) / 2 - 20);
			g.drawString("> pressione enter para continuar <", ((WIDTH * SCALE)) / 2 - 200,
					((HEIGHT * SCALE)) / 2 + 40);
			
		} else if (Game.GameState.equals("MENUINICIAL")) {
			menuinicial.render(g);
		}
		if (Game.GameState == "PAUSE") {
			Pause.render(g);
		}
		if (SaveGame) {
			int frame = 0;
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 28));
			try {
				f = ImageIO.read(getClass().getResource("/save.png"));
				g.drawImage(f, 300, 100, 100 * 3, 100 * 3, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (SaveGame) {
				frame++;

				if (frame == 7) {

					frame = 0;
					SaveGame = false;

				}
			}

		}
		Graphics2D g2d = (Graphics2D) g;
		mouseAngle = Math.atan2(my - 200 + 25, mx - 200 + 25);

		g2d.rotate(0, 200 + 25, 200 + 25);

		// g.setColor(Color.RED);
		// g.fillRect(200, 200, 50, 50);

		bs.show();

	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();

		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();

		int frames = 0;

		requestFocus();
		while (isRunning) {

			long now = System.nanoTime();

			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {

				tick();
				render();

				frames++;
				delta--;

			}

			if (System.currentTimeMillis() - timer >= 1000) {

				System.out.println("FPS: " + frames);
				System.out.println("Vida: " + player.life);
				System.out.println("Sobre Vida: " + player.sobrevida);
				System.out.println("GameState: " + GameState);
				System.out.println("Angulo Mouse: " + mouseAngle);
				frames = 0;
				timer += 1000;

			}
		}

		stop();

	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();

	}

	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.setMx((e.getX() / SCALE));
		player.setMy((e.getY() / SCALE));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mx = e.getX();
		this.my = e.getY();
	}

}