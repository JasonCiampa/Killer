package com.killer.game;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

// IMPORTS // 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

public abstract class Scene {
    

    // FIELDS // 
    protected static int totalScenes;                                                                                                                                 // Keeps track of how many scenes have been created.
    protected Texture backgroundImage;                                                                                                                                // Background image to be drawn for the scene
    protected Music backgroundMusic;                                                                                                                                  // Background music to be played while the scene is active
    protected int x;                                                                                                                                                  // x-coordinate of the scene (px)
    protected int y;                                                                                                                                                  // y-coordinate of the scene (px)
    protected int width;                                                                                                                                              // width of the scene (px)
    protected int height;                                                                                                                                             // height of the scene (px)
    protected ArrayList<Button> buttons;                                                                                                                              // an ArrayList of all buttons created in a scene    

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // CONSTRUCTOR //
    protected Scene(String pathToBackgroundImage, String pathToBackgroundMusic, int x, int y) {
        totalScenes++;                                                                                                                                                // Increase total scene count by 1 since we're creating a new one
        this.backgroundImage = new Texture(Gdx.files.internal(pathToBackgroundImage));                                                                      // Sets background image
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(pathToBackgroundMusic));                                                               // Sets background music
        this.x = x;                                                                                                                                                   // Sets x-coordinate
        this.y = y;                                                                                                                                                   // Sets y-coordinate
        this.width = backgroundImage.getWidth();                                                                                                                      // Sets width to the width of the image
        this.height = backgroundImage.getHeight();                                                                                                                    // Sets height to the height of the image
        this.buttons = new ArrayList<Button>();                                                                                                                       // Creates a new ArrayList to store any buttons that are created inside.
    }
  
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    // METHODS //
    
    // Plays the Scene's background music
    protected void playMusic() {
        this.backgroundMusic.setLooping(true);                                                                                                                // Enables looping for the Scene's background music
        this.backgroundMusic.play();                                                                                                                                   // Begins to play the Scene's background music
        this.backgroundMusic.setVolume((float) 0.25);                                                                                                                  // Lowers the volume to 25% for the Scene's background music
    }
    
    // Stops the Scene's background music
    protected void stopMusic() {
        this.backgroundMusic.stop();
    }
    
    // Enables the Scene
    protected void enable() {
        Killer.activeScene = this;                                                                                                                                    // Set this Scene as the activeScene in Killer
        this.playMusic();                                                                                                                                             // Play this Scene's background music
        Killer.activeScene.load();                                                                                                                                    // Load this Scene
    }
    
    // Disables the Scene
    protected void disable() {
        this.stopMusic();                                                                                                                                             // Stop this Scene's music
    }
    
    // Creates a new Button object and adds it to the Scene's ArrayList of Buttons.
    protected void updateButtons() {
        for (Button button: this.buttons) {                                                                                                                           // For each Button in titleScreen...
            button.update();
        }
    }
    
    // Draws all of the Buttons stored in the Scene on the screen
    protected void drawButtons(SpriteBatch batch) { 
        for (int i = 0; i < this.buttons.size(); i++) {                                                                                                               // For every Button in the Scene...
            this.buttons.get(i).draw(batch);                                                                                                                       // Draw the Button
        }
    }    


    // Initializes the state of the Scene
    protected abstract void load();                                                                                                                                   // This code will vary for each Scene, so a Scene-specific function should be written and take the place of this one
    
    // Updates the state of the Scene
    protected abstract void update();                                                                                                                                 // This code will vary for each Scene, so a Scene-specific function should be written and take the place of this one
    
    // Draws the state of the Scene
    protected abstract void draw(SpriteBatch batch);                                                                                                                  // This code will vary for each Scene, so a Scene-specific function should be written and take the place of this one
    
}

// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//