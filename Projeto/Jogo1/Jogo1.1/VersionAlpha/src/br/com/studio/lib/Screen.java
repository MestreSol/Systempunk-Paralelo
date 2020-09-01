package br.com.studio.lib;

public class Screen {
	/*
	 * public static final int MAP_WIDTH = 64; public int[] tiles = new
	 * int[MAP_WIDTH * MAP_WIDTH]; public int[] colors = new int[MAP_WIDTH *
	 * MAP_WIDTH]; public int[] databits = new int[MAP_WIDTH * MAP_WIDTH];
	 */
	public int xOffSet;
	public int yOffSet;

	public static final int BIT_MIRROR_X = 0x01;
	public static final int BIT_MIRROR_Y = 0x02;

	public final int w, h;
	public int[] pixels;

	private SpriteSheet sheet;

	public Screen(int w, int h, SpriteSheet sheet) {
		this.sheet = sheet;
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
		/*
		 * for(int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++) { colors[i] =
		 * Color.get(00,40,50,40); tiles[i] = 0; if(GameConfig.random.nextInt(40) == 0)
		 * { tiles[i] = 32; colors[i] = Color.get(111, 40, 222, 333); databits[i] =
		 * GameConfig.random.nextInt(2); }else if(GameConfig.random.nextInt(40) == 0) {
		 * tiles[i] = 33; colors[i] = Color.get(20,40,30,550); }else { tiles[i] =
		 * GameConfig.random.nextInt(4); databits[i] = GameConfig.random.nextInt(4); } }
		 */

	}

	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void render(int xp, int yp, int tile, int colors, int bits) {
		xp -= xOffSet;
		yp -= yOffSet;

		boolean mirrorX = (bits & BIT_MIRROR_X) > 0;
		boolean mirrorY = (bits & BIT_MIRROR_Y) > 0;

		int xTile = tile % 32;
		int yTile = tile / 32;
		int toffs = xTile * 8 + yTile * 8 * sheet.width;

		for (int y = 0; y < 8; y++) {
			int ys = y;
			if (mirrorY)
				ys = 7 - y;
			if (y + yp < 0 || y + yp >= h)
				continue;
			for (int x = 0; x < 8; x++) {
				if (x + xp < 0 || x + xp >= w)
					continue;

				int xs = x;
				if (mirrorX)
					xs = 7 - x;
				int col = (colors >> (sheet.pixels[xs + ys * sheet.width + toffs] * 8)) & 255;
				if (col < 255)
					pixels[(x + xp) + (y + yp) * w] = col;
			}
		}
	}

	public void setOffSet(int xOffSet, int yOffSet) {
		this.xOffSet = xOffSet;
		this.yOffSet = yOffSet;
	}

	public int[] dither = new int[] { 0, 8, 2, 10, 12, 4, 14, 6, 3, 11, 1, 9, 15, 7, 13, 5, };

	public void overlay(Screen screen2, int xa, int ya) {
		int[] oPixels = screen2.pixels;
		int i = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (oPixels[i] / 10 <= dither[((x + xa) & 3) + ((y + ya) & 3) * 4])
					pixels[i] = 0;
				i++;
			}
		}
	}

	public void renderLigth(int x, int y, int r) {
		x -= xOffSet;
		y -= yOffSet;
		int x0 = x - r;
		int x1 = x + r;
		int y0 = y - r;
		int y1 = y + r;

		if (x0 < 0)
			x0 = 0;
		if (y0 < 0)
			y0 = 0;
		if (x1 > w)
			x1 = w;
		if (y1 > h)
			y1 = h;

		for (int yy = y0; yy < y1; yy++) {
			int yd = yy - y;
			yd = yd * yd;
			for (int xx = x0; xx < x1; xx++) {
				int xd = xx - x;
				int dist = xd * xd + yd;
				if (dist <= r * r) {
					int br = 255 - dist * 255 / (r * r);
					if (pixels[xx + yy * w] < br)
						pixels[xx + yy * w] = br;
				}
			}
		}
	}
}
