package dev.yehric2018.minesweeper.board;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import dev.yehric2018.minesweeper.Handler;
import dev.yehric2018.minesweeper.gfx.Assets;

public class Tile {
	
	public static final int TILESIZE = 25;
	private Handler handler;
	private Rectangle bounds;
	
	private int boardX, boardY;
	private int x, y, size;
	private boolean revealed;
	private boolean marked;
	
	private int danger;
	private boolean mine;
	
	private BufferedImage[] images;
	
	public Tile(Handler handler, int y, int x) {
		this.handler = handler;
		
		revealed = false;
		marked = false;
		mine = false;
		
		danger = 0;
		
		this.size = TILESIZE;
		boardX = x;
		boardY = y;
		this.x = x * size + 5;
		this.y = y * size + 85;
		this.bounds = new Rectangle(this.x, this.y, size, size);
		
		images = new BufferedImage[3];
		images[0] = Assets.tile;
		images[1] = Assets.flag;
	}

	public void render(Graphics g) {
		if (revealed)
			g.drawImage(images[2], x, y, size, size, null);
		else if (marked)
			g.drawImage(images[1], x, y, size, size, null);
		else
			g.drawImage(images[0], x, y, size, size, null);
	}
	
	public void onMouseClick(MouseEvent e) {
		if (!marked && e.getButton() == MouseEvent.BUTTON1 && bounds.contains(e.getX(), e.getY()))
			handler.getBoard().getResetButton().setFace(1);
	}
	public void onMouseRelease(MouseEvent e) {
		if (!marked && e.getButton() == MouseEvent.BUTTON1 && bounds.contains(e.getX(), e.getY())) {
			revealed = true;
			if (mine) {
				images[2] = Assets.hitMine;
				handler.getBoard().loseGame();
			} else if (danger == 0) {
				revealed = false;
				handler.getBoard().recursion(boardY, boardX);
				handler.getBoard().getResetButton().setFace(0);
			} else {
				handler.getBoard().getResetButton().setFace(0);
			}
		} else if (!revealed && e.getButton() == MouseEvent.BUTTON3 && bounds.contains(e.getX(), e.getY())) {
			if (marked) {
				marked = false;
				handler.getBoard().removeMark();
			} else {
				marked = true;
				handler.getBoard().useMark();
			}
		}
	}
	public boolean isMine() {
		return mine;
	}
	public void setMine() {
		mine = true;
		danger = 99;
		images[2] = Assets.mine;
	}
	
	public int getDanger() {
		return danger;
	}
	public void setDanager(int danger) {
		this.danger = danger;
		images[2]= Assets.nums[danger];
	}
	
	public boolean isRevealed() {
		return revealed;
	}
	
	public void setFlag() {
		marked = true;
	}
	public void reveal() {
		revealed = true;
	}
}
