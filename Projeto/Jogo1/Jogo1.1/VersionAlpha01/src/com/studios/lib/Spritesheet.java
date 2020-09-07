package com.studios.lib;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.studios.main.logSuport;

public class Spritesheet {
	private BufferedImage spritesheet;
	public int width;
	public int height;

	public Spritesheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
			width = spritesheet.getWidth();
			height = spritesheet.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
			new logSuport(0001, "Infelismente ocorreu um erro nos recursos do jogo, verifique a instalação:\n " + e,
					"0xFF000001", "Spritesheet");
			JOptionPane.showInternalMessageDialog(null,
					"Nao foi possivel carregar os Sprites, verifique os Logs em sua TEMP", "E R R O 0xFF000001",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public BufferedImage getSprite(int x, int y, int width, int height) {

		return spritesheet.getSubimage(x, y, width, height);

	}

}
