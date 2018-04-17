package dev.yehric2018.minesweeper;

import dev.yehric2018.minesweeper.board.Board;
import dev.yehric2018.minesweeper.input.KeyManager;
import dev.yehric2018.minesweeper.input.MouseManager;

public class Handler {
	
	public static final int GAMEWIDTH = 40, GAMEHEIGHT = 20;
	
	private Game game;
	private Board board;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
}
