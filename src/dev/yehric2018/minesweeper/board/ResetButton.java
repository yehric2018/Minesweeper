package dev.yehric2018.minesweeper.board;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import dev.yehric2018.minesweeper.Handler;
import dev.yehric2018.minesweeper.gfx.Assets;

public class ResetButton {
	
	private Handler handler;
	private Rectangle bounds;
	private int x, y, size, face;
	
	public ResetButton(Handler handler, int x, int y, int size) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.size = size;
		this.face = 0;
		
		this.bounds = new Rectangle(x, y, size, size);
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.faceButton[face], x, y, size, size, null);
	}
	public void onMouseRelease(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1 && bounds.contains(e.getX(), e.getY()))
			handler.getGame().resetGame();
	}
	
	public void setFace(int n) {
		face = n;
	}
}
