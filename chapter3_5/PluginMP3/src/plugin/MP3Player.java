package plugin;

import java.io.File;

import javax.swing.JOptionPane;

import hostProgram.Plugin;

public class MP3Player implements Plugin {
	public void LoadMusic(File music) {
		
	}
	public void Play() {
		JOptionPane.showMessageDialog(null, "play mp3");
	}
	public void Stop() {
		JOptionPane.showMessageDialog(null, "stop mp3");
	}
	public void Pause() {
		JOptionPane.showMessageDialog(null, "pause mp3");
	}
	public String GetMusicType() {
		return ".mp3";
	}
}
