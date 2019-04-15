package MySnakeGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class PlaySnake extends JPanel implements ActionListener, KeyListener{
	
	private static int WIDTH = 600, HEIGHT = 600;
	private ArrayList<Integer> gridCoordinates;
	private int snakeWidth, foodX, foodY, amountToGrowBy;
	private JFrame frame;
	private SnakeBody snake;
	private Timer t;
	private FrameRate FPS;
	private Random rand;
	
	public PlaySnake() {
		snakeWidth = 20;
		amountToGrowBy = 3;
		rand = new Random();
		gridCoordinates = new ArrayList<Integer>();
		frame = new JFrame();
		t = new Timer(70, this);
		FPS = new FrameRate();
		snake = new SnakeBody(WIDTH / 2, HEIGHT / 2, snakeWidth, WIDTH, HEIGHT, amountToGrowBy);
		
		for(int i = 0; i < WIDTH - (snakeWidth * 2); i += snakeWidth) {
			gridCoordinates.add(i);
		}
		
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Snake");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.black);
		pickNewLocation();
		t.start();
		
	}
	
	public static void main(String args[]) {
		new PlaySnake();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		createFood(g);
		if(snake.isGameOver()) {
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString("GAME OVER", 200, 300);
			frame.dispose();
		}else
			snake.printSnake(g);
		FPS.printFPS(g);
		FPS.limitFPSTo60();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		wasFoodEaten();
		snake.updateBody();
		FPS.calculateFPS();
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		
		if(key.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != Direction.DOWN) 
			snake.setDirection(Direction.UP);
		else if(key.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != Direction.UP) 
			snake.setDirection(Direction.DOWN);
		else if(key.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != Direction.LEFT) 
			snake.setDirection(Direction.RIGHT);
		else if(key.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != Direction.RIGHT) 
			snake.setDirection(Direction.LEFT);
		
	}

	public void createFood(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(foodX, foodY, snakeWidth, snakeWidth);
	} 
	
	public void pickNewLocation() {
		foodX = gridCoordinates.get(rand.nextInt(gridCoordinates.size()));
		foodY = gridCoordinates.get(rand.nextInt(gridCoordinates.size()));
	}
	
	public void wasFoodEaten() {
		
		if(snake.isFoodEaten(foodX, foodY)) {
			pickNewLocation();
			snake.grow();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// nothing goes here
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// nothing goes here
		
	}
	
	
}
