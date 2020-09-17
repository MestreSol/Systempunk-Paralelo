package com.studios.lib;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.studios.main.Game;

public class Menu {
	protected Game game;
	protected Inputs input;

	public void init(Game game, Inputs input) {
		this.input = input;
		this.game = game;
	}

	public void tick() {

	}

	public void render() {

	}

	public void renderItemList(Graphics g, int xo, int yo, int x1, int y1, List<? extends ListItem> listItems,
			int selected) {
		boolean renderCursor = true;
		if (selected < 0) {
			selected = -selected - 1;
			renderCursor = false;
		}
		int w = x1 - xo;
		int h = y1 - yo - 1;
		int i0 = 0;
		int i1 = listItems.size();
		if (i1 > h)
			i1 = h;
		int io = selected - h / 2;
		if (io > listItems.size() - h)
			io = listItems.size() - h;
		if (io < 0)
			io = 0;
		for (int i = i0; i < i1; i++) {
			listItems.get(i + io).renderInventory(g, (1 + xo) * 8, (i + 1 + yo) * 8);
		}
		if (renderCursor) {
			int yy = selected + 1 - io + yo;
			g.setColor(new Color(255,255,255));
			g.drawString(">", (xo+0)*8, yy*8);
			g.drawString("<", (xo+w)*8, yy*8);
		}
	}
}
