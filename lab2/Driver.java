package lab2;

import lab2.level.Level;
import lab2.level.LevelGUI;
import lab2.level.Room;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class Driver {
    // Function to get ArrayList from Stream
    public static <T> ArrayList<T> removeIfPredicate(ArrayList<T> l, Predicate<T> p) {
        // Create an iterator from the l

        // Find and remove all null
        // Fetching the next element
        // Checking for Predicate condition
        // If the condition matches,
        // remove that element
        l.removeIf(t -> !p.test(t));

        // Return the null
        return l;
    }

    public static <T> List<T> removeIfPredicate(List<T> l, Predicate<T> p) {
        // Create an iterator from the l

        // Find and remove all null
        // Fetching the next element
        // Checking for Predicate condition
        // If the condition matches,
        // remove that element
        l.removeIf(t -> !p.test(t));

        // Return the null
        return l;
    }

    public static int getRandomWithExclusion(Random rnd, int start, int end, ArrayList<Integer> exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.size());
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public static Color[] getColors(int stepSize) {
        List<Color> colors;
        stepSize = Math.abs(stepSize);

       // int test = 255 - (255 % stepSize);
       // colors = new Color[(test / stepSize + test) / stepSize * (255 / stepSize + 1) + test * (255 / stepSize + 1) * (255 / stepSize + 1) / stepSize];
        colors = new ArrayList<>();
        int count = 0;
        // Iterates every color skipping every [stepSize] color
        for (int red = 0; red <= 255; red += stepSize) {
            for (int green = 0; green <= 255; green += stepSize) {
                {
                    for (int blue = 0; blue <= 255; blue += stepSize) {
                        colors.add(new Color(red / 10 * 8, green / 10 * 8, blue / 10 * 8));
                    }
                }
            }
        }
        Collections.shuffle(colors);

        Color[] aColors = new Color[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            aColors[i] = colors.get(i);
        }
        return aColors;
    }



    public static void getRoomSuggestion(Color[] colors, int roomAmount, Room[] rooms, int minWidth, int minHeight, int maxWidth, int maxHeight) {
        ThreadMXBean threadMX = ManagementFactory.getThreadMXBean();


        Random random = new Random();


        // Setting some temporary bounds on where rooms can be placed
        // int xBound = Math.max(12 * maxWidth, 100);
        // int yBound = Math.max(12 * maxHeight, 100);

        int xBound = (maxWidth + minWidth) / 2 * roomAmount / 12;
        int yBound = (maxHeight + minHeight) / 2 * roomAmount / 12;

        int xLowBound = 0;
        int yLowBound = 0;
        int areaTaken = 0;

        // The list of available free spaces
        List<int[]> availableCords = new ArrayList<>();
        // Quadrants of the space so that I don't have to collision check every room
        List<Room>[] quadrants = new ArrayList[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };
        Color color = colors[0];

        // Populate the list of available free spaces
        for (int x = 0; x < xBound; x++) {
            for (int y = 0; y < yBound; y++) {
                availableCords.add(new int[]{x, y});
            }
        }

        // Create the first room
        int roomWidth = random.nextInt(minWidth, maxWidth + 1);
        int roomHeight = random.nextInt(minHeight, maxHeight + 1);
        rooms[0] = new Room(roomWidth, roomHeight, color);
        rooms[0].setXY(0, 0);
        quadrants[0].add(rooms[0]);

		/*for (int x = 0; x < roomWidth; x++){
			for (int y = 0; y < roomHeight; y++){
				if (!(availableCords.remove(allCords[x][y]))) {
					System.out.println(String.format("FAK %s, %s", x, y));
				}
			}
		}*/
        int finalRoomWidth = roomWidth;
        int finalRoomHeight = roomHeight;
        areaTaken += roomWidth * roomHeight;
        removeIfPredicate(availableCords, coordinate ->
                !(coordinate[0] >= 0 && coordinate[0] <= finalRoomWidth - 1
                        &&
                        coordinate[1] >= 0 && coordinate[1] <= finalRoomHeight - 1));

        for (int i = 1; i < roomAmount; i++) {
            if (i % 100 == 0){
                System.out.println((double) 100 * i / (double) roomAmount + "% " + i + "/" + roomAmount);
            }
            /* if ((double) ((availableCords.size() - availableCordsSizeOffset)) / (double) (yBound * xBound) <= 0.6) {
                availableCords.clear();
                for (int x = xBound; x < xBound * 2; x++) {
                    for (int y = 0; y < yBound * 2; y++) {
                        availableCords.add(new int[]{x, y});
                    }
                }
                for (int x = 0; x < xBound * 2; x++) {
                    for (int y = yBound; y < yBound * 2; y++) {
                        availableCords.add(new int[]{x, y});
                    }
                }
				"""for (ArrayList<Room> quadrant: quadrants){
					quadrants[0].addAll(quadrant);
				}
				Set<Room> hashSet = new HashSet<>(quadrants[0]);
				quadrants[0] = new ArrayList<>(hashSet);"""
                for (ArrayList<Room> quadrant : quadrants) {
                    quadrant.clear();
                }

                xBound *= 2;
                yBound *= 2;
            }*/


            roomPlacement:
            while (true) {
                if ((double) (areaTaken) / (double) (yBound * xBound - xLowBound * yLowBound)  >= 0.2 || availableCords.size() == 0) {
                    availableCords.clear();
                    areaTaken = 0;
                    double rescaleFactor = 1.2;
                    for (int x = xBound; x < xBound * rescaleFactor; x++) {
                        for (int y = 0; y < yBound * rescaleFactor; y++) {
                            availableCords.add(new int[]{x, y});
                        }
                    }
                    for (int x = 0; x < xBound * rescaleFactor; x++) {
                        for (int y = yBound; y < yBound * rescaleFactor; y++) {
                            availableCords.add(new int[]{x, y});
                        }
                    }
				/*for (ArrayList<Room> quadrant: quadrants){
					quadrants[0].addAll(quadrant);
				}
				Set<Room> hashSet = new HashSet<>(quadrants[0]);
				quadrants[0] = new ArrayList<>(hashSet);*/
                    for (List<Room> quadrant : quadrants) {
                        quadrant.clear();
                    }
                    xLowBound = xBound;
                    yLowBound = yBound;
                    xBound *= rescaleFactor;
                    yBound *= rescaleFactor;
                }

                // Pick a random index from the list of available coordinates
                int randomCoordinateIndex = random.nextInt(0, availableCords.size());
                // Get the coordinates stored at that index
                int[] randomCoordinate = availableCords.get(randomCoordinateIndex);


                // Try some widths and heights for the room & try some other location if that doesn't work
                roomWidth = random.nextInt(minWidth, maxWidth + 1);
                roomHeight = random.nextInt(minHeight, maxHeight + 1);

                if (randomCoordinate[0] + roomWidth > xBound && minWidth >= xBound - randomCoordinate[0]) {
                    if (minWidth > xBound - randomCoordinate[0]) {
                        availableCords.remove(randomCoordinateIndex);
                        continue;
                    }
                    roomWidth = xBound - randomCoordinate[0];
                }
                if (randomCoordinate[1] + roomHeight > yBound && minHeight >= yBound - randomCoordinate[1]) {
                    if (minHeight > yBound - randomCoordinate[1]) {
                        availableCords.remove(randomCoordinateIndex);
                        continue;
                    }
                    roomHeight = yBound - randomCoordinate[1];
                }


                for (int dy = roomHeight; dy >= minHeight; dy--) {
                    sizeTest:
                    for (int dx = roomWidth; dx >= minWidth; dx--) {
                        Room roomCandidate = new Room(dx, dy, color);
                        roomCandidate.setXY(randomCoordinate[0], randomCoordinate[1]);

                        // Does the room intersect with the 1st quadrant?
                        ArrayList<Integer> intersectedQuadrants = new ArrayList<>();
                        Room rect2 = new Room(xBound / 2, yBound / 2, Color.black);
                        rect2.setXY(0,0);

                        if (roomCandidate.roomIsColliding(rect2)) {
                            intersectedQuadrants.add(0);
                        }

                        // Does the room intersect with the 2nd quadrant?
                        rect2 = new Room(xBound / 2, yBound / 2, Color.black);
                        rect2.setXY(xBound / 2, 0);

                        if (roomCandidate.roomIsColliding(rect2)) {
                            intersectedQuadrants.add(1);
                        }

                        // Does the room intersect with the 3rd quadrant?
                        rect2 = new Room(xBound / 2, yBound / 2, Color.black);
                        rect2.setXY(0, yBound / 2);

                        if (roomCandidate.roomIsColliding(rect2)) {
                            intersectedQuadrants.add(2);
                        }

                        // Does the room intersect with the 4th quadrant?
                        rect2 = new Room(xBound / 2, yBound / 2, Color.black);
                        rect2.setXY(xBound / 2, yBound / 2);

                        if (roomCandidate.roomIsColliding(rect2)) {
                            intersectedQuadrants.add(3);
                        }

                        for (int quadrant : intersectedQuadrants) {
                            for (Room room : quadrants[quadrant]) {
                                Room coordinatePoint = new Room(1, 1, Color.black);
                                coordinatePoint.setXY(randomCoordinate[0], randomCoordinate[1]);

                                if (coordinatePoint.roomIsColliding(room)){ // Origin is occupied by room, no size will work
                                    availableCords.remove(randomCoordinateIndex);
                                    continue roomPlacement;
                                }
                                else if (roomCandidate.roomIsColliding(room) && dx > minWidth && dy > minHeight) { // If the room doesn't fit, try a smaller size
                                    continue sizeTest;
                                } else if (roomCandidate.roomIsColliding(room)) { // If no sizes worked then remove this index and try a different one
                                    availableCords.remove(randomCoordinateIndex);
                                    continue roomPlacement;
                                }
                            }
                        }

                        // Places itself in the correct quadrants now that we have decided that the room is to be placed
                        for (int quadrant : intersectedQuadrants) {
                            quadrants[quadrant].add(roomCandidate);
                        }

                        // Add the room to the list of rooms
                        color = colors[i % colors.length];

                        roomCandidate = new Room(dx, dy, color);
                        roomCandidate.setXY(randomCoordinate[0], randomCoordinate[1]);
                        areaTaken += dx * dy;
                        rooms[i] = roomCandidate;


                        /* This removed all the coordinates of the room from the list of available coordinates.
                        int finalRoomWidth1 = x;
                        int finalRoomHeight1 = y;
                        removeIfPredicate(availableCords, coordinate ->
                                !(coordinate[0] >= randomCoordinate[0] && coordinate[0] <= randomCoordinate[0] + finalRoomWidth1 - 1
                                        &&
                                        coordinate[1] >= randomCoordinate[1] && coordinate[1] <= randomCoordinate[1] + finalRoomHeight1 - 1));
                        */

                        /*
                        This less robust solution has replaced the above one because it's far less expensive. This is
                        because it doesn't iterate through the entire list of available coordinates every time a room
                        has been picked.

                        This one will only delete those coordinates if it manages to hit them through chance.
                         */
                        availableCords.remove(randomCoordinateIndex);

                        // We've found a room, so we'll create the next one
                        break roomPlacement;
                    }
                }
            }
        }
    }

    public static void getConnectionSuggestion(Room[] rooms) {
        Random random = new Random();

        int roomAmount = rooms.length;

        ArrayList<Integer> isolatedRooms = new ArrayList<>();
        for (int i = 0; i < roomAmount; i++) {
            isolatedRooms.add(i);
        }

        for (int i = 0; i < roomAmount; i++) {
            ArrayList<Integer> roomExclusion = new ArrayList<>();
            roomExclusion.add(i);
            if (isolatedRooms.size() > 0) {
                isolatedRooms.remove((Integer) i);
            }

            for (int j = 1; j <= Math.min(4, roomAmount); j++) {
                Room randomRoom;
                int randInt;
                if (isolatedRooms.size() > 0) {
                    randInt = random.nextInt(0, isolatedRooms.size());

                    randomRoom = rooms[isolatedRooms.get(randInt)];
                    roomExclusion.add(isolatedRooms.remove(randInt));
                } else {

                    randInt = getRandomWithExclusion(random, 0, roomAmount - 1, roomExclusion);

                    randomRoom = rooms[randInt];
                    roomExclusion.add(randInt);
                }

                switch (j) {
                    case 1 -> rooms[i].connectNorthTo(randomRoom);
                    case 2 -> rooms[i].connectEastTo(randomRoom);
                    case 3 -> rooms[j].connectSouthTo(randomRoom);
                    case 4 -> rooms[i].connectWestTo(randomRoom);
                }
            }
        }
    }

    public void run() {
        Level level = new Level();
        Random random = new Random();
        ThreadMXBean threadMX = ManagementFactory.getThreadMXBean();

        Color[] colors = getColors(63);

        // int roomAmount = random.nextInt(5,26);
        System.out.println(colors.length);
        int roomAmount = colors.length;
        Room[] rooms = new Room[roomAmount];

        int randint1 = random.nextInt(3,50);
        int randint2 =  random.nextInt(3, randint1 + 1);

        int maxWidth = randint1;
        int maxHeight = randint1;

        int minHeight = randint2;
        int minWidth = randint2;

        double time = threadMX.getThreadCpuTime(1);
        getRoomSuggestion(colors, roomAmount, rooms, minWidth, minHeight, maxWidth, maxHeight);
        time = threadMX.getThreadCpuTime(1) - time;
        time = time / Math.pow(10, 9);


        getConnectionSuggestion(rooms);

        // Place rooms, if legal by leve.place()
        for (int room = 0; room < rooms.length; room++) {
            if (!(level.place(rooms[room]))) {
                rooms[room] = null;
            }
        }

        System.out.println(time + " seconds");
        System.out.printf("%s rooms / second%n", (rooms.length * 100) / time);

        LevelGUI gui = new LevelGUI(level, "lvl1");
        //TODO Others: Add comments
    }

    public void changeLevel(Level l){
        Level level = l;
        l.clearRoomsContained();
        Random random = new Random();
        ThreadMXBean threadMX = ManagementFactory.getThreadMXBean();

        Color[] colors = getColors(63);

        // int roomAmount = random.nextInt(5,26);
        System.out.println(colors.length);
        int roomAmount = colors.length;
        Room[] rooms = new Room[roomAmount];

        int randint1 = random.nextInt(3,50);
        int randint2 =  random.nextInt(3, randint1 + 1);

        int maxWidth = randint1;
        int maxHeight = randint1;

        int minHeight = randint2;
        int minWidth = randint2;

        double time = threadMX.getThreadCpuTime(1);
        getRoomSuggestion(colors, roomAmount, rooms, minWidth, minHeight, maxWidth, maxHeight);
        time = threadMX.getThreadCpuTime(1) - time;
        time = time / Math.pow(10, 9);


        getConnectionSuggestion(rooms);

        // Place rooms, if legal by leve.place()
        for (int room = 0; room < rooms.length; room++) {
            if (!(level.place(rooms[room]))) {
                rooms[room] = null;
            }
        }

        System.out.println(time + " seconds");
        System.out.printf("%s rooms / second%n", (rooms.length * 100) / time);
        //TODO Others: Add comments
    }
}
