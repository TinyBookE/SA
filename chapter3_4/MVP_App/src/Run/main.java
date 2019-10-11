package Run;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import MVP_frame.Presenter;;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("MVP");
		Presenter presenter = new Presenter();
		frame.add(presenter, BorderLayout.CENTER);
		presenter.init();
		presenter.start();
		
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}

}
