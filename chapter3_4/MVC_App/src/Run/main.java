package Run;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import MVC_frame.Controller;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("MVC");
		Controller controller = new Controller();
		frame.add(controller, BorderLayout.CENTER);
		controller.init();
		controller.start();
		
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}

}
