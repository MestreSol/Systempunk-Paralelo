package br.com.studio.screen;

import br.com.studio.lib.Menu;
import br.com.studio.lib.Sound;

public class TitleMenu extends Menu {
	public int selected = 0;

	private static final String[] options = { "New Game", "Load Game", "Config", "About", "Exit" };

	public TitleMenu() {
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
			if (selected == 0) {
				Sound.main.play();
			}
		}
	}
}
