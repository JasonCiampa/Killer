package com.killer.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TitleScreen extends Scene {
    
    // IMAGES
    private Texture killerLogo;
    
    // BUTTONS
    ChangeSceneButton playButton;
    Button quitButton;
    
    // MUSIC
    private Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Ending Theme - Super Mario World.mp3"));
    
    public TitleScreen() {
        super("images/main_menu/background.jpg", "music/Ending Theme - Super Mario World.mp3", 0, 0);                                       // Calls the Scene's constructor to build the framework for TitleScreen
        
        killerLogo = new Texture("images/main_menu/killer_logo.png");                                                                                                     // Stores the killerLogo texture
        
        playButton = new ChangeSceneButton(Button.LONG, (this.width / 2) - (this.width / 4), (this.height / 2) - 50, "Play Game", this, null);         // Creates a playButton that changes the Scene to casinoTable when clicked on
            
        quitButton = new Button(Button.LONG, (this.width / 2) - (this.width / 4), (this.height / 2) - 250, "Quit Game", this) {                                     // Creates a quitButton
            @Override
            public void clickAction() {                                                                                                                                               // Overriding the abstract clickAction method from the Button class                                                                    
                Gdx.app.exit();                                                                                                                                                       // Quits the program, ends the application
            }
        };
    }
    
    // Anything that should happen immediately as TitleScreen becomes active should go in load
    @Override
    protected void load() {
        playButton.next = Killer.casinoTable;
    }
    
    @Override
    protected void update() {
        this.updateButtons();                                                                                                                                                          // Updates all of TitleScreen's Buttons
    }
    
    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(this.backgroundImage, this.x, this.y, this.width, this.height);                                                                               // Draws TitleScreen's background image onto the screen
        batch.draw(killerLogo, this.x, this.y);                                                                                                                              // Draws the killerLogo onto the screen
        this.drawButtons(batch);                                                                                                                                                        // Draw all of TitleScreen's Buttons onto the screen
    }
    
}
