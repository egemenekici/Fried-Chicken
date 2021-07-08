package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Chicken extends GameObject {

	Animation anim = new Animation(500, sprite);
	int direction = 0;
	BufferedImage currentSprite;
	boolean stop = true;
	
	public Chicken(int x, int y, int width, int height) {
		super(x, y, width, height);
		sprite[0] = ImageUtils.loadImage("/chickenegg/d.png");
		sprite[1] = ImageUtils.createFlipped(sprite[0]);
	}

	public void move() {
		if (!stop && x < Game.width - width && x > 0) {
			x += direction;
			
			if(x <= 0 || x >= Game.width - width)
				x -= direction;
				
		}
	}
	
	public void tick(Graphics g) {
		anim.tick();
		
		if(direction == -3) {
			currentSprite = sprite[0];
		} else if(direction == 3) {
			currentSprite = sprite[1];
		}
		
		g.drawImage(currentSprite, x, y, width, height, null);
		
		boundBox.x = x;
		boundBox.y = y;
	}
	
	@Override
	public boolean isOutOfScreen() {
		return (x <= 0) || (x+width-5 >= 400);
	}
}
