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

public class Main {
	private static final int width = 900;
	private static final int height = 600;
		
	public static void main(String[] args) {
		JFrame window = new JFrame("ASTEROIDS!!!!");
				
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.setSize(width + window.getInsets().left + window.getInsets().right,
				height + window.getInsets().top + window.getInsets().bottom);
		window.setLocationRelativeTo(null);


		
		
		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = frame.createGraphics();
		g.setBackground(Color.BLACK);
		
		Engine gameEngine = new Engine(width, height);
		
		addListeners(window, gameEngine);
		
		while(true) {
			gameEngine.run(g);
			refresh(window, frame, g);
		}
	}
	
	private static void refresh(JFrame window, BufferedImage frame, Graphics2D g) {
		window.getGraphics().drawImage(frame, window.getInsets().left, window.getInsets().top, null);
		g.clearRect(0, 0, width, height);
	}
	
	private static void addListeners(JFrame window, Engine gameEngine) {
		window.addMouseListener(new MouseListener() {
			
			public void mouseExited(MouseEvent arg0) {
				PointerInfo.mousePressed = false;
			}

			public void mousePressed(MouseEvent arg0) {
				PointerInfo.mousePressed = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				gameEngine.mouseReleased(e.getX(), e.getY());
				PointerInfo.mousePressed = false;
			}
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
		});
		
		window.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				PointerInfo.x = e.getX();
				PointerInfo.y = e.getY();
			}
			
			public void mouseDragged(MouseEvent arg0) {}
			
		});
		
		window.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				ControllerInfo.update(e, true);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				ControllerInfo.update(e, false);
			}

			@Override public void keyTyped(KeyEvent arg0) {}
		});
	}
}
