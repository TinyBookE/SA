package hostProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class AAPlayer {
	protected File[] plugins_list;
	public Map<String, Plugin> pluginsMap = new HashMap<String, Plugin>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AAPlayer();
	}
	
	public AAPlayer() {
		// TODO Auto-generated constructor stub
		AAPFrame aapFrame = new AAPFrame(this);
		GetPlugins();
		LoadPlugins();
		
		aapFrame.init();
	}
	
	public void GetPlugins() {
		File plugins_dir = new File("./plugins");
		if(!plugins_dir.exists() || !plugins_dir.isDirectory()) {
			plugins_list = new File[0];
			return;
		}
		
		plugins_list = plugins_dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".jar");
			}
		});
	}
	
	public void LoadPlugins() {
		for(File f:plugins_list) {
			try {
				URL url = f.toURI().toURL();
				URLClassLoader classLoader = new URLClassLoader(new URL[] {url}, Thread.currentThread().getContextClassLoader());
				BufferedReader in = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("Class.info")));
				
				String s = new String();
				while ((s=in.readLine())!=null) {
					Class pluginClass = classLoader.loadClass(s);
					Plugin plugin = (Plugin)pluginClass.newInstance();
					String type = plugin.GetMusicType();
					if(!pluginsMap.containsKey(type)) {
						pluginsMap.put(type, plugin);
					}
				}
				
				in.close();
				classLoader.close();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public Plugin PlayMusic(File music) {
		String musicName = music.getName();
		String suffix = musicName.substring(musicName.lastIndexOf('.'), musicName.length());
		
		if(pluginsMap.containsKey(suffix)) {
			Plugin plugin = pluginsMap.get(suffix);
			plugin.LoadMusic(music);
			return plugin;
		}
		return null;
	}
	
}
