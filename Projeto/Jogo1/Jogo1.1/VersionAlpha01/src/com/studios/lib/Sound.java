package com.studios.lib;

import java.applet.Applet;
import java.applet.AudioClip;

import javax.swing.JOptionPane;

import com.studios.main.logSuport;

public class Sound {
	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource("/Sounds/" + name));
		} catch (Throwable e) {
			e.printStackTrace();
			new logSuport(0001, "Infelismente ocorreu um erro nos recursos do jogo, verifique a instalação:\n " + e,
					"0xFF000002", "Sound");
			JOptionPane.showInternalMessageDialog(null,
					"Nao foi possivel carregar os arquivos de audio, verifique os Logs em sua TEMP",
					"E R R O 0xFF000002", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
			new logSuport(0001, "Infelismente ocorreu um erro nos recursos do jogo, verifique a instalação:\n " + e,
					"0xFF000003", "Sound");
			JOptionPane.showInternalMessageDialog(null,
					"Nao foi possivel carregar os arquivos de audio, verifique os Logs em sua TEMP",
					"E R R O 0xFF000003", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
			new logSuport(0001, "Infelismente ocorreu um erro nos recursos do jogo, verifique a instalação:\n " + e,
					"0xFF000004", "Sound");
			JOptionPane.showInternalMessageDialog(null,
					"Nao foi possivel carregar os arquivos de audio, verifique os Logs em sua TEMP",
					"E R R O 0xFF000004", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void stop() {
		try {
			new Thread() {
				public void run() {
					clip.stop();
				}
			}.start();
		}catch(Throwable e) {
			e.printStackTrace();
			new logSuport(0001, "Infelismente ocorreu um erro nos recursos do jogo, verifique a instalação:\n " + e,
					"0xFF000005", "Sound");
			JOptionPane.showInternalMessageDialog(null,
					"Nao foi possivel carregar os arquivos de audio, verifique os Logs em sua TEMP",
					"E R R O 0xFF000005", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setVolume(double vol) {
		try {
			new Thread() {
				public void run() {
					((Sound) clip).setVolume(vol);
				}
			}.start();
		}catch(Throwable e) {
			e.printStackTrace();
			new logSuport(0001, "Infelismente ocorreu um erro nos recursos do jogo, verifique a instalação:\n " + e,
					"0xFF000006", "Sound");
			JOptionPane.showInternalMessageDialog(null,
					"Nao foi possivel carregar os arquivos de audio, verifique os Logs em sua TEMP",
					"E R R O 0xFF000006", JOptionPane.ERROR_MESSAGE);
		}
	}
}
