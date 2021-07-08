package game;

import java.awt.Graphics;

public class Egg extends GameObject{
	
	private int direction;
	
	public Egg(int x, int y, int width, int height, int direction) {
		super(x, y, width, height);
		sprite[0] = ImageUtils.loadImage("/chickenegg/12.png");
		this.direction = direction;
	}

	public void move() {
		x+=direction*3;
		y-=2;
	}
	
	public void tick(Graphics g) {
		g.drawImage(sprite[0], x, y, 18, 24, null);
		boundBox.x = x;
		boundBox.y = y;
		move();
	}
}
