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
		Level level = new Level();

		int roomAmount = random.nextInt(5,26);

		Room[] rooms = new Room[roomAmount];
		int[][] requestedRoomCords = new int[roomAmount][roomAmount];
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

		int currentPlacingBounds = 30 * roomAmount;
		ArrayList<int[]> availableRoomCords = new ArrayList<>();

		for (int x = 0; x < currentPlacingBounds; x++){
			for (int y = 0; y < currentPlacingBounds; y++){
				availableRoomCords.add(new int[]{x,y});
			}
		}

		for (int i = 0; i < roomAmount; i++){
			if (((double) availableRoomCords.size() / currentPlacingBounds * currentPlacingBounds) <= 0.3){
				for (int x = currentPlacingBounds; x < currentPlacingBounds * 2; x++){
					for (int y = 0; y < Math.pow(currentPlacingBounds * 2,2); y++){
						availableRoomCords.add(new int[]{x,y});
					}
				}
			}

			int randomColorIndex = random.nextInt(colors.size());
			Color color = colors.remove(randomColorIndex);

			int randomCoordinateIndex = random.nextInt(availableRoomCords.size());
			int[] randomCoordinate = availableRoomCords.get(randomCoordinateIndex);

			int searchSize = Math.min(availableRoomCords.size() - (randomCoordinateIndex + 1), 25);

			boolean maxWidthNotFound = true;
			boolean maxHeightNotFound = true;
			int roomWidth;
			int roomHeight;
			int maxHeight = 25;
			int maxWidth = 25;

			for (int j = randomCoordinateIndex; j < searchSize - 1; j++) {
				int[] currentCord = availableRoomCords.get(j);
				int[] nextCord = availableRoomCords.get(j + 1);

				if (nextCord[0] != currentCord[0] + 1 && maxWidthNotFound) {
					maxWidth = currentCord[0] - randomCoordinate[0];
					maxWidthNotFound = false;
				}

				if (nextCord[1] != currentCord[1] + 1 && maxHeightNotFound) {
					maxHeight = currentCord[1] - randomCoordinate[1];
					maxHeightNotFound = false;
				}
			}
			roomWidth = random.nextInt(1, maxWidth + 1);
			roomHeight = random.nextInt(1, maxHeight + 1);

			rooms[i] = new Room(roomWidth, roomHeight, color);
			requestedRoomCords[i] = randomCoordinate;
			for (int x = requestedRoomCords[i][0]; x <= requestedRoomCords[i][0] + roomWidth; x++){
				for (int y = requestedRoomCords[i][1]; y <= requestedRoomCords[i][1] + roomHeight; y++){
					availableRoomCords.remove(new int[]{x,y});
				}
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

		for (int room = 0; room < rooms.length; room++){
			if (!(level.place(rooms[room], requestedRoomCords[room][0], requestedRoomCords[room][1]))){
				rooms[room] = null;
			}
		}
		//TODO Add code: Make sure that the first room is placed at 0,0. This is easily fixed
		//TODO Add code: Make it so that every room is accessible i.e. all rooms are interconnect through other rooms
		//TODO Refactor: Segment code into methods appropriately
		//TODO Others: Add comments
	}
}
