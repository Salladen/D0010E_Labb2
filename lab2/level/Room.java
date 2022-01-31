
package lab2.level;

import java.awt.Color;


public class Room { 
	Room doorNorth = null;
	Room doorEast = null;
	Room doorSouth = null;
	Room doorWest = null;
	Color floorColor = null;

	int yCoordinate = 0;
	int xCoordinate = 0;



	public Room(int dx, int dy, Color color) {
		this.doorNorth = null;
		this.doorEast = null;
		this.doorSouth = null;
		this.doorWest = null;

		this.xCoordinate = dx;
		this.yCoordinate = dy;

		this.floorColor = color;
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


}
