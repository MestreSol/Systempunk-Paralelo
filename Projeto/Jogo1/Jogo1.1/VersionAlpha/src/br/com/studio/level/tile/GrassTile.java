package br.com.studio.level.tile;

import br.com.studio.level.Level;
import br.com.studio.lib.Color;
import br.com.studio.lib.Screen;

public class GrassTile extends Tile {

	public GrassTile(int id) {
	super(id);
	connectsToGrass = true;
	}
	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(level.grassColor, level.grassColor,level.grassColor+111, level.grassColor +111);
		int transitionColor = Color.get(level.grassColor - 111, level.grassColor, level.grassColor + 111, level.dirtColor);
		boolean u = !level.getTile(x,y-1).connectsToGrass;
	}
}
