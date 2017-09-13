package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Random;

import io.Controller;

public class RocketShip {
	
	private static final double thrust = 0.045;
	private static final double turn = 0.045;
	
	private int hWidth = 6;
	private int hHeight = 10;
	
	private double x;
	private double y;
	
	private double vx;
	private double vy;
	
	private double heading;
	
	private boolean isDead;
	
	private int health;
	
	/**
	 * The thing you control.
	 * @param x,y The desired region to spawn.
	 */
	RocketShip(int x, int y) {
		this.x = x;
		this.y = y;
		
		vx = 0;
		vy = 0;
		heading = 0;
		
		health = 4;
	}
	
	void update(List<Asteroid> asteroids, int winWidth, int winHeight) {
		Rectangle bounds = getBounds();
		//dies when hits an asteroid.
		for(int i = asteroids.size()-1; i>=0; i--)
			if(bounds.intersects(asteroids.get(i).getBounds())) {
				if(health == 1) {
					isDead = true;
					return;
				}
				else {
					health--;
					int r = (int)(Math.random()+0.5);
					vx = -vx*(r+0.5);
					vy = -vy*(r+0.5);
					
					if(asteroids.get(i).getRadius()>17)
						for(int j = 0; j<3; j++)
							asteroids.add(new Asteroid((int)x, (int)y, 0.3));
					else
						asteroids.get(i).explode();
					
						
					asteroids.remove(i);
				}
			
			}
		
		//turns
		if(Controller.getLeft())
			heading -= turn;
		else if(Controller.getRight())
			heading += turn;
		
		//accelerates
		if(Controller.getUp()) {
			//remember, the triangle itself is rotated +90 degrees
			vx += -Math.sin(-heading)*thrust;
			vy += -Math.cos(-heading)*thrust;
		}
		
		//moves
		this.x += vx;
		this.y += vy;
		
		//wraps around screen
		if(x<0)
			x = winWidth;
		else if(x>winWidth)
			x = 0;
		
		if(y<0)
			y = winHeight;
		else if(y>winHeight)
			y = 0;
	}
	
	Rectangle getBounds() {
		return new Rectangle(
				(int)x-hWidth, 
				(int)y-hHeight, 
				hWidth*2, 
				hHeight*2
		);
	}
	
	void draw(Graphics2D g) {
		AffineTransform reset = g.getTransform();
		
		//rotate + translate
		g.translate((int)x, (int)y - (int)(hHeight/2.5));
		g.rotate(heading);
		g.translate(0, (int)(hHeight/2.5));
		
		//g.translate(-hWidth, -2*ahHeight);
		
		//body
		g.setColor(getColor());
		g.fillRect(-hWidth, -hHeight, 2*hWidth, 2*hHeight);
		
		//head
		g.setColor(getColor().darker().darker());
		g.fillPolygon(new int[] {-hWidth, 0, hWidth}, 
				new int[] {-hHeight, (int)(-hHeight*2.5), -hHeight}, 
				3);
		
		if(Controller.getUp()) {
			g.setColor(new Color(200, 60, 0));
			g.fillPolygon(new int[]{-hWidth, 0, hWidth}, 
					new int[]{hHeight, (int)( hHeight*(2.1+Math.random()*0.66) ), hHeight}, 
					3);
		}
		
		g.setTransform(reset);
	}
	
	private Color getColor() {
		switch(health) {
			case 4:
				return new Color(250,250,250);
			case 3:
				return new Color(210, 210, 210);
			case 2:
				return new Color(150, 100, 100);
			case 1:
				return new Color(70, 0, 0);
			case 0:
				return new Color(0, 0, 0);
			default:
				throw new InternalError("WHY THE HELL DO YOU HAVE "+health+" HEALTH??");
		}
	}
	
	double distSQ(double x, double y) {
		return (this.x-x)*(this.x-x) + (this.y-y)*(this.y-y);
	}
}
