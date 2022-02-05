
package lab2.level;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

		d = new Display(level,screenSize.width, screenSize.height);

		frame.getContentPane().add(d);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setLocation(0,0);
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

			//Old code by Sallad
/*			g.setColor(Color.BLUE);
			g.fillRect(0,0,200, 200);

			g.setColor(Color.WHITE);
			g.drawRect(0,0,200, 200);

			g.setColor(Color.MAGENTA);
			g.fillRect(this.getWidth() - 200,this.getHeight() - 200,200, 200);

			g.setColor(Color.WHITE);
			g.drawRect(this.getWidth() - 200 - 1,this.getHeight() - 200 - 1,200, 200);*/ // Old code by Sallad

			//Displays all rooms and gives a double border that is pink and black to the currentlocation
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
		}

		public static void drawScaledImage(Level level, JFrame frame, Graphics g) {
			Dimension levelDim = level.getLevelDimensions();
			int levelWidth = levelDim.width;
			int levelHeight = levelDim.height;


			double levelAspect = (double) levelHeight / levelWidth;

			int paneWidth = frame.getContentPane().getWidth();
			int paneHeight = frame.getContentPane().getHeight();

			double paneAspect = (double) paneHeight / paneWidth;

			int scaledX = 0; // top left X position
			int scaledY = 0; // top left Y position
			int scaledWidth; // bottom right X position
			int scaledHeight; // bottom right Y position

			if (levelWidth < paneWidth && levelHeight < paneHeight) {
				// the image is smaller than the canvas
				scaledX = (paneWidth - levelWidth)  / 2;
				scaledY = (paneHeight - levelHeight) / 2;
				scaledWidth = levelWidth;
				scaledHeight = levelHeight;

			} else {
				if (paneAspect > levelAspect) {
					scaledY = paneHeight;
					// keep image aspect ratio
					paneHeight = (int) (paneWidth * levelAspect);
					scaledY = (scaledY - paneHeight) / 2;
				} else {
					scaledX = paneWidth;
					// keep image aspect ratio
					paneWidth = (int) (paneHeight / levelAspect);
					scaledX = (scaledX - paneWidth) / 2;
				}
				scaledWidth = paneWidth;
				scaledHeight = paneHeight;
			}

			frame.getContentPane().paintComponents(g);
		}

	 	private class Listener implements KeyListener {

	 		
	 		public void keyPressed(KeyEvent arg0) {
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
