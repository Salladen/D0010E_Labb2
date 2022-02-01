package lab2;

import lab2.level.Level;
import lab2.level.LevelGUI;
import lab2.level.Room;

import java.awt.*;
import java.util.*;

public class Driver {
	public static int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
		int random = start + rnd.nextInt(end - start + 1 - exclude.length);
		for (int ex : exclude) {
			if (random < ex) {
				break;
			}
			random++;
		}
		return random;
	}

	public static ArrayList<java.awt.Color> getColors (int stepSize){
		ArrayList<java.awt.Color> colors = new ArrayList<>();
		for (int red = 0; red <= 255; red += stepSize){
			for (int green = 0; green <= 255; green += stepSize){
				{
					for (int blue = 0; blue <= 255; blue += stepSize){
						colors.add(new Color(red, green , blue));
					}
				}
			}
		}
		return colors;
	}

	public static void getRoomSuggestion(ArrayList<Color> colors, int roomAmount, Room[] rooms, int[][]requestedRoomCords, int maxHeight, int maxWidth){
		Random random = new Random();

		int currentPlacingBounds = 30 * roomAmount * (int) Math.pow(maxHeight + maxWidth, 2);
		ArrayList<int[]> availableRoomCords = new ArrayList<>();

		int randomColorIndex = random.nextInt(colors.size());
		Color color = colors.remove(randomColorIndex);
		int roomWidth = random.nextInt(1, maxWidth + 1);
		int roomHeight = random.nextInt(1, maxHeight + 1);


		for (int x = 0; x < currentPlacingBounds; x++){
			for (int y = 0; y < currentPlacingBounds; y++){
				availableRoomCords.add(new int[]{x,y});
			}
		}

		rooms[0] = new Room(roomWidth, roomHeight, color);
		requestedRoomCords[0] = new int[]{0, 0};
		for (int x = requestedRoomCords[0][0]; x <= requestedRoomCords[0][0] + roomWidth; x++){
			for (int y = requestedRoomCords[0][1]; y <= requestedRoomCords[0][1] + roomHeight; y++){
				availableRoomCords.remove(new int[]{x,y});
			}
		}

		for (int i = 1; i < roomAmount; i++){
			if (((double) availableRoomCords.size() / (currentPlacingBounds * currentPlacingBounds)) <= 0.3){
				for (int x = 0; x < currentPlacingBounds * 2; x++){
					for (int y = currentPlacingBounds; y < currentPlacingBounds * 2; y++){
						availableRoomCords.add(new int[]{x,y});
					}
				}
			}

			int randomCoordinateIndex = random.nextInt(availableRoomCords.size());
			int[] randomCoordinate = availableRoomCords.get(randomCoordinateIndex);

			int searchSize = Math.min(availableRoomCords.size() - (randomCoordinateIndex + 1), 25);

			boolean maxWidthNotFound = true;
			boolean maxHeightNotFound = true;

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

			randomColorIndex = random.nextInt(colors.size());
			color = colors.remove(randomColorIndex);
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
	}

	public static void getConnectionSuggestion(Room[] rooms){
		Random random = new Random();

		int roomAmount = rooms.length;

		ArrayList<Integer> isolatedRooms = new ArrayList<>();
		for (int i = 0; i < roomAmount; i++){
			isolatedRooms.add(i);
		}

		for (int i = 0; i < roomAmount; i++){
			int[] roomExclusion = new int[5];
			roomExclusion[0] = i;
			isolatedRooms.remove(i);

			for (int j = 1; j <= Math.min(4, roomAmount); j++) {
				Room randomRoom;
				if (isolatedRooms.size() > 0){
					int randInt = random.nextInt(0, isolatedRooms.size());

					randomRoom = rooms[isolatedRooms.get(randInt)];
					roomExclusion[j] = isolatedRooms.remove(randInt);
				}
				else{
					int randInt = getRandomWithExclusion(random, 0, roomAmount - 1, roomExclusion);

					randomRoom = rooms[randInt];
					roomExclusion[j] = randInt;
					isolatedRooms.remove(randInt);
				}

				switch (j) {
					case 1:
						rooms[i].connectNorthTo(randomRoom);
						break;

					case 2:
						rooms[i].connectEastTo(randomRoom);
						break;

					case 3:
						rooms[j].connectSouthTo(randomRoom);
						break;

					case 4:
						rooms[i].connectWestTo(randomRoom);
						break;
				}
			}
		}
	}

	public void run() {
		Level level = new Level();
		Random random = new Random();

		ArrayList<java.awt.Color> colors = getColors(32);

		int roomAmount = random.nextInt(5,26);
		Room[] rooms = new Room[roomAmount];
		int[][] requestedRoomCords = new int[roomAmount][roomAmount];

		int maxWidth = 25;
		int maxHeight = 25;

		getRoomSuggestion(colors, roomAmount, rooms, requestedRoomCords, maxHeight, maxWidth);
		getConnectionSuggestion(rooms);

		// Place rooms if legal by leve.place()
		for (int room = 0; room < rooms.length; room++){
			if (!(level.place(rooms[room], requestedRoomCords[room][0], requestedRoomCords[room][1]))){
				rooms[room] = null;
			}
		}
		//TODO Others: Add comments
	}
}
