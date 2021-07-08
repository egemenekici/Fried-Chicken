package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TargetSpawner {

	Graphics context;
	int spawn_y = 620;
	ArrayList<Target> spawnedTargets = new ArrayList<Target>(), idleTargets = new ArrayList<Target>();
	Random r = new Random();

	public void setG(Graphics g) {
		context = g;
	}
	
	public void spawn() {
		Target to_be_spawned = idleTargets.remove(0);
		int points[] = {10,340};
		to_be_spawned.setX(points[r.nextInt(2)]);
		to_be_spawned.setY(spawn_y);
		to_be_spawned.tick(context);
		spawnedTargets.add(to_be_spawned);
	}
	
	public TargetSpawner(Graphics g) {
		context = g;
		for(int i=0; i<10; i++) {
			idleTargets.add(new Target(180, spawn_y, 50, 40));
		}
	}
	
	public boolean checkCollision(Egg projectile) {
		Iterator<Target> iterator = spawnedTargets.iterator();
		while (iterator.hasNext()) {
			if(iterator.next().checkCollision(projectile)) {
				return true;
			}
		}
		return false;
	}
	
	public Iterator<Target> getIterator(){
		return spawnedTargets.iterator();
	}
	
	public void tick() {
		//System.out.println(idleTargets.size());
		Iterator<Target> iterator = spawnedTargets.iterator();
		while (iterator.hasNext()) {
			//System.out.println("TICKIN");
			//System.out.println(spawnedTargets.size());
			Target temp = iterator.next();
			//System.out.println(spawnedTargets.size());
			if(temp.isOutOfScreen()) {
				idleTargets.add(temp);
				iterator.remove();
				//System.out.println("REMOVED");
			} else {
				//System.out.println("TICKIN FO SHO");
				temp.tick(context);
			}
		}
	}

	public void recycle(Target to_be_recycled) {
		idleTargets.add(to_be_recycled);
	}
	
	public void resetSpawns() {
		idleTargets.addAll(spawnedTargets);
		spawnedTargets.clear();
	}
	
}
