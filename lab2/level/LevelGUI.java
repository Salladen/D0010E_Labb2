
package lab2.level;

import lab2.Driver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import static java.awt.Color.WHITE;


public class LevelGUI extends myObserver {
	private Level level;
	private Display d;

	public LevelGUI(Level level, String name) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.level = level;
		level.addObserver(this);

		Dimension levelDims = level.getLevelDimensions();
		System.out.println(levelDims);

		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: You should change 200 to a value 
		// depending on the size of the level
		d = new Display(level,screenSize.width / 2, screenSize.height / 2);

		frame.getContentPane().add(d);
		// frame.setAlwaysOnTop(true);
		frame.setLocation(screenSize.width / 4,screenSize.height / 4);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void update(Observable arg0, Object arg1) {
		d.repaint();
	}


	private class Display extends JPanel {
		private Level level;

		public Display(Level l, int x, int y) {
			this.level = l;

			addKeyListener(new Listener());
			
			setBackground(Color.darkGray.darker());
			setPreferredSize(new Dimension(x,y));
			setFocusable(true);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawScaledLevel(g);
		}

		private void drawScaledLevel(Graphics g) {
			Dimension levelDim = this.level.getLevelDimensions();
			JRootPane frame = this.getRootPane();

			int levelWidth = levelDim.width;
			int levelHeight = levelDim.height;

			double levelAspect = (double) levelHeight / levelWidth;

			// int paneWidth = frame.getContentPane().getWidth() - 1;
			// int paneHeight = frame.getContentPane().getHeight() - 1;

			int paneWidth = this.getWidth() - 1;
			int paneHeight = this.getHeight() - 1;

			double paneAspect = (double) paneHeight / paneWidth;
			int xOffset = 0; // top left X position
			int yOffset = 0; // top left Y position
			int scaledWidth = 0; // bottom right X position
			int scaledHeight = 0; // bottom right Y position




			if (paneAspect > levelAspect) {
				yOffset = paneHeight;
				// keep image aspect ratio
				paneHeight = (int) (paneWidth * levelAspect);
				yOffset = (yOffset - paneHeight) / 2;
			} else {
				xOffset = paneWidth;
				// keep image aspect ratio
				paneWidth = (int) (paneHeight / levelAspect);
				xOffset = (xOffset - paneWidth) / 2;
			}
			scaledWidth = paneWidth;
			scaledHeight = paneHeight;
			double widthRatio = (double) scaledWidth / (double) levelWidth;
			double heightRatio = (double) scaledHeight / (double) levelHeight;

			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(xOffset, yOffset, scaledWidth, scaledHeight);

			for (Room room: this.level.getRoomsContained()){
				g.setColor(room.getFloorColor());
				g.fillRect((int) (room.getX() * widthRatio) + xOffset
						, (int) (room.getY() * heightRatio) + yOffset
						, (int) ((double) room.getWidth() * widthRatio)
						, (int) ((double) room.getHeight() * heightRatio));

				drawRoomConnections(g, room, widthRatio, heightRatio, xOffset, yOffset);

				Color[] borderColor = new Color[]{Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK};
				for (int borderSize = 0; borderSize < ((level.getCurrentLocation() == room) ? 8 : 1); borderSize++){
					g.setColor(borderColor[borderSize]);

					g.drawRect((int) (room.getX() * widthRatio) + xOffset - borderSize
							, (int) (room.getY() * heightRatio) + yOffset - borderSize
							, (int) ((double) room.getWidth() * widthRatio + borderSize*2)
							, (int) ((double) room.getHeight() * heightRatio) + borderSize*2);
				}
			}
			//TODO TASK 4.4, Add a way to display connections between room
		}

