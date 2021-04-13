package snakegame;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public final class GameBoard extends JPanel implements Runnable{

	private static final long serialVersionUID = 2L;
	
	public static int CELL_SIZE = 30;

	public static final int ROWS = 16; //poèet øádkù
	public static final int COLUMNS = 16; //poèet sloupcù
	
	public static final int WIDTH = ROWS * CELL_SIZE; //šíøka obrazovky
	public static final int HEIGHT = COLUMNS * CELL_SIZE; //výška obrazovky
	
	private static final int DELAY = 200; //delay
	
	private Thread game;
	
	private int score;
	
	private Font gameFont = new Font("Helvetica", Font.BOLD, 14);
	
	private Snake snake;


		//textura
	private Image apple;
		
		//souøadnice
	private int fruitX;
	private int fruitY;
		
	private boolean fruitGenerated = false;
	
	
	
	
	// konstruktor
	public GameBoard() {
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(new KAdapter());
		
		loadTexture();
		snake = new Snake();
		
	}
	
	private void loadTexture() {
		ImageIcon ii = new ImageIcon(".\\resource\\apple.png");
		apple = ii.getImage();
	}
	
	
	//spuštìní hry
	public void addNotify() {
		super.addNotify();
		game = new Thread(this);
		game.start();
	}
	
	
	
	
	// vykreslí JPanel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		if(snake.isAlive()) {
			drawFruit(g2d);
			snake.drawSnake(g2d);
		} else { 
			drawMsg(g2d);
		}
		
		drawScore(g2d);
		
		g2d.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void drawFruit(Graphics2D g2d) {
		g2d.drawImage(apple, fruitX * GameBoard.CELL_SIZE, fruitY * GameBoard.CELL_SIZE, null);
	}
	
	private void drawMsg(Graphics2D g2d) {
		
		String msg = "Stisknutím mezerníku spustíte hru";
		FontMetrics fm = getFontMetrics(gameFont);
	
		g2d.setFont(gameFont);
		g2d.setColor(Color.white);
		g2d.drawString(msg, (WIDTH - fm.stringWidth(msg)) / 2, HEIGHT / 2);
			
	}
	
	private void drawScore(Graphics2D g2d) {
		FontMetrics fm = getFontMetrics(gameFont);
		String scoreText = "Skóre: " + score;
		
		g2d.setColor(Color.white);
		g2d.setFont(gameFont);
		g2d.drawString(scoreText , 10, 0 + fm.getHeight() );
		
	}

	
	
	// herní cyklus
	public void run() {
		long startTime, timeDiff, sleep;
		startTime = System.currentTimeMillis();
		
		while(true) {
			
			update();
			
			repaint();
			
			timeDiff = System.currentTimeMillis() - startTime;
			sleep = DELAY - timeDiff;
			
			if(sleep < 2) {
				sleep = 2;
			}
			
			System.out.println(""+sleep);
			try { 
				Thread.sleep(sleep);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			startTime = System.currentTimeMillis();
		}
	}
	
	
	private void update() {
		if(snake.isAlive()) {
		snake.moveSnake();
		checkFruit();
		
		}
	}
	
	private void checkFruit() {
		if(!fruitGenerated) {
			generateFruit();
		}
		if(snake.fruitEaten(fruitX, fruitY)) {
			score++;
			generateFruit();
		}
		
	}
	
	public void generateFruit() {
		do {
		Random rngFruitPos = new Random();
		fruitX =  rngFruitPos.nextInt(16);
		fruitY =  rngFruitPos.nextInt(16);
		} while(!snake.checkSpace(fruitX, fruitY));
		
		fruitGenerated = true;
	}
	
	
		
	// reakce na zmáèknutí klávesy
	private class KAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) { 
			int keyCode = e.getKeyCode();
			
			if(!snake.isAlive() && keyCode == KeyEvent.VK_SPACE) {
				snake.resetSnake();
				snake.setAlive(true);
				score = 0;
				generateFruit();
			}
		}
		
		public void keyReleased(KeyEvent e) { 
			
			int keyCode = e.getKeyCode();
			
			if(checkKeys(keyCode)) {
				snake.keyReleased(keyCode);
			}
		} 
		
		private boolean checkKeys(int keyCode) {
			return keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S ||keyCode == KeyEvent.VK_A ||keyCode == KeyEvent.VK_D;
		}
		
		
	}
}
