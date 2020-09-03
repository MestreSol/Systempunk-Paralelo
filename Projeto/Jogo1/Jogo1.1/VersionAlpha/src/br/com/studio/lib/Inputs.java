package br.com.studio.lib;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import br.com.studio.main.Game;

public class Inputs implements KeyListener {

	// cria uma classe local para evitar erros
	public class Key {

		// presses retorna a quantidade de keys apertadas, absorbs retorna o valor geral
		// de keys precionadas em x frames
		public int presses, absorbs;

		// down retorna se a key esta precionada e clicked o mesmo porem relacionado ao
		// mouse
		public boolean down, clicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}

	// cria uma lista de keys para uso futuro
	public List<Key> keys = new ArrayList<Key>();
	// Declaração das keys
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key menu = new Key();

	// release em todas as keys declaradas acima
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}

	// tick global das keys
	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}

	public Inputs(Game game) {
		game.addKeyListener(this);
	}

	// metodos padroes do KeyListener
	@Override
	public void keyPressed(KeyEvent arg0) {
		toggle(arg0, true);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		toggle(arg0, false);
	}

	// valida de acordo o Override das keys e valida se estao ou nao sendo
	// precionadas\
	private void toggle(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8)
			up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2)
			down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4)
			left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6)
			right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_W)
			up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S)
			down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_A)
			left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D)
			right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_UP)
			up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_DOWN)
			down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT)
			left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
			right.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_TAB)
			menu.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ALT)
			menu.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ALT_GRAPH)
			menu.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_SPACE)
			attack.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_CONTROL)
			attack.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD0)
			attack.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_INSERT)
			attack.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_ENTER)
			menu.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_X)
			menu.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_C)
			attack.toggle(pressed);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
