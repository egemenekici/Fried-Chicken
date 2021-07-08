package game;

import java.awt.Graphics;

public class Cat extends GameObject{
	
	public Cat(int x, int y, int width, int height) {
		super(x, y, width, height);
		sprite[0] = ImageUtils.loadImage("/cat/s.png");
	}

	public void move(int speed) {
		y-=speed;
	}
	
	public void tick(Graphics g, int speed) {
		move(speed);
		if(g == null) System.out.println("LUL");
		g.drawImage(sprite[0], x, y, width, height, null);
		boundBox.x = x;
		boundBox.y = y;
	}
}
