package br.com.studio.lib;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public static Sound main = new Sound("MainMusic.wav");

	private AudioClip clip;

	private Sound(String name) {

		try {

			clip = Applet.newAudioClip(Sound.class.getResource("/Sounds/" + name));

		} catch (Throwable e) {

			e.printStackTrace();

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

		}
	}

	public void stop() {

		try {

			new Thread() {

				public void run() {

					clip.stop();

				}
			}.start();

		} catch (Throwable e) {

			e.printStackTrace();

		}
	}
}
