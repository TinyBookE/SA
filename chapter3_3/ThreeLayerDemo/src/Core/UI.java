package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UI {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BUS _userBus = new BUS();
		JFrame frame = new JFrame("ThreeLayerDemo");
		frame.setSize(550, 400);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.add(panel);
		
		JTextArea inputextArea = new JTextArea();
		panel.add(inputextArea);
		inputextArea.setBounds(10, 10, 300, 50);
		
		/*
		JTextArea ouTextArea = new JTextArea();
		panel.add(ouTextArea);
		ouTextArea.setEditable(false);
		ouTextArea.setBounds(10, 100, 300, 100);
		*/
		
		JButton button = new JButton("SearchByName");
		panel.add(button);
		button.setBounds(10, 200, 200, 50);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserVO vo = _userBus.getUserEmailByName(inputextArea.getText());
				inputextArea.setText(null);
				if(vo.email==null) {
					JOptionPane.showMessageDialog(null, "No Match Found", "Not Found", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, vo.email, "Result", JOptionPane.INFORMATION_MESSAGE);
				}
				/*
				ouTextArea.setText(null);
				ouTextArea.append("id: " + String.valueOf(vo.idUser) + "\n");
				ouTextArea.append("firstname: " + vo.firstname + "\n");
				ouTextArea.append("lastname: " + vo.lastname + "\n");
				ouTextArea.append("email: " + vo.email + "\n");
				*/
			}
		});
		
		frame.setVisible(true);
	}

}
