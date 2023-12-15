package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS //
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public class DesktopLauncher {
    
    public static void main (String[] arg) {
                
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();       // Creates a new application window
        config.setTitle("Killer");                                                          // Sets the title of the window to "Killer"
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());          // Sets the screen dimensions to fit the user's viewport (fullscreen)
        config.setForegroundFPS(60);                                                        // Set frame rate to 60 FPS
        new Lwjgl3Application(new Killer(), config);                                        // Runs the Killer file in the application window
    }
}
