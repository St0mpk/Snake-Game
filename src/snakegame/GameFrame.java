package snakegame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

/*
 * Tøída urèená pro zobrazení hlavního okna hry
 */

public final class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GameBoard board = new GameBoard();

	public static void main(String[] args) {
			
		Runnable runner = new Runnable() {
			
			public void run() {
				GameFrame gFrame = new GameFrame();
				gFrame.setVisible(true); 
	
			}
		};
		
		EventQueue.invokeLater(runner);
	}

	//konstruktor
	public GameFrame() {
		
		setContentPane(board);
		
		setResizable(false);
		pack();
		setTitle("SNAKE");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
}
