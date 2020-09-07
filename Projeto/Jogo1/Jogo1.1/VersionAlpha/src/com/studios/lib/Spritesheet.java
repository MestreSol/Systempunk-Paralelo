package com.studios.lib;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	public int width, height;
	public int[] pixels;
	public Spritesheet(String path) {
	
		try {
		
			spritesheet = ImageIO.read(getClass().getResource(path));
			width = spritesheet.getWidth();
			height = spritesheet.getHeight();

			
		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
	
		return spritesheet.getSubimage(x, y, width, height);
	
	}
	
}