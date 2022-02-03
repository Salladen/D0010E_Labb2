package lab2.level;

import lab2.Driver;

import java.awt.*;


public class Room {
    Room doorNorth;
    Room doorEast;
    Room doorSouth;
    Room doorWest;

    Color floorColor;
    // Only for testing purposes
    private final int width;
    private final int height;
    private final int x;
	private final int y;


    public Room(int x, int y, int dx, int dy, Color color) {
        this.x = x;
        this.y = y;

        width = dx;
        height = dy;

        floorColor = color;
        //		System.out.println("\n dx = " + dx
        //							+ "\n dy = " + dy
        //							+ "\n color = " + color);


    }

    public void connectNorthTo(Room r) {
        doorNorth = r;

    }

    public void connectEastTo(Room r) {
        doorEast = r;
    }

    public void connectSouthTo(Room r) {
        doorSouth = r;
    }

    public void connectWestTo(Room r) {
        doorWest = r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean roomIsColliding(Room room2) {
        // Are the sides of one rectangle touching the other?

        return this.x + this.width >= room2.x &&    // r1 right edge past r2 left
                this.x <= room2.x + room2.width &&    // r1 left edge past r2 right
                this.y + this.height >= room2.y &&    // r1 top edge past r2 bottom
                this.y <= room2.y + room2.height;      // r1 bottom edge past r2 top
    }
}
