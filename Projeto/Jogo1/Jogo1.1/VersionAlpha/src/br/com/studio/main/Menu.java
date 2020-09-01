package br.com.studio.main;

import java.util.List;

import br.com.studio.lib.Color;
import br.com.studio.lib.Font;
import br.com.studio.lib.Inputs;
import br.com.studio.lib.Screen;
import br.com.studio.screen.ListItem;

public class Menu {
	protected Game game;
	protected Inputs input;

	public void init(Game game, Inputs input) {
		this.input = input;
		this.game = game;
	}

	public void tick() {
	}

	public void render(Screen screen) {
	}

	public void renderItemList(Screen screen, int xo, int yo, int x1, int y1, List<? extends ListItem> listItems,
			int selected) {
		boolean renderCursos = true;
		if (selected < 0) {
			selected = -selected - 1;
			renderCursos = false;
		}
		int w = x1 - xo;
		int h = y1 - yo - 1;
		int i0 = 0;
		int i1 = listItems.size();
		if (i1 > h)
			i1 = h;
		int io = selected - h / 2;
		if(io > listItems.size() - h) io = listItems.size() - h;
		if(io < 0) io = 0;
		for(int i = i0; i<i1;i++) {
			listItems.get(i +io).renderInventory(screen, (1+xo)*8,(i+1+yo)*8);
		}
		if(renderCursos) {
			int yy = selected + 1 - io + yo;
			Font.draw(">", screen, (xo + 0) * 8, yy * 8, Color.get(5, 555, 555, 555));
			Font.draw("<", screen, (xo + w) * 8, yy * 8, Color.get(5, 555, 555, 555));
		}
	}

}
