package main;

import java.awt.Graphics2D;

/**
 * Holds all objects in game,
 * Provides general update and draw functions
 * @author Nathan
 *
 */
public class Engine {
	
	final int width;
	final int height;
	
	private Asteroid[] asteroids;
	private RocketShip ship = null;
	
	Engine(int width, int height) {
		this.width = width;
		this.height = height;
		
		resetGame();
	}
	
	void resetGame() {
		asteroids = new Asteroid[5];
		for(int i = 0; i<asteroids.length; i++)
			asteroids[i] = new Asteroid(width, height);
	}
	
	void run(Graphics2D g) {
		for(Asteroid a : asteroids)
			a.updateA(asteroids);
				
		for(Asteroid a : asteroids) {
			a.updateB(width, height);
			a.draw(g);
		}
		
		if(ship != null) {
			ship.update(asteroids, width, height);
			ship.draw(g);
		}
	}
	
	
	void mouseReleased(int x, int y) {
		ship = new RocketShip(x, y);
	}
	
	
}
