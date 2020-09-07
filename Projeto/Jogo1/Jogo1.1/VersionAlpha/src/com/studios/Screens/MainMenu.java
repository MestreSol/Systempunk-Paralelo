package com.studios.Screens;

import java.awt.Graphics;

import com.studios.graphic.Screen;
import com.studios.lib.Color;
import com.studios.lib.Font;
import com.studios.main.Game;

public class MainMenu extends Menu {
	private int selected = 0;
	private static final String[] options = { "Continuar", "Novo Jogo", "Configurações", "Dicas", "Mais" };

	public MainMenu() {

	}

	public void tick() {
		if (input.up.clicked)
			selected--;
		if (input.down.clicked)
			selected++;

		int len = options.length;
		if (selected < 0)
			selected += len;
		if (selected >= len)
			selected -= len;

		if (input.attack.clicked || input.menu.clicked) {
			switch (selected) {
			case 0:
				break;
			case 1:
				Game.GameState = "NORMAL";
				Game.resetGame();

				break;
			}
		}
	}

	public void render(Graphics g) {
		
		int h = 2;
		int w = 13;
		int titleColor = Color.get(0, 010, 131, 551);
		int xo = (Game.screen.w - w * 8) / 2;
		int yo = 24;

		
		for (int i = 0; i < 5; i++) {
			String msg = options[i];
			int col = Color.get(0, 222, 222, 222);
			if (i == selected) {
				msg = "> " + msg + " <";
				col = Color.get(0, 555, 555, 555);
			}
			Font.draw(msg, Game.screen, (Game.screen.w - msg.length() * 8) / 2, (8 + i) * 8, col);
		}
		Font.draw("(Arrow keys,X and C)", Game.screen, 0, Game.screen.h - 8, Color.get(0, 111, 111, 111));
	}
}
