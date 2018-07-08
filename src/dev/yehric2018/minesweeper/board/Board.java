package dev.yehric2018.minesweeper.board;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import dev.yehric2018.minesweeper.Handler;
import dev.yehric2018.minesweeper.gfx.Assets;

public class Board {
	
	private Handler handler;
	private int width, height;
	private Tile[][] grid;
	
	private boolean alive;
	
	private int timer;
	private boolean timerStart;
	private long initialTime;
	
	private int marksLeft;
	private int safeTiles;
	
	private ResetButton resetButton;
	
	public Board(Handler handler) {
		this.handler = handler;
		width = Handler.GAMEWIDTH;
		height = Handler.GAMEHEIGHT;
		this.handler.getMouseManager().setBoard(this);
		timer = 0;
		timerStart = false;
		
		alive = true;
		handler.setBoard(this);
		
		init();
	}
	private void init() {
		grid = new Tile[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = new Tile(handler, i, j);
			}
		}
		
		marksLeft = 99;
		safeTiles = width * height - marksLeft;
		
		int mines = marksLeft;
		while (mines > 0) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (!grid[i][j].isMine() && Math.random() > 0.9) {
						grid[i][j].setMine();
						mines--;
					}
					if (mines == 0)
						break;
				}
				if (mines == 0)
					break;
			}
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!grid[i][j].isMine()) {
					grid[i][j].setDanager(findMines(i, j));
				}
			}
		}
		resetButton = new ResetButton(handler, handler.getWidth() / 2 - 25, 10, 50);
	}
	private int findMines(int y, int x) {
		int count = 0;
		if (y != 0 && x != 0 && grid[y - 1][x - 1].isMine())
			count++;
		if (y != 0 && grid[y - 1][x].isMine())
			count++;
		if (x != 0 && grid[y][x - 1].isMine())
			count++;
		if (y != grid.length - 1 && grid[y + 1][x].isMine())
			count++;
		if (x != grid[0].length - 1 && grid[y][x + 1].isMine())
			count++;
		if (y != 0 && x != grid[0].length - 1 && grid[y - 1][x + 1].isMine())
			count++;
		if (y != grid.length - 1 && x != grid[0].length - 1 && grid[y + 1][x + 1].isMine())
			count++;
		if (y != grid.length - 1 && x != 0 && grid[y + 1][x - 1].isMine())
			count++;
		return count;
	}
	
	public void render(Graphics g) {
		resetButton.render(g);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j].render(g);
			}
		}
		
		//MINES LEFT RENDER
		g.drawImage(Assets.score[marksLeft % 1000 / 100], 200, 15, 26, 46, null);
		g.drawImage(Assets.score[marksLeft % 100 / 10], 226, 15, 26, 46, null);
		g.drawImage(Assets.score[marksLeft % 10], 252, 15, 26, 46, null);
		
		//GAME TIMER RENDER
		if (timerStart) {
			long now = System.nanoTime();
			if (now - initialTime >= 1000000000) {
				timer++;
				initialTime = now;
			}
		}
		g.drawImage(Assets.score[timer % 1000 / 100], 500, 15, 26, 46, null);
		g.drawImage(Assets.score[timer % 100 / 10], 526, 15, 26, 46, null);
		g.drawImage(Assets.score[timer % 10], 552, 15, 26, 46, null);
	}
	
	public void onMouseClick(MouseEvent e) {
		if (alive) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					grid[i][j].onMouseClick(e);
				}
			}
		}
	}
	public void onMouseRelease(MouseEvent e) {
		if (alive) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					grid[i][j].onMouseRelease(e);
				}
			}
		}
		resetButton.onMouseRelease(e);
		
		int count = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].isRevealed()) {
					count++;
				}
			}
		}
		if (count > 0 && !timerStart && alive) {
			timerStart = true;
			initialTime = System.nanoTime();
		}
		if (count == safeTiles) {
			winGame();
		}
	}
	
	public void recursion(int y, int x) {
		if (grid[y][x].getDanger() != 0 || grid[y][x].isRevealed()) {
			grid[y][x].reveal();
		} else {
			grid[y][x].reveal();
			if (y != 0)
				recursion(y - 1, x);
			if (y != grid.length - 1)
				recursion(y + 1, x);
			if (y != 0 && x != 0)
				recursion(y - 1, x - 1);
			if (y != 0 && x != grid[0].length - 1)
				recursion(y - 1, x + 1);
			if (y != grid.length - 1 && x != 0)
				recursion(y + 1, x - 1);
			if (y != grid.length - 1 && x != grid[0].length - 1)
				recursion(y + 1, x + 1);
			if (x != 0)
				recursion(y, x - 1);
			if (x != grid[0].length - 1)
				recursion(y, x + 1);
		}
	}
	
	public void loseGame() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].isMine())
					grid[i][j].reveal();
			}
		}
		timerStart = false;
		alive = false;
		resetButton.setFace(3);
	}
	private void winGame() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].isMine())
					grid[i][j].setFlag();
			}
		}
		marksLeft = 0;
		timerStart = false;
		alive = false;
		resetButton.setFace(2);
	}
	
	public ResetButton getResetButton() {
		return resetButton;
	}
	public void removeMark() {
		marksLeft++;
	}
	public void useMark() {
		marksLeft--;
	}
}
