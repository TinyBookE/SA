package hostProgram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class AboutBox extends JButton{
	public AboutBox() {
		// TODO Auto-generated constructor stub
		this.setText("About");
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Player Demo", "About me", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
}
