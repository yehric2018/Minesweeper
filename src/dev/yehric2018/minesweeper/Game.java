package dev.yehric2018.minesweeper;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.yehric2018.minesweeper.board.Board;
import dev.yehric2018.minesweeper.gfx.Assets;
import dev.yehric2018.minesweeper.gfx.Display;
import dev.yehric2018.minesweeper.input.MouseManager;

public class Game {
	private int width, height;
	
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	
	private MouseManager mouseManager;
	
	private Handler handler;
	private Board board;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		
		this.display = new Display(title, width, height);
	}
	private void init() {
		Assets.init();
		display.getFrame().setIconImage(Assets.icon);
		
		this.handler = new Handler(this);
		
		this.mouseManager = new MouseManager();
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		this.board = new Board(handler);
		
		display.getFrame().setVisible(true);
	}
	
	public void resetGame() {
		this.board = new Board(handler);
	}

	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, display.getFrame().getWidth(), display.getFrame().getHeight());
		
		board.render(g);
		
		bs.show();
		g.dispose();
	}

	public void run() {
		init();
		
		while (true) {
			render();
		}
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	public Display getDisplay() {
		return display;
	}
}
