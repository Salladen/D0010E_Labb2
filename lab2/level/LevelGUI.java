
package lab2.level;

import lab2.Driver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import static java.awt.Color.WHITE;


public class LevelGUI implements Observer {

	private Level level;
	private Display d;

	public LevelGUI(Level level, String name) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.level = level;
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
			/* Old code by drcatjk
			for(int i = 0; i < level.roomsContained.size(); i++) {
				if(level.roomsContained.get(i) == level.currentLocation) {
					g.setColor(Color.PINK);
					g.drawRect(level.roomsContained.get(i).x - 2, level.roomsContained.get(i).y - 2,
							level.roomsContained.get(i).width + 4, level.roomsContained.get(i).height + 4);

					g.setColor(Color.BLACK);
					g.drawRect(level.roomsContained.get(i).x - 3, level.roomsContained.get(i).y - 3,
							level.roomsContained.get(i).width + 6, level.roomsContained.get(i).height + 6);

				}
				g.setColor(level.roomsContained.get(i).floorColor);

				g.fillRect(level.roomsContained.get(i).x, level.roomsContained.get(i).y,
							level.roomsContained.get(i).width, level.roomsContained.get(i).height);
			}
			 */
			drawScaledLevel(g);
		}

		public void drawScaledLevel(Graphics g) {
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

			g.setColor(WHITE);
			g.drawRect(xOffset, yOffset, scaledWidth, scaledHeight);
			for (Room room: this.level.getRoomsContained()){
				g.setColor(room.getFloorColor());
				g.fillRect((int) (room.getX() * widthRatio) + xOffset
						, (int) (room.getY() * heightRatio) + yOffset
						, (int) ((double) room.getWidth() * widthRatio)
						, (int) ((double) room.getHeight() * heightRatio));

				for (int borderSize = 0; borderSize < 1; borderSize++){
					g.setColor(Color.WHITE);
					g.drawRect((int) (room.getX() * widthRatio) + xOffset - borderSize
							, (int) (room.getY() * heightRatio) + yOffset - borderSize
							, (int) ((double) room.getWidth() * widthRatio + borderSize*2)
							, (int) ((double) room.getHeight() * heightRatio) + borderSize*2);
				}
			}
		}

	 	private class Listener implements KeyListener {

	 		
	 		public void keyPressed(KeyEvent arg0) {
				 System.out.println(KeyEvent.getKeyText(arg0.getKeyCode()));
				 if (KeyEvent.getKeyText(arg0.getKeyCode()).equals("F5")){
					 new Driver().changeLevel(level);
					 repaint();
				 }
				 switch(KeyEvent.getKeyText(arg0.getKeyCode())) {
					 case "Up": {
						 level.changeRoomNorth();
						 break;
					 }
					 case "Right": {
						 level.changeRoomEast();
						 break;
					 }
					 case "Down": {
						 level.changeRoomSouth();
						 break;
					 }
					 case "Left": {
						 level.changeRoomWest();
						 break;
					 }
					 case "Escape": {
						 System.exit(0);
					 }
					 case "F5": {
						 new Driver().changeLevel(level);
						 repaint();
					 }
				 }
				 //System.out.println(KeyEvent.getKeyText(arg0.getKeyCode()));
				 repaint();
	 		}

	 		public void keyReleased(KeyEvent arg0) {
	 		}

	 		public void keyTyped(KeyEvent event) {
	 		}
	 	}

	}
	
}
