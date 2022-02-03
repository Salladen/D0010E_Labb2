package lab2.level;

import java.util.ArrayList;
import java.util.Observable;

public class Level extends Observable {
    private final ArrayList<Room> roomsContained = new ArrayList<>();

    private boolean hasStarted = false;

    private Room currentLocation;

    public Level() {
    }

    public boolean place(Room r) {
        if (hasStarted) {
            return false; //since it cannot place the room, the action should have failed
            // as hard as if the coordinates was occupied
        } else {
            for (Room room : roomsContained) {
                if (r.roomIsColliding(room)) {
                    return false;
                }
            }
            roomsContained.add(r);

            setChanged();
            notifyObservers();
            return true;

        }
    }

    public void firstLocation(Room r) {
        this.hasStarted = true;
        this.currentLocation = r;

        setChanged();
        notifyObservers();
    }

    void changeRoomNorth() {
        if (currentLocation.doorNorth != null) {
            this.currentLocation = currentLocation.doorNorth;
            setChanged();
            notifyObservers();
        }
    }

    void changeRoomEast() {
        if (currentLocation.doorEast != null) {
            this.currentLocation = currentLocation.doorEast;
            setChanged();
            notifyObservers();
        }
    }

    void changeRoomSouth() {
        if (currentLocation.doorSouth != null) {
            this.currentLocation = currentLocation.doorSouth;
            setChanged();
            notifyObservers();
        }
    }

    void changeRoomWest() {
        if (currentLocation.doorWest != null) {
            this.currentLocation = currentLocation.doorWest;
            setChanged();
            notifyObservers();
        }
    }

    //Code that I wrote after getting confused by the wording in Task 5 but, since it looked uglier than what I
    // first made, I just commented it away. Feel free to delete it if you want a cleaner class.
/*	public void changeRoom(String direction) {
		switch (direction) {
			case "north": {
				if(currentLocation.doorNorth == null) {
					break;
				} else {
					this.currentLocation = currentLocation.doorNorth;
					setChanged();
					notifyObservers();
					break;
				}
			}
			case "east": {
				if(currentLocation.doorEast == null) {
					break;
				} else {
					this.currentLocation = currentLocation.doorEast;
					setChanged();
					notifyObservers();
					break;
				}
			}
			case "south": {
				if(currentLocation.doorSouth == null) {
					break;
				} else {
					this.currentLocation = currentLocation.doorSouth;
					setChanged();
					notifyObservers();
					break;
				}
			}
			case "west": {
				if(currentLocation.doorWest == null) {
					break;
				} else {
					this.currentLocation = currentLocation.doorWest;
					setChanged();
					notifyObservers();
					break;
				}
			}
			default: {
				throw new IllegalArgumentException();
			}
		}
	}*/

}
