package dev.yehric2018.minesweeper.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	public static final int TILESIZE = 128, FACESIZE = 104, SCOREWIDTH = 52, SCOREHEIGHT = 92;
	
	public static BufferedImage icon;
	
	public static BufferedImage[] nums;
	public static BufferedImage flag, mine, tile, hitMine;
	public static BufferedImage[] score;
	public static BufferedImage[] faceButton;
	
	public static void init() {
		icon = ImageLoader.loadImage("/icon.png");
		
		SpriteSheet sheet = new SpriteSheet("/tiles.jpg");
		tile = sheet.crop(0, 0, TILESIZE, TILESIZE);
		flag = sheet.crop(TILESIZE, 0, TILESIZE, TILESIZE);
		mine = sheet.crop(TILESIZE * 2, 0, TILESIZE, TILESIZE);
		nums = new BufferedImage[9];
		nums[0] = sheet.crop(TILESIZE * 3, 0, TILESIZE, TILESIZE);
		nums[1] = sheet.crop(0, TILESIZE, TILESIZE, TILESIZE);
		nums[2] = sheet.crop(TILESIZE, TILESIZE, TILESIZE, TILESIZE);
		nums[3] = sheet.crop(TILESIZE * 2, TILESIZE, TILESIZE, TILESIZE);
		nums[4] = sheet.crop(TILESIZE * 3, TILESIZE, TILESIZE, TILESIZE);
		nums[5] = sheet.crop(0, TILESIZE * 2, TILESIZE, TILESIZE);
		nums[6] = sheet.crop(TILESIZE, TILESIZE * 2, TILESIZE, TILESIZE);
		nums[7] = sheet.crop(TILESIZE * 2, TILESIZE * 2, TILESIZE, TILESIZE);
		nums[8] = sheet.crop(TILESIZE * 3, TILESIZE * 2, TILESIZE, TILESIZE);
		hitMine = sheet.crop(0, TILESIZE * 3, TILESIZE, TILESIZE);
		
		sheet = new SpriteSheet("/nongameelements.png");
		faceButton = new BufferedImage[4];
		faceButton[0] = sheet.crop(0, 0, FACESIZE, FACESIZE);
		faceButton[1] = sheet.crop(FACESIZE, 0, FACESIZE, FACESIZE);
		faceButton[2] = sheet.crop(FACESIZE * 2, 0, FACESIZE, FACESIZE);
		faceButton[3] = sheet.crop(FACESIZE * 3, 0, FACESIZE, FACESIZE);
		score = new BufferedImage[10];
		for (int i = 0; i < score.length; i++) {
			score[i] = sheet.crop(SCOREWIDTH * i, FACESIZE, SCOREWIDTH, SCOREHEIGHT);
		}
	}
}
