package snakegame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Snake {
	 
	private BufferedImage snakeTexture;
	
	private int numberOfCells;
	
	private int [] cellX = new int[GameBoard.ROWS * GameBoard.COLUMNS];
	private int [] cellY = new int[GameBoard.ROWS * GameBoard.COLUMNS];
	
	private Direction dir;
	
	private boolean canChangeDir;
	private boolean alive = false;
	
	
	public Snake() {
		loadTexture();
		resetSnake();
	}
	
	public void resetSnake() {
		numberOfCells = 3;
		dir = Direction.UP;
		canChangeDir = false;
		
		int initX = 8, initY = 8;
		
		for(int i = numberOfCells; i >= 0; i--, initY-- ) {
			cellX[i] = initX;
			cellY[i] = initY;
		}
	}
	
	//naète texture hada
	private void loadTexture() {
		snakeTexture = new BufferedImage(GameBoard.CELL_SIZE, GameBoard.CELL_SIZE, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = (Graphics2D) snakeTexture.getGraphics();
		g2d.setColor(Color.green);
		g2d.fillRect(0 + 2, 0 + 2, GameBoard.CELL_SIZE - 2, GameBoard.CELL_SIZE - 2);
		
		g2d.dispose();
	}
	
	public void drawSnake(Graphics2D g2d) {
		
		for(int i = 0; i < numberOfCells; i++) {
			g2d.drawImage(snakeTexture, cellX[i] * GameBoard.CELL_SIZE, cellY[i] * GameBoard.CELL_SIZE, null);	
		}
		
	}
	
	public void moveSnake() {
		for(int i = numberOfCells; i > 0; i--) {
			cellX[i] = cellX[i-1];
			cellY[i] = cellY[i-1];
		}
		
		if(dir == Direction.UP) {
			cellY[0]--;
		} else if(dir == Direction.DOWN) {
			cellY[0]++;
		} else if(dir == Direction.LEFT) {
			cellX[0]--;
		} else if(dir == Direction.RIGHT) {
			cellX[0]++;
		}
		
		canChangeDir = true;
		
		if(checkBorderCollision() || checkSuicide()) {
			alive = false;
			resetSnake();
		}
	}
	
	private boolean checkBorderCollision() {
		return cellY[0] < 0 || cellY[0] > GameBoard.COLUMNS - 1 || cellX[0] < 0 || cellX[0] > GameBoard.ROWS - 1;
	}
	
	private boolean checkSuicide() {
		
		for(int i = 4; i < numberOfCells; i++) {
			if(cellX[0] == cellX[i] && cellY[0] == cellY[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	public void keyReleased(int keyCode) {
	
		if(alive && canChangeDir) {
			
			if(dir == Direction.RIGHT || dir == Direction.LEFT) {
				
				if(keyCode == KeyEvent.VK_W) {
					dir = Direction.UP;
					
				} else if(keyCode == KeyEvent.VK_S) {
					dir = Direction.DOWN; 
					}
		
				}else if(dir == Direction.UP || dir == Direction.DOWN)	{
				
					if(keyCode == KeyEvent.VK_A) {
					dir = Direction.LEFT;
					
				} else if(keyCode == KeyEvent.VK_D){
					dir = Direction.RIGHT;
					
				}
			}
			
			canChangeDir = false;

		}
	}
	
	public boolean checkSpace(int fruitX, int fruitY) {
		
		for(int i = numberOfCells; i >= 0; i--) {
			if(fruitX == cellX[i] && fruitY == cellY[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean fruitEaten(int fruitX, int fruitY) {
		boolean eaten = false;
		if(fruitX == cellX[0] && fruitY == cellY[0]) {
			numberOfCells++;
			eaten = true;
		}
		
		return eaten;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void increaseLength() {
		numberOfCells++;
	}
	
}
