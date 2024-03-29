package lab2.level;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

public class Level extends myObservable {
    private final ArrayList<Room> roomsContained = new ArrayList<>();

    private boolean hasStarted = false;

    private Room currentLocation;

    public Level() {
    }

    public boolean place(Room r) {

        if (false) {
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
        if (currentLocation.getNorth() != null) {
            this.currentLocation = currentLocation.getNorth();
            setChanged();
            notifyObservers();
        }
    }

    void changeRoomEast() {
        if (currentLocation.getEast() != null) {
            this.currentLocation = currentLocation.getEast();
            setChanged();
            notifyObservers();
        }
    }

    void changeRoomSouth() {
        if (currentLocation.getSouth() != null) {
            this.currentLocation = currentLocation.getSouth();
            setChanged();
            notifyObservers();
        }
    }

    void changeRoomWest() {
        if (currentLocation.getWest() != null) {
            this.currentLocation = currentLocation.getWest();
            setChanged();
            notifyObservers();
        }
    }

    Dimension getLevelDimensions(){
        AtomicInteger width = new AtomicInteger();
        AtomicInteger height = new AtomicInteger();

        Comparator<Room> c = Comparator.comparingInt(r -> (r.getWidth() + r.getX()));
        roomsContained.stream().max(c).ifPresent(room -> width.set(room.getX() + room.getWidth()));

        c = Comparator.comparingInt(r -> (r.getHeight() + r.getY()));
        roomsContained.stream().max(c).ifPresent(room -> height.set(room.getY() + room.getHeight()));

        return new Dimension(width.get(),height.get());
    }

    public ArrayList<Room> getRoomsContained() {
        return this.roomsContained;
    }

    public void clearRoomsContained() {this.roomsContained.clear();}

    Room getCurrentLocation() {
        return this.currentLocation;
    }

}
