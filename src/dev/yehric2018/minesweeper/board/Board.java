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
	
	public Board(Handler handler) {
		this.handler = handler;
		width = Handler.GAMEWIDTH;
		height = Handler.GAMEHEIGHT;
		this.handler.getMouseManager().setBoard(this);
		
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
		int mines = width * height / 10;
		while (mines > 0) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (!grid[i][j].isMine() && Math.random() > 0.9) {
						grid[i][j].setMine();
						mines--;
					}
				}
			}
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!grid[i][j].isMine()) {
					grid[i][j].setDanager(findMines(i, j));
				}
			}
		}
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
	
	public void update() {}
	public void render(Graphics g) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j].render(g);
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
	public void endGame() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].isMine())
					grid[i][j].reveal();
			}
		}
		alive = false;
	}
}
