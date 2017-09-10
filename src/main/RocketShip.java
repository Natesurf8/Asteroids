package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class RocketShip {
	
	private static final double thrust = 0.05;
	private static final double turn = 0.045;
	
	private int hWidth = 7;
	private int hHeight = 10;
	
	private double x;
	private double y;
	
	private double vx;
	private double vy;
	
	private double heading;
	
	private boolean accelerating;
	private boolean isDead;
	
	private int health;
		
	RocketShip(int x, int y) {
		Random r = new Random();
		this.x = x + ( (Math.random()-0.5)*2 + (r.nextInt(2)-0.5) ) * 50;
		this.y = x + ( (Math.random()-0.5)*2 + (r.nextInt(2)-0.5) ) * 50;
		
		vx = 0;
		vy = 0;
		heading = 0;
		
		health = 3;
	}
	
	void update(Asteroid[] asteroids, int winWidth, int winHeight) {
		Rectangle bounds = getBounds();
		for(Asteroid a : asteroids)
			if(bounds.intersects(a.getBounds())) {
				isDead = true;
				return;
			}
		
		if(ControllerInfo.getLeft())
			heading -= turn;
		else if(ControllerInfo.getRight())
			heading += turn;
		
		if(ControllerInfo.getUp()) {
			//remember, the triangle itself is rotated 90 degrees
			vx += -Math.sin(-heading)*thrust;
			vy += -Math.cos(-heading)*thrust;
		}
		
		this.x += vx;
		this.y += vy;

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
				(int)(x-hWidth+0.5), 
				(int)(y-hHeight+0.5), 
				hWidth*2, 
				hHeight*2
		);
	}
	
	void draw(Graphics2D g) {
		AffineTransform reset = g.getTransform();
				
		g.translate((int)x, (int)y);
		g.rotate(heading);
		
		
		g.setColor(getColor());
		g.fillRect(-hWidth+1, -hHeight, 2*hWidth-1, 2*hHeight);
		
		g.setColor(getColor().darker().darker());
		g.fillPolygon(new int[] {-hWidth, 0, hWidth}, 
				new int[] {-hHeight, (int)(-hHeight*2.5), -hHeight}, 
				3);
		
		if(ControllerInfo.getUp()) {
			g.setColor(new Color(200, 60, 0));
			g.fillPolygon(new int[]{-hWidth+1, 0, hWidth}, 
					new int[]{hHeight, (int)( hHeight*(2.1+Math.random()/1.5) ), hHeight}, 
					3);
		}
		
		g.setTransform(reset);
	}
	
	private Color getColor() {
		switch(health) {
			case 3:
				return new Color(200,200,200);
			case 2:
				return new Color(150, 50, 0);
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
