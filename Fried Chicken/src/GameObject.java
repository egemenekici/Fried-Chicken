package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int x, y, width, height;
	protected Rectangle boundBox;
	protected BufferedImage[] sprite = new BufferedImage[2];

	GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		boundBox = new Rectangle(x,y,width,height);
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Rectangle getBoundBox() {
		return boundBox;
	}
	
	public void tick(Graphics g) {
		
	}
	
	public boolean checkCollision(GameObject go) {	
		return boundBox.intersects(go.getBoundBox());
	}
	
	public boolean isOutOfScreen() {
		return y+height <= 0;
	}
}
