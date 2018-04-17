package dev.yehric2018.minesweeper;

import dev.yehric2018.minesweeper.board.Tile;

public class Launcher {
	public static void main(String[] args) {
		Game game = new Game("Minesweeper", 25 + Handler.GAMEWIDTH * Tile.TILESIZE, 130 + Handler.GAMEHEIGHT * Tile.TILESIZE);
		game.start();
	}
}
