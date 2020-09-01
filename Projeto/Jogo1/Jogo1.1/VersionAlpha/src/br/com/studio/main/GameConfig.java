package br.com.studio.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameConfig {
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	public static boolean isRunning = false;
	public static String GameState = "Menu";
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	public static int CUR_LEVEL = 0;
	public static Random random = new Random();
	public GameConfig() {
		isSaved();
	}

	public void isSaved() {
		File file = new File("save.TheStudio");
		if (file.exists()) {
			saveExists = true;

		} else {
			saveExists = false;

		}
	}

	public static void saveGame(String[] val1, int[] val2, int code) {

		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.TheStudio"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("E R R O: 0xFF000002");
		}
		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for (int in = 0; in < value.length; in++) {
				value[in] += code;
				current += value[in];
			}
			try {
				write.write(current);
				if (i < val1.length - 1) {
					write.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("E R R O: 0xFF000003");
			}
		}
		try {
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("E R R O: 0xFF000004");
		}
	}

	public static String loadGame(int code) {
		String line = "";
		File file = new File("save.TheStudio");

		if (saveExists) {
			String singleLine = null;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				try {
					while ((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for (int i = 0; i < val.length; i++) {
							val[i] -= code;
							trans[1] += val[i];

						}
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}

				} catch (IOException e) {

				}

			} catch (IOException e) {
			}
		} else {
			return line;
		}
		return line;
	}
	public static void applySave(String str) {
		String[]spl = str.split("/");
		for(int i = 0; i< spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
			case "level":
				CUR_LEVEL = Integer.parseInt(spl2[1]);
				System.out.println("Level:"+CUR_LEVEL);
				}
		}
	}
}
