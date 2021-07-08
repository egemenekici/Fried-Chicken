package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	public boolean up;
	public static boolean pressed = false;
	static int direction = 0;


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			pressed = true;
			direction = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			pressed = true;
			direction = -1;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			pressed = true;
		}
	}


	public void keyReleased(KeyEvent e) {
		pressed = false;
	}


	public void keyTyped(KeyEvent arg0) {
	}

}
