package MVC_frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Controller extends JApplet{
	
	JPanel buttonPanel = new JPanel();
	JButton stepButton = new JButton("Step");
	
	Model model;
	View view;
	
	public void init() {
		model = new Model();
		view = new View(model);
		setSize(300, 300);
		setLayout(new BorderLayout());
		buttonPanel.add(stepButton);
		this.add(BorderLayout.SOUTH, buttonPanel);
		this.add(BorderLayout.CENTER, view);
		
		stepButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.makeOneStep();
			}
		});
		
		model.addObserver(view);
		view.controller = this;
	}
	
	public void start() {
		model.xLimit = view.getSize().width - model.BALL_SIZE;
		model.yLimit = view.getSize().height - model.BALL_SIZE;
		repaint();
	}
}
