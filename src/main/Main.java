package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import io.Controller;
import io.Pointer;

public class Main {
	private static final int width = 900;
	private static final int height = 600;
		
	public static void main(String[] args) throws InterruptedException {
		//CREATE WINDOW
		JFrame window = new JFrame("ASTEROIDS!!!!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setResizable(false);
		window.setSize(width + window.getInsets().left + window.getInsets().right,
				height + window.getInsets().top + window.getInsets().bottom);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		//CREATE BUFFERED IMAGE && GRAPHICS VARIABLE
		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = frame.createGraphics();
		g.setBackground(Color.BLACK);
		
		//INIT GAME ENGINE AND LISTENERS
		Engine gameEngine = new Engine(width, height);
		window.add(gameEngine);
		addListeners(gameEngine, gameEngine);
		gameEngine.requestFocusInWindow();

		//Game Loop
		while(true) {
			window.repaint();
			Thread.sleep(4);
		}
	}
	
	/**
	 * ALL THE LISTENERS MODIFY
	 * PUBLIC-STATIC VARIABLES SO
	 * THEY CAN BE ASSESSED IN A
	 * TICK-BASED FASHION.
	 */
	private static void addListeners(JPanel panel, Engine gameEngine) {
		panel.addMouseListener(new MouseListener() {
			
			public void mouseExited(MouseEvent e) {
				Pointer.updateState(e, false);
			}
			public void mousePressed(MouseEvent e) {
				Pointer.updateState(e, true);
			}
			public void mouseReleased(MouseEvent e) {
				gameEngine.mouseReleased(e.getX(), e.getY());
				Pointer.updateState(e, false);
			}
			
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
		});
		
		panel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				Pointer.updateLocation(e);
			}
			
			public void mouseDragged(MouseEvent e) {}
			
		});
		
		panel.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				Controller.update(e, true);
			}
			public void keyReleased(KeyEvent e) {
				Controller.update(e, false);
			}

			public void keyTyped(KeyEvent e) {}
		});
	}
}
