package com.studios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.studios.lib.Camera;
import com.studios.main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR = Game.tiles.getSprite(0, 0, World.TILE_SIZE, World.TILE_SIZE);
	public static BufferedImage TILE_WALL = Game.tiles.getSprite(0, 32, World.TILE_SIZE, World.TILE_SIZE);

	private BufferedImage sprite;

	private int x;
	private int y;

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

	public Tile(BufferedImage sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}
}
