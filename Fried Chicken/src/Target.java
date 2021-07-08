package game;

import java.awt.Graphics;
import java.util.Random;

public class Target extends GameObject{
	
	int scoreValue = 5; //TODO:Change
	
	public Target(int x, int y, int width, int height) {
		super(x, y, width, height);
		Random r = new Random();
		int targetSize = r.nextInt(3) + 1;
		sprite[0] = ImageUtils.loadImage("/assets/2.png");
		this.width *= Math.pow(0.75, targetSize);
		this.height *= Math.pow(0.75, targetSize);
		scoreValue *= targetSize;
	}

	public void move() {
		y--;
	}
	
	public void tick(Graphics g) {
		move();
		if(g == null) System.out.println("LUL");
		g.drawImage(sprite[0], x, y, width, height, null);
		boundBox.x = x;
		boundBox.y = y;
	}
}