		private void drawRoomConnections(Graphics g, Room room, double widthRatio, double heightRatio, int xOffset, int yOffset){
			g.setColor(room.getFloorColor());
			int x = (int) (room.getX() * widthRatio) + xOffset;
			int y = (int) (room.getY() * heightRatio) + yOffset;
			int width = (int) ((double) room.getWidth() * widthRatio);
			int height = (int) ((double) room.getHeight() * heightRatio);

			if (room.getNorth() != null) {
				int n_x = (int) (room.getNorth().getX() * widthRatio) + xOffset;
				int n_y = (int) (room.getNorth().getY() * heightRatio) + yOffset;
				int n_width = (int) ((double) room.getNorth().getWidth() * widthRatio);
				int n_height = (int) ((double) room.getNorth().getHeight() * heightRatio);

				g.setColor(room.getNorth().getFloorColor());
				g.fillRect(x + width * 2 / 5, y, width / 5, height / 5);

				// drawAtConnection(g, x + width / 2, y, n_x, n_y, n_width, n_height, room.getDoorNorthEndpoint());

				g.setColor(Color.BLACK);
				g.drawRect(x + width * 2 / 5, y, width / 5, height / 5);
			}

			if (room.getEast() != null) {
				int e_x = (int) (room.getEast().getX() * widthRatio) + xOffset;
				int e_y = (int) (room.getEast().getY() * heightRatio) + yOffset;
				int e_width = (int) ((double) room.getEast().getWidth() * widthRatio);
				int e_height = (int) ((double) room.getEast().getHeight() * heightRatio);

				g.setColor(room.getEast().getFloorColor());
				g.fillRect(x + width * 4 / 5, y + height * 2 / 5, width / 5, height / 5);

				// drawAtConnection(g, x + width, y + height / 2, e_x, e_y, e_width, e_height, room.getDoorEastEndpoint());

				g.setColor(Color.BLACK);
				g.drawRect(x + width * 4 / 5, y + height * 2 / 5, width / 5, height / 5);
			}

			if (room.getSouth() != null) {
				int s_x = (int) (room.getSouth().getX() * widthRatio) + xOffset;
				int s_y = (int) (room.getSouth().getY() * heightRatio) + yOffset;
				int s_width = (int) ((double) room.getSouth().getWidth() * widthRatio);
				int s_height = (int) ((double) room.getSouth().getHeight() * heightRatio);

				g.setColor(room.getSouth().getFloorColor());
				g.fillRect(x + width * 2 / 5, y + height * 4 / 5, width / 5, height / 5);

				// drawAtConnection(g, x + width / 2, y + height, s_x, s_y, s_width, s_height, room.getDoorSouthEndpoint());

				g.setColor(Color.BLACK);
				g.drawRect(x + width * 2 / 5, y + height * 4 / 5, width / 5, height / 5);
			}

			if (room.getWest() != null) {
				int w_x = (int) (room.getWest().getX() * widthRatio) + xOffset;
				int w_y = (int) (room.getWest().getY() * heightRatio) + yOffset;
				int w_width = (int) ((double) room.getWest().getWidth() * widthRatio);
				int w_height = (int) ((double) room.getWest().getHeight() * heightRatio);

				g.setColor(room.getWest().getFloorColor());
				g.fillRect(x, y + height * 2 / 5, width / 5, height / 5);

				// drawAtConnection(g, x, y / 2, w_x, w_y, w_width, w_height, room.getDoorWestEndpoint());

				g.setColor(Color.BLACK);
				g.drawRect(x, y + height * 2 / 5, width / 5, height / 5);
			}
		}

	 	private class Listener implements KeyListener {
	 		public void keyPressed(KeyEvent arg0) {
				 switch(arg0.getKeyCode()) {
					 case KeyEvent.VK_W: {
						 level.changeRoomNorth();
						 break;
					 }
					 case KeyEvent.VK_D: {
						 level.changeRoomEast();
						 break;
					 }
					 case KeyEvent.VK_S: {
						 //TODO doesn't seem to work, no connections to the south?
						 level.changeRoomSouth();
						 break;
					 }
					 case KeyEvent.VK_A: {
						 level.changeRoomWest();
						 break;
					 }
					 case KeyEvent.VK_ESCAPE: {
						 System.exit(0);
					 }
					 case KeyEvent.VK_F5: {
						 new Driver().changeLevel(level);
					 }
				 }
				 //System.out.println(KeyEvent.getKeyText(arg0.getKeyCode()));
	 		}

	 		public void keyReleased(KeyEvent arg0) {
	 		}

	 		public void keyTyped(KeyEvent event) {
	 		}
	 	}

	}

	private void drawAtConnection(Graphics g, int x, int y, int d_x, int d_y, int d_width, int d_height, String cardinal_direction) {
		switch (cardinal_direction){
			/*case "north" -> g.drawLine(x, y, d_x + d_width / 2, d_y);
			case "east" -> g.drawLine(x, y, d_x + d_width, d_y + d_height / 2);
			case "south" -> g.drawLine(x, y, d_x + d_width / 2, d_y + d_height);
			case "west" -> g.drawLine(x, y, d_x, d_y + d_height / 2);*/

			case "north" -> g.drawPolygon(new int[]{x, d_x + d_width / 2}, new int[]{y, d_y}, 2);
			case "east" -> g.drawPolygon(new int[]{x, d_x + d_width}, new int[]{y, d_y + d_height / 2}, 2);
			case "south" -> g.drawPolygon(new int[]{x, d_x + d_width / 2}, new int[]{y, d_y + d_height}, 2);
			case "west" -> g.drawPolygon(new int[]{x, d_x}, new int[]{y, d_y + d_height / 2}, 2);

		}
	}

}
