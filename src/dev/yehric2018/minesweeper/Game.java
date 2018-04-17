package dev.yehric2018.minesweeper;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.yehric2018.minesweeper.board.Board;
import dev.yehric2018.minesweeper.gfx.Assets;
import dev.yehric2018.minesweeper.gfx.Display;
import dev.yehric2018.minesweeper.input.KeyManager;
import dev.yehric2018.minesweeper.input.MouseManager;

public class Game implements Runnable {
	private String title;
	private int width, height;
	
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	
	private Thread thread;
	private boolean running = false;
	
	private MouseManager mouseManager;
	private KeyManager keyManager;
	
	private Handler handler;
	private Board board;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		this.display = new Display(title, width, height);
	}
	private void init() {
		Assets.init();
		
		this.handler = new Handler(this);
		
		this.mouseManager = new MouseManager();
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		this.keyManager = new KeyManager();
		display.getFrame().addKeyListener(keyManager);
		
		this.board = new Board(handler);
		
		display.getFrame().setVisible(true);
	}
	
	public void update() {}
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

	@Override
	public void run() {
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}
			if (timer  >= 1000000000) {
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	public KeyManager getKeyManager() {
		return keyManager;
	}
	public MouseManager getMouseManager() {
		return mouseManager;
	}
}
