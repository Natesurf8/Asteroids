package io;

import java.awt.event.MouseEvent;

/**
 * Better than a 
 * new Point p = SwingUtilities.convertPointFromScreen(MouseInfo.getPointerInfo().getLocation(),window);
 * for every update for (if you dont want to store that point here) every element. Besides,
 * i'll need to store mousePressed here anyways.
 * @author Nathan
 * 
 */
public class Pointer {
	private static boolean mousePressed;
	private static int x;
	private static int y;
	
	public static boolean isPressed() { return mousePressed;}
	public static int x() { return x; }
	public static int y() { return y; }
	
	public static void updateState(MouseEvent e, boolean isPressed) {
		mousePressed = isPressed;
	}
	public static void updateLocation(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
}
