package hostProgram;

import java.io.File;

public interface Plugin {
	void LoadMusic(File music);
	void Play();
	void Stop();
	void Pause();
	String GetMusicType();
}
