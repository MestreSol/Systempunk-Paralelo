package br.com.studio.level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.studio.entity.Entity;
import br.com.studio.level.tile.Tile;
import br.com.studio.levelgen.LevelGen;
import br.com.studio.lib.Screen;
import br.com.studio.main.GameConfig;

public class Level {
	public int w, h;
	public byte[] tiles;
	public byte[] date;
	public List<Entity>[] entitiesInTiles;

	public int grassColor = 141;
	public int dirtColor = 322;
	public int sandColor = 550;
	private int depth;
	public int monsterDensity = 8;

	public List<Entity> entities = new ArrayList<Entity>();
	
	private Comparator<Entity> spriteSorter = new Comparator<Entity>() {
		public int compare(Entity e0, Entity e1) {	
			if(e1.y < e0.y) return +1;
			if(e1.y > e0.y) return -1;
			return 0;
		}
	};

	@SuppressWarnings("unchecked")
	public Level(int w, int h, Level parentLevel) {
		
		this.depth = GameConfig.CUR_LEVEL;
		this.w = w;
		this.h = h;
		byte[][] maps;
		if(GameConfig.CUR_LEVEL == 0){
			maps = LevelGen.createAndValidateTopMap(w, h);
		}
		else if(GameConfig.CUR_LEVEL < 0) {
			dirtColor = 222;
			maps = LevelGen.createAndValidateUndergroundMap(w, h, -level);
			monsterDensity = 4;
		}else {
			maps = LevelGen.createAndValidateSkyMap(w, h); // Sky level
			monsterDensity = 4;
		}
		tiles = maps[0];
		data = maps[1];
		
		if(parentLevel != null) {
			for (int y = 0; y < h; y++)
				for (int x = 0; x < w; x++) {
					if (parentLevel.getTile(x, y) == Tile.stairsDown) {

						setTile(x, y, Tile.stairsUp, 0);
						if (GameConfig.CUR_LEVEL == 0) {
							setTile(x - 1, y, Tile.dirt, 0);
							setTile(x + 1, y, Tile.dirt, 0);
							setTile(x, y - 1, Tile.dirt, 0);
							setTile(x, y + 1, Tile.dirt, 0);
							setTile(x - 1, y - 1, Tile.dirt, 0);
							setTile(x - 1, y + 1, Tile.dirt, 0);
							setTile(x + 1, y - 1, Tile.dirt, 0);
							setTile(x + 1, y + 1, Tile.dirt, 0);
						} else {
							setTile(x - 1, y, Tile.dirt, 0);
							setTile(x + 1, y, Tile.dirt, 0);
							setTile(x, y - 1, Tile.dirt, 0);
							setTile(x, y + 1, Tile.dirt, 0);
							setTile(x - 1, y - 1, Tile.dirt, 0);
							setTile(x - 1, y + 1, Tile.dirt, 0);
							setTile(x + 1, y - 1, Tile.dirt, 0);
							setTile(x + 1, y + 1, Tile.dirt, 0);
						}
					}

				}
		}
		entitiesInTiles = new ArrayList[w*h];
		for(int i = 0; i<w*h; i++) {
			entitiesInTiles[i] = new ArrayList<Entity>();
			
		}
		if(level == 1) {
			AirWizard aw = new AirWizard();
			aw.x = w*8;
			aw.y = h*8;
			add(aw);
		}
		}
		public void renderBackground(Screen screen, int xScroll, int yScroll) {
			int xo = xScroll >> 4;
			int yo = yScroll >> 4;
			int w = (screen.w+15)>>4;
			int h = (screen.h+15)>>4;
			
			screen.setOffSet(xScroll, yScroll);
			for(int y = yo; y<=h+yo;y++) {
				for(int x = xo; x<=w+xo;x++) {
					getTile(x,y).render(screen, this, x,y);
				}
			}
			screen.setOffSet(0, 0);
		}
		private List<Entity> rowSprites = new ArrayList<Entity>();
		public Player player;
		
		public void renderSprites(Screen screen, int xScroll, int yScroll) {
			int xo = xScroll>>4;
			int yo = yScroll>>4;
			int w = (screen.w+15) >> 4;
			int h = (screen.h+15)>>4;
			
			screen.setOffSet(xScroll, yScroll);
			for(int y = yo; y<=h+yo;y++) {
				for(int x = xo; x<=w+xo;x++) {
					if(x<0||y<0||x>=this.w||y>=this.h) continue;
					rowSprites.addAll(entitiesInTiles[x+y*this.w]);
				}
				if(rowSprites.size()>0) {
					sortAndRender(screen,rowSprites);
				}
				rowSprites.clear();
			}
			screen.setOffSet(0, 0);
			
		}
		
}
	

