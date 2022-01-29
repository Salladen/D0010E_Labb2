package lab2;

import lab2.level.Level;
import lab2.level.LevelGUI;
import lab2.level.Room;

import java.awt.*;
import java.util.*;

public class Driver {

	public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
		int random = start + rnd.nextInt(end - start + 1 - exclude.length);
		for (int ex : exclude) {
			if (random < ex) {
				break;
			}
			random++;
		}
		return random;
	}

	public void run() {
		Random random = new Random();

		int roomAmount = random.nextInt(5,26);

		Room[] rooms = new Room[roomAmount];
		int[][] requestedRooms = new int[roomAmount][roomAmount];
		ArrayList<java.awt.Color> colors = new ArrayList<>();
		for (int red = 0; red <= 255; red++){
			for (int green = 0; green <= 255; green++){
				{
					for (int blue = 0; blue <= 255; blue++){
						colors.add(new Color(red, green , blue));
					}
				}
			}
		}

		for (int i = 0; i < roomAmount; i++){
			for (int j = 0; j < roomAmount; j++){
				int randomColorIndex = random.nextInt(colors.size());

				int origin = i * 10;
				int bound = (i == 0) ? 10 : i * 20;
				rooms[i] = new Room(random.nextInt(origin, bound), random.nextInt(origin, bound), colors.remove(randomColorIndex));
			}
		}

		for (int i = 0; i < roomAmount; i++){
			int[] roomExclusion = new int[5];
			roomExclusion[0] = i;
			for (int j = 1; j <= 4; j++) {
				switch (j) {
					case 1 -> {
						int randInt = getRandomWithExclusion(random, 0, roomAmount - 1, roomExclusion);
						Room randomRoom = rooms[getRandomWithExclusion(random, 0, roomAmount - 1, i)];
						rooms[i].connectNorthTo(randomRoom);
						roomExclusion[j] = randInt;
					}
					case 2 -> {
						int randInt = getRandomWithExclusion(random, 0, roomAmount - 1, roomExclusion);
						Room randomRoom = rooms[randInt];
						rooms[i].connectEastTo(randomRoom);
						roomExclusion[j] = randInt;
					}
					case 3 -> {
						int randInt = getRandomWithExclusion(random, 0, roomAmount - 1, roomExclusion);
						Room randomRoom = rooms[randInt];
						rooms[j].connectSouthTo(randomRoom);
						roomExclusion[j] = randInt;
					}
					case 4 -> {
						int randInt = getRandomWithExclusion(random, 0, roomAmount - 1, roomExclusion);
						Room randomRoom = rooms[randInt];
						rooms[i].connectWestTo(randomRoom);
						roomExclusion[j] = randInt;
					}
				}
			}
		}
	}
}
