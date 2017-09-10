package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Asteroid {
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double ndx;
	private double ndy;
	private double radius;
		
	private Rectangle bounds;
	
	public Asteroid(int winWidth, int winHeight) {
		radius = Math.random()*30 + 30;
		
		x = radius + Math.random()*(winWidth-radius*2);
		y = radius + Math.random()*(winHeight-radius*2);
		
		dx = 1-Math.pow(Math.random(), 4);
		dy = 1-Math.pow(Math.random(), 3);
		
		bounds = new Rectangle((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2));
	}
	
	void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	void updateA(Asteroid[] asteroids) {
		ndx = dx;
		ndy = dy;
		
		Asteroid closest = null;
		double closestDist = Double.POSITIVE_INFINITY;
		
		for(Asteroid a : asteroids) {
			if(a != this && a.getBounds().intersects(this.getBounds())) {

				if(distSQ(a) < closestDist) {
					closestDist = distSQ(a);
					closest = a;
				}
			}
		}
		
		if(closest != null) {
			double distX = this.x-closest.x;
			double distY = this.y-closest.y;
			
			ndx = Math.abs(closest.dx)*Math.signum(distX);
			ndy = Math.abs(closest.dy)*Math.signum(distY);
		}

	}
	void updateB(int winWidth, int winHeight) {
		dx = ndx;
		dy = ndy;
		
		x += dx;
		y += dy;
		
		if(x<radius) {
			x = 2*radius-x;
			dx = Math.abs(dx);
		}
		else if(x>winWidth-radius) {
			x = 2*(winWidth-radius)-x;
			dx = -Math.abs(dx);
		}
		
		if(y<radius) {
			y = 2*radius-y;
			dy = Math.abs(dy);
		}
		else if(y>winHeight-radius) {
			y = 2*(winHeight-radius)-y;
			dy = -Math.abs(dy);
		}
		
		bounds.setLocation((int)(x-radius), (int)(y-radius));
	}
	
	Rectangle getBounds() {
		return bounds;
	}
	
	private double distSQ(Asteroid b) {
		double dx = b.x - x;
		double dy = b.y - y;
		
		return dx*dx + dy*dy;
	}
	
	double getX() { return x; }
	double getY() { return y; }
	
	double getRadius() { return radius; }
}
