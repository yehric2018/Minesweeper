package dev.yehric2018.minesweeper;

public class Launcher {
	public static void main(String[] args) {
		Game game = new Game("Minesweeper", 500, 500);
		game.start();
	}
}
