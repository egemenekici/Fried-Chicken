package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class CatSpawner {

	Graphics context;
	int spawn_y = 620;
	ArrayList<Cat> spawnedCats = new ArrayList<Cat>(), idleCats = new ArrayList<Cat>();
	Random r = new Random();

	public void setG(Graphics g) {
		context = g;
	}
	
	public void spawn() {
		Cat to_be_spawned = idleCats.remove(0);
		to_be_spawned.setX(r.nextInt(235)+50);
		to_be_spawned.setY(spawn_y);
		to_be_spawned.tick(context);
		spawnedCats.add(to_be_spawned);
	}
	
	public CatSpawner(Graphics g) {
		context = g;
		for(int i=0; i<10; i++) {
			idleCats.add(new Cat(180, spawn_y, 50, 40));
		}
	}
	
	public boolean checkCollision(Chicken chick) {
		Iterator<Cat> iterator = spawnedCats.iterator();
		while (iterator.hasNext()) {
			if(iterator.next().checkCollision(chick)) {
				return true;
			}
		}
		return false;
	}
	
	public void tick(int speed) {
		System.out.println(idleCats.size());
		Iterator<Cat> iterator = spawnedCats.iterator();
		while (iterator.hasNext()) {
			System.out.println("TICKIN");
			System.out.println(spawnedCats.size());
			Cat temp = iterator.next();
			System.out.println(spawnedCats.size());
			if(temp.isOutOfScreen()) {
				idleCats.add(temp);
				iterator.remove();
				System.out.println("REMOVED");
			} else {

				System.out.println("TICKIN FO SHO");
				temp.tick(context, speed);
				
			}
		}
	}

	public void resetSpawns() {
		idleCats.addAll(spawnedCats);
		spawnedCats.clear();
	}
	
}
