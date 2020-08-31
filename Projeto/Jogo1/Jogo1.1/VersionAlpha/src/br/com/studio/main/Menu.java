package br.com.studio.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {
	public BufferedImage fundo;
	public String[] options = {"New Game", "Loading","The Project", "Exit"};
	public int currentOption = 0;
	public int maxOption = options.length-1;
	public boolean up = false;
	public boolean down = false;
	public GameConfig conf = new GameConfig();	
	public Menu() {
		try {
			fundo = ImageIO.read(getClass().getResource("/FundoMenu.jpg"));
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("E R R O: 0xFF000001");
		}
	}
	public void tick() {
		conf.isSaved();
		if(up) {
			currentOption--;
			up = false;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}else if(down) {
			currentOption++;
			down = false;
			if(currentOption < 0) {
				currentOption = maxOption;
			}
			if(currentOption >= 3) {
				currentOption = 0;
			}
		}
	}
	public void render() {}
	
}
