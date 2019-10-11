package MVP_frame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import core.Observer;

public class View extends Canvas implements Observer {
	Presenter presenter;
	int stepNumber = 0;
	int x = 0;
	int y = 0;
	final int BALL_SIZE;
	
	public View(int size) {
		// TODO Auto-generated constructor stub
		BALL_SIZE = size;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, BALL_SIZE, BALL_SIZE);
		presenter.showStatus("Step "+ (stepNumber++) +", x ="+ x + ", y ="+ y);
	}
	
	@Override
	public void update() {
		repaint();
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
		repaint();
	}
}
