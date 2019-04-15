package MySnakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class SnakeBody{
	
	private SnakeSegment head;
	private Random rand;
	private ArrayList<SnakeSegment> tail; 
	private Direction direction;
	private int windowWidth, windowHeight, amountToGrowBy;
	
	public SnakeBody(int x, int y, int width, int windowWidth, int windowHeight, int amountToGrowBy) {
		rand = new Random();
		tail = new ArrayList<SnakeSegment>(); 
		head = new SnakeSegment(x, y, width, width, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.amountToGrowBy = amountToGrowBy;
	}
	
	public void grow() {
		
		/**
		 * When grow() is called in main(), this method will create a new snake segment. This will be repeated 
		 * "amountToGrowBy" times. This will result in me being able to control how many units the snake will grow by. 
		 */
		for(int i = 0; i < amountToGrowBy; i++) {
			if(tail.isEmpty()) {
				tail.add(new SnakeSegment(head.getXPrevious(), head.getYPrevious(), head.width, head.height, 
						rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
			}
			else {
				tail.add(new SnakeSegment(tail.get(tail.size() - 1).getXPrevious(), tail.get(tail.size() - 1).getYPrevious(), 
						head.width, head.height, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
			}
		}
		
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * This method is responsible for updating the current and previous x and y of the head of the snake. The constant updating will
	 * create the illusion of movement in the 
	 * 
	 * @param rightBoundary: This parameter represents the width of the window. 
	 * @param bottomBoundary: This parameter represents the height of the window.
	 */
	private void move(int rightBoundary, int bottomBoundary) {
		if (direction == Direction.RIGHT) {
			head.updatePrevious(head.x, head.y);
			head.x += head.height;
		}else if(direction == Direction.DOWN) {
			head.updatePrevious(head.x, head.y);
			head.y += head.height;
		}else if(direction == Direction.LEFT) {
			head.updatePrevious(head.x, head.y);
			head.x -= head.height;
		}else if(direction == Direction.UP) {
			head.updatePrevious(head.x, head.y);
			head.y -= head.height;
		}
			
		checkBounds(rightBoundary, bottomBoundary);
	}
	
	public void printSnake(Graphics g) {
		g.setColor(new Color(head.getColorOne(), head.getColorTwo(), head.getColorThree()));
		g.fillRect(head.x, head.y, head.width, head.height);
		for (SnakeSegment s : tail) {
			g.setColor(new Color(s.getColorOne(), s.getColorTwo(), s.getColorThree()));
			g.fillRect(s.x, s.y, s.width, s.height);
		}
			
	}
	/**
	 * The way we will go about updating the positions of all of the segments is by abiding by my rule of "A sgement's 
	 * current x and y position is the previous x and y of the segment in front of it". For example, the previous x and y
	 * of the snake head will be the current x and y of the first sgement of the tail, causing it to follow the head. The
	 * previous x and y of the first segment will be the current x and y of the second, and so on.
	 */
	public void updateBody() {
		move(windowWidth, windowHeight);
		
		if(!tail.isEmpty()) {
			tail.get(0).updatePrevious(tail.get(0).x, tail.get(0).y);
			tail.get(0).setLocation(head.getXPrevious(), head.getYPrevious());
			for(int i = 1; i < tail.size(); i++) {
				tail.get(i).updatePrevious(tail.get(i).x, tail.get(i).y);
				tail.get(i).setLocation(tail.get(i - 1).getXPrevious(), tail.get(i - 1).getYPrevious());
			}
		}	
	}
	
	public boolean isGameOver() {
		for(SnakeSegment s : tail) 
			if(head.x == s.x && head.y == s.y)
				return true;
		return false;
	}
	
	public boolean isFoodEaten(int foodX, int foodY) {
		return head.x == foodX && head.y == foodY;
	}
	
	private void checkBounds(int rightBoundary, int bottomBoundary) {
		if(head.x < 0)
			head.x = rightBoundary - head.width;
		else if(head.y < 0)
			head.y = bottomBoundary - head.width;
		else if(head.x + head.width > rightBoundary)
			head.x = 0;
		else if (head.y + head.height > bottomBoundary)
			head.y = 0;
	}

	
}
