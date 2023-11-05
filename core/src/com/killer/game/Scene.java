package com.killer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Scene {
    protected static int totalScenes;
    
    protected boolean active;
    protected Texture backgroundImage;
    protected Music backgroundMusic;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    protected ArrayList<Button> buttons;
    
    protected Scene(String pathToBackgroundImage, String pathToBackgroundMusic, int x, int y) {
        totalScenes++;
        this.active = false;
        this.backgroundImage = new Texture(Gdx.files.internal(pathToBackgroundImage));
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(pathToBackgroundMusic));
        this.x = x;
        this.y = y;
        this.width = backgroundImage.getWidth();
        this.height = backgroundImage.getHeight();
        
        this.buttons = new ArrayList<Button>();
    }
    
    protected void makeButton(int width, int height, int x, int y, String text, String buttonName) {
        Button button = new Button(width, height, x, y, text, buttonName);
        buttons.add(button);
    }    
    
    protected void draw(SpriteBatch batch) {
        if (this.active) {
            // Draws the background image of the scene
            batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);

            // Draws every button in the scene
            for (int i = 0; i < this.buttons.size(); i++) {
                this.buttons.get(i).draw(batch);
            }
        }
    }
    
    
}
