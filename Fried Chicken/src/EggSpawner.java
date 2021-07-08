package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class EggSpawner {

	Graphics context;
	Chicken spawnPoint;
	ArrayList<Egg> spawnedEggs = new ArrayList<Egg>(), idleEggs = new ArrayList<Egg>();
	Random r = new Random();

	public void setG(Graphics g) {
		context = g;
	}
	
	public void spawn() {
		Egg to_be_spawned = new Egg(spawnPoint.getX(), spawnPoint.getY(), 14, 20, spawnPoint.direction);
		to_be_spawned.tick(context);
		spawnedEggs.add(to_be_spawned);
	}
	
	public EggSpawner(Graphics g, Chicken chick) {
		context = g;
		spawnPoint = chick;
	}
	
	public boolean checkCollision(Target projectile) {
		Iterator<Egg> iterator = spawnedEggs.iterator();
		while (iterator.hasNext()) {
			Egg current = iterator.next();
			if(current.checkCollision(projectile)) {
				idleEggs.add(current);
				iterator.remove(); //Egg should be removed
				return true;
			}
		}
		return false;
	}
	
	public void tick() {
		Iterator<Egg> iterator = spawnedEggs.iterator();
		while (iterator.hasNext()) {
			Egg temp = iterator.next();
			temp.tick(context);
		}
	}

	public void resetSpawns() {
		idleEggs.addAll(spawnedEggs);
		spawnedEggs.clear();
	}
	
}
