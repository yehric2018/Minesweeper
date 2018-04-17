package dev.yehric2018.minesweeper.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	
	private JFrame frame;
	private Canvas canvas;
	
	public Display(String title, int width, int height) {
		init(title, width, height);
	}
	private void init(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setBackground(Color.LIGHT_GRAY);
		canvas.setFocusable(false);
		
		frame.add(canvas);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	public Canvas getCanvas() {
		return canvas;
	}
}
