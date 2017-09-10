package main;

import java.awt.event.KeyEvent;

public class ControllerInfo {
	
	private static boolean up;
	private static boolean down;
	private static boolean right;
	private static boolean left;
	private static boolean space;
	
	public static boolean getUp() { return up; }
	public static boolean getDown() { return down; }
	public static boolean getLeft() { return left; }
	public static boolean getRight() { return right; }
	public static boolean getSpace() { return space; }
	
	static void update(KeyEvent e, boolean isPressed) {
		char c = e.getKeyChar();
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP || c == 'w' || c == 'W')
			up = isPressed;
		else if(keyCode == KeyEvent.VK_DOWN || c == 's' || c == 'S')
			down = isPressed;
		else if(keyCode == KeyEvent.VK_LEFT || c == 'a' || 'c' == 'A')
			left = isPressed;
		else if(keyCode == KeyEvent.VK_RIGHT || c == 'd' || c == 'D')
			right = isPressed;
	}
}
