package br.com.studio.level.tile;

import br.com.studio.entity.Entity;
import br.com.studio.level.Level;
import br.com.studio.lib.Screen;

public class Tile {

	public static int tickCount = 0;
	
	public static Tile tiles = new Tile[256];
	public static Tile grass = new GrassTile(0);
	public static Tile rock = new RockTile(1);
	public static Tile water = new WaterTile(2);
	public static Tile flower = new FlowerTile(3);
	public static Tile tree = new TreeTile(4);
	public static Tile dirt = new DirtTile(5);
	public static Tile sand = new SandTile(6);
	public static Tile cactus = new CactusTile(7);
	public static Tile hole = new HoleTile(8);
	public static Tile treeSapling = new SaplingTile(9, grass ,tree);
	public static Tile lava = new LavaTile(13);
	public final byte id;
	
	public boolean connectsToGrass;
	public boolean connectsToSand;
	public boolean connectsToLava;
	public boolean connectsToWater;
	
	public Tile(int id) {
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Tiles duplicados");
		tiles[id] = this;
	}
	public void render(Screen screen, Level level, int x, int y) {
		
	}
	public void mayPass(Level level, int x, int y, Entity e) {
		
	}
	public int getLightRadius(Level level, int x, int y) {
		return 0;
	}
	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir) {
		
	}
	public void bumpedInto(Level level, int xt, int yt, Entity entity) {
	}

	public void tick(Level level, int xt, int yt) {
	}

	public void steppedOn(Level level, int xt, int yt, Entity entity) {
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		return false;
	}

	public boolean use(Level level, int xt, int yt, Player player, int attackDir) {
		return false;
	}

	public boolean connectsToLiquid() {
		return connectsToWater || connectsToLava;
	}
}
