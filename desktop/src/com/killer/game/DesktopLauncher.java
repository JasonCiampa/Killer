package com.killer.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    
	public static void main (String[] arg) {
                
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
                
                // Sets the title of the window to "Killer"
                config.setTitle("Killer");
                
                // Sets the screen dimensions to fit the user's viewport (fullscreen)
                config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
                
                config.useVsync(true);

		config.setForegroundFPS(60);
                
		new Lwjgl3Application(new Killer(), config);
	}
}
