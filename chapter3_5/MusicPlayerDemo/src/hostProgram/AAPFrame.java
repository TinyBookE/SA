package hostProgram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AAPFrame {
	private AAPlayer player;
	private JFrame frame;
	private JTextField inTextField;
	private Plugin curPlugin = null;
	private String path = "";
	
	public AAPFrame() {
		// TODO Auto-generated constructor stub
	}
	
	public AAPFrame(AAPlayer player) {
		this.player = player;
	}
	
	public void init() {
		frame = new JFrame("MusicPlayerDemo");
		frame.setSize(500, 300);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(null);
		
		inTextField = new JTextField();
		panel.add(inTextField);
		inTextField.setEditable(false);
		inTextField.setBounds(10, 10, 300, 50);
		
		JButton chooseButton = new JButton("...");
		panel.add(chooseButton);
		chooseButton.setBounds(330, 10, 80, 50);
		chooseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jf.showOpenDialog(null);
				File f = jf.getSelectedFile();
				if(f!=null) {
					Plugin p = player.PlayMusic(f);
					if(p!=null) {
						 curPlugin = p;
						 inTextField.setText(f.getAbsolutePath());
					}
					else {
						JOptionPane.showMessageDialog(null, "Not support");
					}
				}
			}
		});
		
		JButton playButton = new JButton("²¥·Å");
		panel.add(playButton);
		playButton.setBounds(10, 80, 80, 50);
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curPlugin!=null) {
					curPlugin.Play();
				}
			}
		});
		
		
		JButton pauseButton = new JButton("ÔÝÍ£");
		panel.add(pauseButton);
		pauseButton.setBounds(110, 80, 80, 50);
		pauseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curPlugin!=null) {
					curPlugin.Pause();
				}
			}
		});
		
		JButton stopButton = new JButton("Í£Ö¹");
		panel.add(stopButton);
		stopButton.setBounds(210, 80, 80, 50);
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curPlugin!=null) {
					curPlugin.Stop();
				}
			}
		});
		
		
		AboutBox aboutBox = new AboutBox();
		panel.add(aboutBox);
		aboutBox.setBounds(10, 200, 80, 50);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
