
package lab2.level;

import java.util.ArrayList;
import java.util.Observable;

public class Level extends Observable {
	ArrayList<Integer> usedXCoordinates = new ArrayList<>();

	ArrayList<Integer> usedYCoordinates = new ArrayList<>();

    public Level() {
    }
	public boolean place(Room r, int x, int y)  {
		if(usedXCoordinates.contains(x) && usedYCoordinates.contains(y)) {
			return false;
		} else {
			r.xCoordinate = x;
			r.yCoordinate = y;

			usedXCoordinates.add(x);
			usedYCoordinates.add(y);

			return true;
		}
	}
	public void firstLocation(Room r) {

	}

}
