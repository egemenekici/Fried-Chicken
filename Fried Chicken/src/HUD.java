package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

	int score = 0, highScore = 0, level = 1;
	Graphics context = null;

	public HUD(Graphics g) {
		context = g;
	}
	
	public void drawHUD() {
		context.setColor(Color.white);
		context.setFont(new Font("Comic Sans MS", 3, 40));
		context.drawString(String.valueOf(score), 5, 45);
	}
	
	public void drawLevelUp() {
		context.setColor(Color.white);
		context.setFont(new Font("Tahoma", 3, 40));
		context.drawString("Level: " + String.valueOf(level), 200, 100);
	}
	
	public void setGContext(Graphics g) {
		context = g;
	}
	
}