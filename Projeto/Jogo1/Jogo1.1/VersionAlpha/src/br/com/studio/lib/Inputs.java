package br.com.studio.lib;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Inputs implements KeyListener	 {
	public class Key{
		public int presses, absorbs;
		public boolean down, clicked;
		public Key() {
			keys.add(this);
		}
		public void toggle(boolean pressed) {
			if(pressed != down) {
				down = pressed;
			}
			if(pressed) {
				presses++;
			}
		}
		public void tick() {
			if(absorbs < presses) {
				absorbs++;
				clicked = true;
			}else {
				clicked = false;
			}
		}
	}
	public List<Key> keys = new ArrayList<Key>();
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
