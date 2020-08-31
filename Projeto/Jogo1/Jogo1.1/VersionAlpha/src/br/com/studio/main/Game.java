package br.com.studio.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import br.com.studio.lib.Sound;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public Thread thread;

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public Game() {
		setPreferredSize(new Dimension(GameConfig.WIDTH * GameConfig.SCALE, GameConfig.HEIGHT * GameConfig.SCALE));
		initFrame();

	}

	public void initFrame() {
		frame = new JFrame("V0.0");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		GameConfig.isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		GameConfig.isRunning = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void DefinirMusica() {
		if (GameConfig.GameState.equals("Menu")) {
			Sound.main.play();
		}
	}

	public void tick() {
		DefinirMusica();
	}

	public void render() {

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

		while (GameConfig.isRunning) {
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
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}
}
