package lab2.level;

import java.awt.*;
import java.util.ArrayList; //IDEA chose to add that import,
                            //so why not?


public class Room {
    // Only for testing purposes
    private final int width;
    private final int height;
    private int x;
    private int y;
    private final Color floorColor;
    private Room doorNorth;
    private Room doorEast;
    private Room doorSouth;
    private Room doorWest;

    public Room(Room room) {
        this.x = room.x;
        this.y = room.y;

        this.width = room.width;
        this.height = room.height;

        this.floorColor = room.floorColor;

        this.doorNorth = room.doorNorth;
        this.doorEast = room.doorEast;
        this.doorSouth = room.doorSouth;
        this.doorWest = room.doorWest;
        //		System.out.println("\n dx = " + dx
        //							+ "\n dy = " + dy
        //							+ "\n color = " + color);
    }

    public Room(int dx, int dy, Color color) {
        this.width = dx;
        this.height = dy;

        this.floorColor = color;
        //		System.out.println("\n dx = " + dx
        //							+ "\n dy = " + dy
        //							+ "\n color = " + color);

    }

    public Room getNorth() {
        return doorNorth;
    }

    public Room getEast() {
        return doorEast;
    }

    public Room getSouth() {
        return doorSouth;
    }

    public Room getWest() {
        return doorWest;
    }

    public void connectNorthTo(Room r) {
        doorNorth = (r != this) ? r : null;
    }

    public void connectEastTo(Room r) {
        doorEast = (r != this) ? r : null;
    }

    public void connectSouthTo(Room r) {
        doorSouth = (r != this) ? r : null;
    }

    public void connectWestTo(Room r) {
        doorWest = (r != this) ? r : null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getFloorColor(){
        return floorColor;
    }

    public boolean roomIsColliding(Room room2) {
        // Are the sides of one rectangle touching the other?
        return this.x + this.width >= room2.x &&    // r1 right edge past r2 left
                this.x <= room2.x + room2.width &&    // r1 left edge past r2 right
                this.y + this.height >= room2.y &&    // r1 top edge past r2 bottom
                this.y <= room2.y + room2.height;      // r1 bottom edge past r2 top
    }
}
