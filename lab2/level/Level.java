
package lab2.level;

import java.util.ArrayList;
import java.util.Observable;

public class Level extends Observable {
	ArrayList<Integer> usedXCoordinates = new ArrayList<>();

	ArrayList<Integer> usedYCoordinates = new ArrayList<>();

	boolean hasStarted = false;

	Room currentLocation;

	public Level() {
	}

	public boolean place(Room r, int x, int y) {
		if (hasStarted) {
			return false; //since it cannot place the room, the action should have failed
							// as hard as if the coordinates was occupied
		} else {
			if (usedXCoordinates.contains(x) && usedYCoordinates.contains(y)) {
				return false;
			} else {
				r.xCoordinate = x;
				r.yCoordinate = y;

				usedXCoordinates.add(x);
				usedYCoordinates.add(y);

				return true;
			}
		}
	}

	public void firstLocation(Room r) {
		this.hasStarted = true;
		this.currentLocation = r;
	}

}
