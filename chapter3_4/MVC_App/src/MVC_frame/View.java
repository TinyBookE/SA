package MVC_frame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import core.Observer;

public class View extends Canvas implements Observer {
	Model model;
	Controller controller;
	int stepNumber = 0;
	
	public View(Model model) {
		// TODO Auto-generated constructor stub
		this.model = model;
		//this.setSize(300, 300);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(model.xPosition, model.yPosition, model.BALL_SIZE, model.BALL_SIZE);
		controller.showStatus("Step "+ (stepNumber++) +", x ="+ model.xPosition + ", y ="+ model.yPosition);
	}
	
	@Override
	public void update() {
		repaint();
	}
}
