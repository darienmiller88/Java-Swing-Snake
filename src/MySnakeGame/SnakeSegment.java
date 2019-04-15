package MySnakeGame;

import java.awt.Rectangle;

/**
 * @author Darien Miller
 * 
 * This class will contain the information needed to create a segment of the snake body in the output window. SnakeSegment
 * objects will contain information about its own current position and previous x and y position, allowing it to be updated in a way
 * the simulates motion.
 *
 */
@SuppressWarnings("serial")
public class SnakeSegment extends Rectangle{
	
	private int xPrevious, yPrevious, colorOne, colorTwo, colorThree;

	public SnakeSegment (int x, int y, int width, int height, int colorOne, int colorTwo, int colorThree) {
		this.x = xPrevious = x;
		this.y = yPrevious = y;
		this.width = width;
		this.height = height;
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
		this.colorThree = colorThree;
	}
	
	public void updatePrevious(int newXPrevious, int newYPrevious) {
		xPrevious = newXPrevious;
		yPrevious = newYPrevious;
	}
	
	public int getColorOne() {
		return colorOne;
	}
	
	public int getColorTwo() {
		return  colorTwo;
	}

	public int getColorThree() {
		return colorThree;
	}

	public int getXPrevious() {
		return xPrevious;
	}
	
	public int getYPrevious() {
		return yPrevious;
	}
	 
}
