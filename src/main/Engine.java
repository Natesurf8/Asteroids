package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Holds all objects in game,
 * Provides general update and draw functions
 * @author Nathan
 *
 */
public class Engine extends JPanel{
	private static final long serialVersionUID = 1L;
	
	//final int width;
	//final int height;
	
	private List<Asteroid> asteroids;
	private RocketShip ship = null;
	
	Engine(int width, int height) {
		this.setFocusable(true);
		this.setBounds(0, 0, width, height);
		this.setVisible(true);
		this.requestFocus();
		//this.width = width;
		//this.height = height;
		
		resetGame();
	}
	
	void resetGame() {
		asteroids = new ArrayList<Asteroid>(5);
		for(int i = 0; i<5; i++)
			asteroids.add(new Asteroid(getWidth(), getHeight()));
		
		ship = null;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Asteroid a : asteroids)
			a.updateA(asteroids);
				
		for(Asteroid a : asteroids) {
			a.updateB(getWidth(), getHeight());
			a.draw((Graphics2D)g);
		}
		
		if(ship != null) {
			ship.update(asteroids, getWidth(), getHeight());
			ship.draw((Graphics2D)g);
		}
	}
	
	
	void mouseReleased(int x, int y) {
		ship = new RocketShip(x, y);
	}
}
