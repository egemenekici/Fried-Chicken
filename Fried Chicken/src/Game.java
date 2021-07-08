package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Game implements Runnable {
	private Display display;
	public static int width, height, count = 0, timer = 0;
	public String title;

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	private BufferedImage arkaplan, arkaplan1;
	private BufferedImage gameover_res, scoretable, bronz, altin;
	private HUD scoreDisplay;
	private Chicken flappy;
	private CatSpawner cs;
	private TargetSpawner ts;
	private EggSpawner es;

	private KeyManager keyManager;

	private boolean started = false;
	private boolean gameOver = false;
	static boolean pressed = false;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();

	}

	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					flappy.direction = 3;
					flappy.stop = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					flappy.direction = -3;
					flappy.stop = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					es.spawn();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pressed = true;
				}
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_SPACE) {
					flappy.stop = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					pressed = false;
				}
			}
		});

		arkaplan1 = ImageUtils.loadImage("/assets/bg3.png");

		gameover_res = ImageUtils.loadImage("/assets/go6.png");
		scoretable = ImageUtils.loadImage("/assets/scoretable.png");
		bronz = ImageUtils.loadImage("/assets/blackm.png");
		altin = ImageUtils.loadImage("/assets/goldm.png");
		arkaplan = ImageUtils.loadImage("/assets/bg.jpg");

		flappy = new Chicken(200, 100, 40, 32);

		cs = new CatSpawner(g);
		ts = new TargetSpawner(g);
		es = new EggSpawner(g, flappy);
		scoreDisplay = new HUD(g);
	}

	private void tick() {
		if (started && !gameOver) {
			flappy.move();
		}
	}

	private void render() {

		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear screen
		g.clearRect(0, 0, width, height);
		scoreDisplay.setGContext(g);
		// Draw here!
		cs.setG(g);
		ts.setG(g);
		es.setG(g);

		if (gameOver && !started) {
			g.drawImage(arkaplan, 0, 0, width, height, null);
			g.drawImage(gameover_res, 50, 50, 3 * width / 4, height / 5, null);
			g.drawImage(scoretable, 50, 230, 300, 160, null);
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", 3, 35));

			g.drawString(String.valueOf(scoreDisplay.score), 265, 300);

			g.setColor(Color.white);
			g.setFont(new Font("Tahoma", 3, 35));
			g.drawString(String.valueOf(scoreDisplay.highScore), 270, 360);

			if (scoreDisplay.highScore >= scoreDisplay.score)
				g.drawImage(bronz, 63, 278, 100, 100, null);
			else
				g.drawImage(altin, 63, 278, 100, 100, null);
			
			
			if (pressed) {
				cs.resetSpawns();
				ts.resetSpawns();
				es.resetSpawns();
				flappy.setY(100);
				flappy.setX(200);
				gameOver = false;
				if (scoreDisplay.score > scoreDisplay.highScore)
					scoreDisplay.highScore = scoreDisplay.score;
				scoreDisplay.score = 0;

			}

		}

		if (!started && !gameOver) {
			g.drawImage(arkaplan1, 0, 0, width, height, null);
			if (pressed) {
				started = true;
			}
		}

		if (started && !gameOver) {

			g.drawImage(arkaplan, 0, 0, width, height, null);
			flappy.tick(g);
			timer++;
			if(timer%90 == 0) {
				timer = 0;
				cs.spawn();
				ts.spawn();
				System.out.println("SPAWNED");
			}
			cs.tick(scoreDisplay.level);
			ts.tick();
			es.tick();
			gameOver = cs.checkCollision(flappy);
			
			Iterator<Target> iterator = ts.getIterator();
			while (iterator.hasNext()) {
				Target temp = iterator.next();
				if(es.checkCollision(temp)) {
					scoreDisplay.score += temp.scoreValue;
					ts.recycle(temp);
					iterator.remove();
				}
			}
			
			started = !gameOver;

			scoreDisplay.drawHUD();
			scoreDisplay.level = (scoreDisplay.score / 100) + 1;

		}

		// End Drawing
		bs.show();
		g.dispose();
	}

	public void run() {

		init();
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			if (timer >= 1000000000) {

				ticks = 0;
				timer = 0;

			}

		}

		stop();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.run();

	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}