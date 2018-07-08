package dev.yehric2018.minesweeper;

import dev.yehric2018.minesweeper.board.Tile;

public class Launcher {
	public static void main(String[] args) {
		Game game = new Game("Minesweeper", 15 + Handler.GAMEWIDTH * Tile.TILESIZE, 120 + Handler.GAMEHEIGHT * Tile.TILESIZE);
		game.run();
	}
}
