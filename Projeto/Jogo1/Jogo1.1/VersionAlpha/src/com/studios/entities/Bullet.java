package com.studios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.studios.lib.Entity;
import com.studios.wold.Camera;

public class Bullet extends Entity {

	private double dx;
	private double dy;
	private double spd = 6;

	public Bullet(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);

		this.dx = dx;
		this.dy = dy;

	}

	public void tick() {

		x += dx * spd;
		y += dy * spd;

	}

	public void render(Graphics g) {

		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);

	}
}
