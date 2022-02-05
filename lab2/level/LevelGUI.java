
package lab2.level;

import lab2.Driver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

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
			/*
			g.setColor(Color.BLUE);
			g.fillRect(0,0,200, 200);

			g.setColor(Color.WHITE);
			g.drawRect(0,0,200, 200);

			g.setColor(Color.MAGENTA);
			g.fillRect(this.getWidth() - 200,this.getHeight() - 200,200, 200);

			g.setColor(Color.WHITE);
			g.drawRect(this.getWidth() - 200 - 1,this.getHeight() - 200 - 1,200, 200);
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
	 		}

	 		public void keyReleased(KeyEvent arg0) {
	 		}

	 		public void keyTyped(KeyEvent event) {
	 		}
	 	}

	}
	
}
